package com.rantanplan;

import com.rantanplan.model.Region;
import com.rantanplan.model.RegionEnum;
import com.rantanplan.model.Regions;
import com.rantanplan.model.Sequence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

@Builder
@AllArgsConstructor
@Slf4j
public class MRNAParser {
    private Sequence sequence;
    private final static String START_CODON = "ATG";
    private final static String[] STOP_CODONS = new String[]{"TAA", "TAG", "TGA"};
    private final static String POLY_A = "AAAAAAA"; // TODO minimum poly A size ?
    private final static int CAP_SIZE = 2;
    private final static String[] CAPS = new String[]{"GA", "GG"};


    public Regions parse() {
        String chain = sequence.chain();

        // If doesn't stat with a CAP, this should be MRNA containing only the translated region (sig+p^rotein)
        if (!asList(CAPS).contains(chain.substring(0, 2))) {
            return Regions.builder()
                    .region(parseProtein(chain))
                    .build();
        }

        // CAP
        Region cap = Region.builder()
                .type(RegionEnum.cap)
                .sequence(Sequence.builder()
                        .chain(chain.substring(0, CAP_SIZE))
                        .build())
                .build();
        chain = chain.substring(CAP_SIZE);

        // 5'UTR
        //From end of cap to initiation sequence
        String fivePrimeUTRChain = chain.substring(0, chain.indexOf(START_CODON));
        Region fivePrimeUTR = Region.builder()
                .type(RegionEnum.fivePrimeUtr)
                .sequence(Sequence.builder()
                        .chain(fivePrimeUTRChain)
                        .build())
                .build();
        chain = chain.substring(chain.indexOf(START_CODON));

        // sig & spike
        // From end of 5'UTR to end Codon
        Region sigAndSpike = parseProtein(chain);
        chain = chain.substring((int)sigAndSpike.sequence().length());

        // 3'UTR
        // From end of spike to poly A
        int polyAStartIndex = chain.indexOf(POLY_A);
        String threePrimeUTRChain = chain.substring(0, polyAStartIndex);
        Region threePrimeUtr = Region.builder()
                .type(RegionEnum.threePrimeUtr)
                .sequence(Sequence.builder()
                        .chain(threePrimeUTRChain)
                        .build())
                .build();
        chain = chain.substring(polyAStartIndex);

        // polyA end of 3'UTR (should we split 3' and polyA? probably not but doesn't matter here
        Region polyA = Region.builder()
                .type(RegionEnum.polyA)
                .sequence(Sequence.builder()
                        .chain(chain)
                        .build())
                .build();

        return Regions.builder()
                .region(cap)
                .region(fivePrimeUTR)
                .region(sigAndSpike)
                .region(threePrimeUtr)
                .region(polyA)
                .build();
    }

    private Region parseProtein(String chain) {
        //TODO how to sig peptide prediction ?
        List<String> sigAndSpikeCodons = readUntilStopCodon(chain);
        Region sigAndSpike = Region.builder()
                .type(RegionEnum.protein)
                .sequence(Sequence.builder()
                        .chain(String.join("", sigAndSpikeCodons))
                        .build())
                .build();
        return sigAndSpike;
    }

    private ArrayList<String> readUntilStopCodon(String chain) {
        StringReader reader = new StringReader(chain);
        char[] codon = new char[3];
        ArrayList<String> codons = new ArrayList<>();
        Boolean stopFound = Boolean.FALSE, isStop = Boolean.FALSE;
        while (true) {
            try {
                if (!(reader.read(codon, 0, 3) > 0)) break;
                String codonStr = String.valueOf(codon);
                isStop = false;
                if (Arrays.stream(STOP_CODONS).anyMatch(codonStr::equals)) {
                    log.debug("stop codon {} found at index {}", codonStr, codons.size() * 3);
                    stopFound = true;
                    isStop = true;
                }
                if (stopFound && !isStop) break;
                codons.add(codonStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reader.close();
        return codons;
    }
}
