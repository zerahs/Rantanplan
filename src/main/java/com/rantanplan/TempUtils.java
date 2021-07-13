package com.rantanplan;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

@Slf4j
public class TempUtils {

    private static final String LYSINE_CODON = "AAA";

    private void oldParsingFivePUTR(String chain){
        chain = chain.substring(2);
        StringReader reader = new StringReader(chain);
        char[] codon = new char[3];
        String fiveUTRChain = "";
        while(true){
            try {
                if (!(reader.read(codon, 0, 3) > 0)) break;
                String codonStr = String.valueOf(codon);
                if(codonStr.equals("ATG") ){ // Start codon
                    log.warn("ATG found !");
                    break;
                }
                log.info(codonStr);
                fiveUTRChain = fiveUTRChain.concat(codonStr+"");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reader.close();
    }

    private ArrayList<String> readUntilPolyA(String chain) {
        StringReader reader = new StringReader(chain);
        char[] codon = new char[3];
        ArrayList<String> codons = new ArrayList<>();
        int nbConsecutiveLysine = 0;
        while (true) {
            try {
                if (!(reader.read(codon, 0, 3) > 0)) break;
                String codonStr = String.valueOf(codon);
                // if more than 12 consecutive A, it's polyA fill
                if(nbConsecutiveLysine >= 4){
                    break;
                }
                // If lysine, this may be the beginning of end polyA fill sequence
                if (LYSINE_CODON.equals(codonStr)) {
                    nbConsecutiveLysine++;
                    continue;
                }
                // It wasn't the end, so lysine should be re-added to codons list
                else if(nbConsecutiveLysine != 0){
                    codons.add(LYSINE_CODON);
                }
                nbConsecutiveLysine=0;
                codons.add(codonStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        reader.close();
        return codons;
    }
}
