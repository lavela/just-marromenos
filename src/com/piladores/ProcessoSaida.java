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
public class ProcessoSaida implements Processo {
     private List<Object> list;

    public ProcessoSaida() {
        list = new ArrayList<>();
    }
    
    @Override
    public List<Object> verify(Token token) {
        if(token.getType() == TipoToken.TK_ESCREVA) {
            list.add(TipoToken.TK_ESCREVA);
            list.add(new ProcessoDados());
            return this.list;
        }
        
        return null;
    }
    
    @Override
    public String tokenExpected() {
        return "Esperado um ESCREVA";
    }
}
