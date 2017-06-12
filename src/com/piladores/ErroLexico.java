/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piladores;

/**
 *
 * @author leandro
 */
public class ErroLexico {
    //Linha onde ocorreu o erro
    private int line;
    
    //Coluna onde ocorreu o erro
    private int column;
    
    //Descrição do erro
    private String description;

    public ErroLexico(int line, int column, String description) {
        this.line = line;
        this.column = column;
        this.description = description;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
       return String.format("%d | %d > %s\n", line, column, description);
    }
}
