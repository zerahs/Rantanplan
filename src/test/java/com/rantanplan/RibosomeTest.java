package com.rantanplan;

import com.rantanplan.io.Fasta;
import com.rantanplan.io.FastaReader;
import com.rantanplan.model.*;
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
        // PFIZER
        new FastaReader().readFastaFile("fastas/pfizer.fasta").forEach(fasta1 -> proteins.add(translateProtein(fasta1)));
        // MODERNA
        new FastaReader().readFastaFile("fastas/moderna.fasta").forEach(fasta1 -> proteins.add(translateProtein(fasta1)));
        // Conventionals
        new FastaReader().readFastaFile("fastas/conventionals.fasta").forEach(fasta1 -> proteins.add(translateProtein(fasta1)));

        // Check proteins are the same for all vaccines
        Protein vaccineProtein = proteins.get(0);
        proteins.forEach(protein1 -> Assertions.assertEquals(vaccineProtein, protein1));

        // parse NCOV2 protein
        Protein ncovProtein = translateProtein(new FastaReader().readFastaFile("fastas/ncov.fasta").get(0));
        // Verify the difference of the 2 proline at index 985/986
        Assertions.assertNotEquals(AminoAcid.pro, ncovProtein.getAminoAcids().get(985));
        Assertions.assertNotEquals(AminoAcid.pro, ncovProtein.getAminoAcids().get(986));
        Assertions.assertEquals(AminoAcid.pro, vaccineProtein.getAminoAcids().get(985));
        Assertions.assertEquals(AminoAcid.pro, vaccineProtein.getAminoAcids().get(986));


    }

    private Protein translateProtein(Fasta fasta) {
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
