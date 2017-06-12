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
public class ProcessoCondicional implements Processo{
    private List<Object> list;

    public ProcessoCondicional() {
        list = new ArrayList<>();
    }
    
    @Override
    public List<Object> verify(Token token) {
        if(token.getType() == TipoToken.TK_SE) {
            list.add(TipoToken.TK_SE);
            list.add(new ProcessoCondicao());
            list.add(new ProcessoPrograma());
            list.add(TipoToken.TK_FIM_SE);
            return list;
        }
        
        return null;
    }
    
    @Override
    public String tokenExpected() {
        return "Esperado um SE";
    }
}
