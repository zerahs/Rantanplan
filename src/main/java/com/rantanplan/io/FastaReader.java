package com.rantanplan.io;

import com.rantanplan.model.Sequence;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FastaReader {

    public List<Fasta> readFastaFile(String filePath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        return readFromInputStream(inputStream);
    }

    private List<Fasta> readFromInputStream(InputStream inputStream)
            throws IOException {
        ArrayList<Fasta> fastas = new ArrayList<>();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            String header = null;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(">")) {
                    header = line;
                } else if (!StringUtils.isBlank(line)) {
                    fastas.add(Fasta.builder()
                            .header(header)
                            .sequence(Sequence.builder()
                                    .chain(line)
                                    .build())
                            .build());
                    header = null;
                }
            }
        }
        log.debug("fastas read {}", fastas);
        return fastas;
    }
}
