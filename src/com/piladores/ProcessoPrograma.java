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
public class ProcessoPrograma implements Processo {
    private List<Object> list;

    public ProcessoPrograma() {
        list = new ArrayList<>();
    }
    
    @Override
    public List<Object> verify(Token token) {
        if(token.getType() == TipoToken.TK_ESCREVA 
                || token.getType() == TipoToken.TK_LEIA
                || token.getType() == TipoToken.TK_SE) {
            list.add(new ProcessoComando());
            list.add(new ProcessoPrograma());
            return this.list;
        }
        if(token.getType() == TipoToken.TK_FIM_SE) {
            list.add(null);
            return this.list;
        }
        
        if(token.getType() == TipoToken.TK_ID 
                || token.getType() == TipoToken.TK_$) {
            list.add(new ProcessoComando());
            list.add(new ProcessoPrograma());
            return this.list;
        }
        
        return null;
    }
    
    @Override
    public String tokenExpected() {
        return "Esperado LEIA/ESCREVA/IDENTIFICADOR/NUMERO";
    }
}
