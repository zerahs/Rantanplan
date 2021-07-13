package com.rantanplan.model;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
public class Region {
    RegionEnum type;
    Sequence sequence;

    public RegionEnum type(){
        return type;
    }

    public Sequence sequence(){
        return sequence;
    }

    public static class RegionBuilder {
        public Region build() {
            log.debug("{} {}", type, sequence.chain());
            sequenceShouldNotBeEmpty();
            return new Region(type, sequence);
        }
        private void sequenceShouldNotBeEmpty(){
            if(sequence.length() == 0){
                throw new IllegalArgumentException("sequenceShouldNotBeEmpty");
            }
        }
    }
    public Boolean isProtein(){
        return RegionEnum.protein.equals(type);
    }
    public String toString() {
        return type + "(" + sequence.length()+")";
    }
}
