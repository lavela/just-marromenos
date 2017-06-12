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
public class ProcessoEntrada implements Processo {
     private List<Object> list;

    public ProcessoEntrada() {
        list = new ArrayList<>();
    }
    
    @Override
    public List<Object> verify(Token token) {
        if(token.getType() == TipoToken.TK_LEIA) {
            list.add(TipoToken.TK_LEIA);
            list.add(TipoToken.TK_ID);    
        }
        
        return list;
    }
    
    @Override
    public String tokenExpected() {
        return "Esperado um LEIA";
    }
}
