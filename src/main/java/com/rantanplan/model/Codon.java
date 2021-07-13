package com.rantanplan.model;

import static com.rantanplan.model.AminoAcid.*;

public enum Codon {
    TTT(phe), TCT(ser), TAT(tyr), TGT(cys),
    TTC(phe), TCC(ser), TAC(tyr), TGC(cys),
    TTA(leu), TCA(ser), TAA(stop), TGA(stop),
    TTG(leu), TCG(ser), TAG(stop), TGG(trp),

    CTT(leu), CCT(pro), CAT(his), CGT(arg),
    CTC(leu), CCC(pro), CAC(his), CGC(arg),
    CTA(leu), CCA(pro), CAA(gln), CGA(arg),
    CTG(leu), CCG(pro), CAG(gln), CGG(arg),

    ATT(ile), ACT(thr), AAT(asn), AGT(ser),
    ATC(ile), ACC(thr), AAC(asn), AGC(ser),
    ATA(ile), ACA(thr), AAA(lys), AGA(arg),
    ATG(met), ACG(thr), AAG(lys), AGG(arg),

    GTT(val), GCT(ala), GAT(asp), GGT(gly),
    GTC(val), GCC(ala), GAC(asp), GGC(gly),
    GTA(val), GCA(ala), GAA(glu), GGA(gly),
    GTG(val), GCG(ala), GAG(glu), GGG(gly),
    NNN(null);

    private AminoAcid aminoAcid;
    public AminoAcid aminoAcid(){
        return aminoAcid;
    }
    Codon(AminoAcid aminoAcid) {
        this.aminoAcid = aminoAcid;
    }
}
