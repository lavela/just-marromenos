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
public enum TipoToken {
    TK_ESCREVA("escreva"), 
    TK_LEIA("leia"), 
    TK_SE("se"), 
    TK_FIM_SE("fimse"), 
    TK_ATRIBUICAO("="), 
    TK_OP("[*|/|+|-]"), 
    TK_RELOP(">|<|>=|<=|!=|=="), 
    TK_TEXTO("\"[^\"]*\""), 
    TK_NUMERO("[0-9]+(,[0-9]+)?"), 
    TK_ID("[a-zA-Z][0-9a-zA-Z]*"),
    TK_DELIM("[ \n\r\t]*"),
    TK_$("$");

    public String padrao;

    TipoToken(String padrao) {
        this.padrao = padrao;
    } 
}
