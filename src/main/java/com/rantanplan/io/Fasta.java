package com.rantanplan.io;

import com.rantanplan.model.Sequence;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

@Builder(toBuilder = true)
public class Fasta {

    private String header;
    private Sequence sequence;

    public Sequence sequence() {
        return sequence;
    }
    public String header(){
        return header;
    }

    public String toString() {
        return header + "(" + sequence.length() + " size)";
    }

    public static class FastaBuilder {
        public Fasta build() {
            headerShouldNotBeNull();
            sequenceShouldNotBeEmpty();
            return new Fasta(header, sequence);
        }

        private void headerShouldNotBeNull() {
            if (StringUtils.isBlank(header)) {
                throw new IllegalArgumentException("header is blank");
            }
        }

        private void sequenceShouldNotBeEmpty() {
            if (sequence.length() <= 0){
                throw new IllegalArgumentException("sequence length is 0");
            }
        }
    }
}
