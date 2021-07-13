package com.rantanplan.model;

import lombok.Builder;

@Builder
public class MRNA {
    private Sequence sequence;

    public static class MRNABuilder{
        public MRNA build(){
            return new MRNA(sequence);
        }
    }

    public Sequence sequence(){
        return sequence;
    }
}
