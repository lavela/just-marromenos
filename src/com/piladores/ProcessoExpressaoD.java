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
public class ProcessoExpressaoD implements Processo {
    private List<Object> list;

    public ProcessoExpressaoD() {
        list = new ArrayList<>();
    }

    @Override
    public List<Object> verify(Token token) {
        if (token.getType() == TipoToken.TK_ESCREVA
                || token.getType() == TipoToken.TK_LEIA
                || token.getType() == TipoToken.TK_SE
                || token.getType() == TipoToken.TK_FIM_SE
                || token.getType() == TipoToken.TK_ID
                || token.getType() == TipoToken.TK_$) {
            list.add(null);
            return list;
        }

        if (token.getType() == TipoToken.TK_OP) {
            list.add(TipoToken.TK_OP);
            list.add(new ProcessoValor());
            return list;
        }

        return null;
    }

    @Override
    public String tokenExpected() {
        return "Esperado um OPERADOR";
    }
}
