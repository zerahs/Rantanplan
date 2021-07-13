package com.rantanplan.model;

import lombok.Builder;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public class Sequence {
    private String chain;

    public long length(){
        return chain.length();
    }

    public String chain(){
        return chain;
    }

    public static class SequenceBuilder{
        public Sequence build(){
            chainShouldContainValidBases();
            return new Sequence(chain);
        }

        private void chainShouldContainValidBases(){
            Set<Character> codes = Arrays.stream(Base.values()).map(Base::getCode).collect(Collectors.toSet());
            if(!codes.containsAll(chain.chars().mapToObj(o->(char)o).collect(Collectors.toSet()))){
                throw new IllegalArgumentException("chain should only contain valid bases");
            }
        }
    }
}
