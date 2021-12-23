package com.rantanplan;

import com.rantanplan.io.Fasta;
import com.rantanplan.io.FastaReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

@Slf4j
class FastaReaderTest {

    @Test
    public void readFastaFile() throws IOException {
        String filePath = "fastas/conventionals.fasta";
        List<Fasta> fastas = new FastaReader().readFastaFile(filePath);
        log.info("{}", fastas);
        Assertions.assertNotNull(fastas);
        Assertions.assertTrue(fastas.size() > 0);
        fastas.stream().forEach(fasta -> {
            Assertions.assertNotNull(fasta.header());
            Assertions.assertNotNull(fasta.sequence());
            Assertions.assertTrue(fasta.sequence().length() > 0);
        });
    }

}
