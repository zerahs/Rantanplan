package com.rantanplan;

import com.rantanplan.io.Fasta;
import com.rantanplan.io.FastaReader;
import com.rantanplan.model.MRNA;
import com.rantanplan.model.Protein;
import com.rantanplan.model.Region;
import com.rantanplan.model.Regions;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RibosomeTest {

    @Test
    public void testMRNAReader() throws IOException {
        List<Protein> proteins = new ArrayList<>();
        new FastaReader().readFastaFile("fastas/pfizer.fasta").forEach(fasta1 -> proteins.add(testMRNAReader(fasta1)));

        new FastaReader().readFastaFile("fastas/moderna.fasta").forEach(fasta1 -> proteins.add(testMRNAReader(fasta1)));

        // Check proteins are the same
        Protein protein = proteins.get(0);
        proteins.forEach(protein1 -> Assertions.assertEquals(protein, protein1));

        // TODO conventionals fasta only has the protein encoding and won't work as is
        /*new FastaReader().readFastaFile("fastas/conventionals.fasta").forEach(fasta1 -> {
            testMRNAReader(fasta1);
        });*/
    }

    private Protein testMRNAReader(Fasta fasta) {
        log.info("-----------------------------");
        log.info("-------  Read {}  -----------", fasta.header());
        log.info("-----------------------------");
        MRNA mrna = MRNA.builder()
                .sequence(fasta.sequence())
                .build();
        // create ribosome
        Ribosome ribosome = Ribosome.builder().build();
        // ribosome is bound to MRNA
        ribosome.bindToMRNA(mrna);
        Regions regions = ribosome.regions();
        Assertions.assertNotNull(regions);
        // ribosome translates to protein
        return ribosome.translate();

    }
}
