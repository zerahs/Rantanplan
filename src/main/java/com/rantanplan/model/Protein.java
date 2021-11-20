package com.rantanplan.model;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Builder
@Slf4j
public class Protein {
    List<AminoAcid> aminoAcids;

    public List<AminoAcid> getAminoAcids() {
        return aminoAcids;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Protein)) {
            return false;
        }
        Protein protein = (Protein) o;

        for (int idx = 0; idx < aminoAcids.size() - 1; idx++) {
            AminoAcid a1 = aminoAcids.get(idx);
            AminoAcid a2 = protein.getAminoAcids().get(idx);
            if (a1 != a2) {
                log.info("Difference at index {} : {} != {}", idx, a1, a2);
                return false;
            }
        }
        return true;


    }
}
