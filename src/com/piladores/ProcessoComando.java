/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piladores;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author leandro
 */
public class ProcessoComando implements Processo {
    private List<Object> list;
    
    public ProcessoComando() {
        list = new ArrayList<>();
    }
    
    @Override
    public List<Object> verify(Token token) {
        if(token.getType() == TipoToken.TK_ESCREVA) {
            list.add(new ProcessoSaida());
            return this.list;
        }
        if(token.getType() == TipoToken.TK_LEIA) {
            list.add(new ProcessoEntrada());
            return this.list;
        }
        if(token.getType() == TipoToken.TK_SE) {
            list.add(new ProcessoCondicional());
            return this.list;
        }
        if(token.getType() == TipoToken.TK_ID) {
            list.add(new ProcessoAtribuicao());
            return this.list;
        }
        return null;
    }
    
    @Override
    public String tokenExpected() {
        return "Esperado um LEIA/ESCREVA/IDENTIFICADOR/NUMERO";
    }
}
