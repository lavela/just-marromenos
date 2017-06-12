/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piladores;

//import ibd.syntatic.productions.*;
//import ibd.model.Token;
//import ibd.model.TokenType;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author leandro
 */
public class Token {
    //Tipo de token
    private TipoToken type;
    
    //Itendificador que aponta o valor na tabela de simbolos
    private Integer pointer;
    
    //Linha inicial do token
    private int line;

    //Coluna Inicial do token
    private int column;

    public Token(TipoToken type) {
        this.type = type;
    }

    public Token(TipoToken type, Integer pointer, int line, int column) {
        this.type = type;
        this.pointer = pointer;
        this.line = line;
        this.column = column;
    }

    public TipoToken getType() {
        return type;
    }

    public Integer getPointer() {
        return pointer;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    
    @Override
    public String toString() {
        return String.format("<%s>", type);
    }
}
