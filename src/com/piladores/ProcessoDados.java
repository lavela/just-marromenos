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
public class ProcessoDados implements Processo {
    private List<Object> list;

    public ProcessoDados() {
        list = new ArrayList<>();
    }
    
    @Override
    public List<Object> verify(Token token) {
        if(token.getType() == TipoToken.TK_TEXTO) {
            list.add(TipoToken.TK_TEXTO);
            return this.list;
        }
        if(token.getType() == TipoToken.TK_NUMERO) {
            list.add(TipoToken.TK_NUMERO);
            return this.list;
        }
        
        if(token.getType() == TipoToken.TK_ID) {
            list.add(TipoToken.TK_ID);
            return this.list;
        }
        
        return null;
    }
    
    @Override
    public String tokenExpected() {
        return "Esperado um TEXTO/NUMERO/IDENTIFICADOR";
    }
}
