/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piladores;

import java.util.List;

/**
 *
 * @author leandro
 */
public interface Processo {
    public static final String PROGRAM = "PROGRAMA";
    public static final String COMAND = "COMANDO";
    public static final String INPUT = "ENTRADA";
    public static final String OUTPUT = "SAIDA";
    public static final String DATA = "DADO";
    public static final String ATTRIBUITION = "ATRIBUICAO";
    public static final String EXPRESSION = "EXPRESSAO";
    public static final String EXPRESSION_D = "EXPRESSAO'";
    public static final String VALUE = "VALOR";
    public static final String CONDITIONAL = "CONDICIONAL";
    public static final String CONDITION = "CONDICAO";
    
    public List<Object> verify(Token token);
    public String tokenExpected();
}
