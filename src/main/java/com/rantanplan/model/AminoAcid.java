package com.rantanplan.model;

public enum AminoAcid {
    ala("alanine", "A"),
    arg("arginine", "R"),
    asn("asparagine", "N"),
    asp("aspartic acid", "D"),
    cys("cysteine", "C"),
    gln("glutamine", "Q"),
    glu("glutamic acid", "E"),
    gly("glycine", "G"),
    his("histidine", "H"),
    ile("isoleucine", "I"),
    leu("leucine", "L"),
    lys("lysine", "K"),
    met("methionine", "M"),
    phe("phenylalanine", "F"),
    pro("proline", "P"),
    ser("serine", "S"),
    thr("threonine", "T"),
    trp("tryptophan", "W"),
    tyr("tyrosine", "Y"),
    val("valine", "V"),
    stop(null, null);

    private String name;
    private String symbol;

    AminoAcid(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }
}
