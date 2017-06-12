/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.piladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author leandro
 */
public class AnalisadorSintatico {
     //Pilha de interação da tabela
    private Stack stack;

    //Lista de tokens gerados pelo analisador lexico
    private List<Token> listTokens;

    //Lista de com resultado da verificação de uma produção
    private List<Object> listObj;

    //Lista de erros
    private List<String> listError;

    //Construtor
    public AnalisadorSintatico(List<Token> list) {
        this.listTokens = new ArrayList<>();
        this.listTokens.addAll(list);
        this.listObj = new ArrayList<>();
        this.stack = new Stack();
        this.listError = new ArrayList<>();
    }

    public boolean hasError() {
        return listError.size() > 0;
    }

    public List<String> getError() {
        return this.listError;
    }

    //Iniciar analise sintatica
    public void start() {

        //Preenche a pilha com $, 
        //Sempre iniciamos a pilha com $ e a primeira Variavel
        stack.push(TipoToken.TK_$);
        stack.push(new ProcessoPrograma());

        //Objeto do top da pilha
        Object topObject = stack.lastElement();

        while (true) {
            //A produção que está sendo analisado nesta interação
            Processo processoAnalisar;

            //Se lista vazia, sai do loop
            if (listTokens.isEmpty()) {
                break;
            }

            //Pega objeto do topo da pilha
            topObject = stack.lastElement();

            //À comentar //Final da pilha
            if (topObject instanceof TipoToken
                    && ((TipoToken) topObject) == TipoToken.TK_$) {
                break;
            }

            //Verifica se é uma produção a ser analisada
            if (!(topObject instanceof TipoToken)) {
                processoAnalisar = (Processo) topObject;

                if (processoAnalisar instanceof ProcessoPrograma) {
                    listObj = new ProcessoPrograma().verify(listTokens.get(0));

                } else if (processoAnalisar instanceof ProcessoComando) {
                    listObj = new ProcessoComando().verify(listTokens.get(0));

                } else if (processoAnalisar instanceof ProcessoEntrada) {
                    listObj = new ProcessoEntrada().verify(listTokens.get(0));

                } else if (processoAnalisar instanceof ProcessoSaida) {
                    listObj = new ProcessoSaida().verify(listTokens.get(0));

                } else if (processoAnalisar instanceof ProcessoDados) {
                    listObj = new ProcessoDados().verify(listTokens.get(0));

                } else if (processoAnalisar instanceof ProcessoAtribuicao) {
                    listObj = new ProcessoAtribuicao().verify(listTokens.get(0));

                } else if (processoAnalisar instanceof ProcessoExpressao) {
                    listObj = new ProcessoExpressao().verify(listTokens.get(0));

                } else if (processoAnalisar instanceof ProcessoExpressaoD) {
                    listObj = new ProcessoExpressaoD().verify(listTokens.get(0));

                } else if (processoAnalisar instanceof ProcessoValor) {
                    listObj = new ProcessoValor().verify(listTokens.get(0));

                } else if (processoAnalisar instanceof ProcessoCondicional) {
                    listObj = new ProcessoCondicional().verify(listTokens.get(0));

                } else if (processoAnalisar instanceof ProcessoCondicao) {
                    listObj = new ProcessoCondicao().verify(listTokens.get(0));
                }
            } else {//Se o token da lista de tokens for igual ao token no topo da pilha
                TipoToken lastElement = (TipoToken) stack.lastElement();

                //Se o elemento do topo da lista for igual ao $ encerra o processamento
                if (lastElement == TipoToken.TK_$) {
                    break;
                }
                if (listTokens.get(0).getType() == lastElement) {
                    stack.pop();
                    listTokens.remove(0);
                    continue;
                } else { //Erro tokens diferentes
                    addError(lastElement);
                    stack.pop();
                    listTokens.remove(0);
                    continue;
                }
            }

            pushObjetOnTheStack();
        }
    }

    private void pushObjetOnTheStack() {
        //Se a lista de objeto for null então não existe associação com uma produção
        if (listObj == null) {
            Processo item = ((Processo) stack.lastElement());
            listError.add(item.tokenExpected());
            stack.pop();
            listTokens.remove(0);

            return;
        }

        //Tabela LL(1) Retornou Vazio, apenas removemos o topo da pilha.
        if ((!listObj.isEmpty()) && (listObj.get(0) == null)) {
            stack.pop();
            return;
        }

        //Remove o top da pilha, e adiciona novas produções.
        stack.pop();
        for (int i = listObj.size(); i > 0; i--) {
            stack.push(listObj.get(i - 1));
        }

        //Limpa lista de objetos.
        listObj.clear();
    }

    private void addError(TipoToken type) {

        String error = new String();

        if (type == TipoToken.TK_ESCREVA) {
            error = "ESCREVA";
        } else if (type == TipoToken.TK_LEIA) {
            error = "LEIA";
        } else if (type == TipoToken.TK_SE) {
            error = "LEIA";
        } else if (type == TipoToken.TK_FIM_SE) {
            error = "SE";
        } else if (type == TipoToken.TK_ATRIBUICAO) {
            error = "=";
        } else if (type == TipoToken.TK_OP) {
            error = "+ | - | * | /";
        } else if (type == TipoToken.TK_RELOP) {
            error = "> | < | >= | <= | ==";
        } else if (type == TipoToken.TK_TEXTO) {
            error = "TEXTO";
        } else if (type == TipoToken.TK_NUMERO) {
            error = "NUMERO";
        } else if (type == TipoToken.TK_ID) {
            error = "IDENTIFICADOR";
        }

        listError.add(String.format("%d | %d > %s", listTokens.get(0).getColumn(),
                        listTokens.get(0).getLine(), "Esperado um " + error));
    }
}
