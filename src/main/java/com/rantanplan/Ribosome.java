package com.rantanplan;

import com.rantanplan.model.*;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.StringReader;
import java.util.ArrayList;

@Slf4j
@Builder
public class Ribosome {
    // TODO Ribosome is part of the cell
    // Cell could contain cytoplasm, Ribosom, some immune response ...
    // + introduction of mrna inside the cell through lipid nanoparticules?
    // + protein exiting the cell via endoplasmic reticulum (see signal peptide, todo signal peptide prdiction)

    Regions regions;

    public void bindToMRNA(MRNA mrna) {
        // TODO merge MRNAParser and Ribosome ? MRNAParser is not natural
        regions = new MRNAParser(mrna.sequence()).parse();
        log.info("regions : {}", regions);
    }

    public Protein translate() {
        Region proteinRegion = regions.getProteinRegion();
        String chain = proteinRegion.sequence().chain();
        ArrayList<AminoAcid> aminoAcids = new ArrayList<>();
        int i = 0;
        while(i < chain.length()){
            Codon codon = Codon.valueOf(chain.substring(i,i+3));
            aminoAcids.add(codon.aminoAcid());
            i = i + 3;
        }
        log.info("{}",aminoAcids);
        return Protein.builder()
                .aminoAcids(aminoAcids)
                .build();
    }

    public Regions regions() {
        return regions;
    }
}
