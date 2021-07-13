package com.rantanplan.model;

import lombok.Builder;

import java.util.List;

@Builder
public class Protein {
    List<AminoAcid> aminoAcids;
}
