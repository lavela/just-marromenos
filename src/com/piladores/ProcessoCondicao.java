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
public class ProcessoCondicao implements Processo {
    private List<Object> list;

    public ProcessoCondicao() {
        list = new ArrayList<>();
    }
    
    @Override
    public List<Object> verify(Token token) {
        if(token.getType() == TipoToken.TK_NUMERO 
                || token.getType() == TipoToken.TK_ID) {
            list.add(new ProcessoValor());
            list.add(TipoToken.TK_RELOP);
            list.add(new ProcessoValor());
            return list;
        }
        
        return null;
    }
    
     @Override
    public String tokenExpected() {
        return "Esperado um ID/NÚMERO";
    }
}
