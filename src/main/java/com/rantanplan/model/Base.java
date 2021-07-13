package com.rantanplan.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Base {
    A('A'),
    T('T'),
    G('G'),
    C('C'),
    U('U');

    private Character code;
    public Character getCode(){
        return code;
    }
}
