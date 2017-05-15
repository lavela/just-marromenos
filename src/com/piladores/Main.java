package com.piladores;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void gravarVariavel(String nomeVariavel, String conteudoVariavel) {



    }

    // para verificar o ultimo lexema do arquivo de texto
    public static String lexemaExiste(String lexema) {

        String token = "";

        if (lexema.equals("escreva")) {
            System.out.println("tk_escreva encontrado");
            lexema = "";
            token = "tk_escreva";
        } else if (lexema.equals("leia")) {
            System.out.println("tk_leia encontrado");
            lexema = "";
            token = "tk_leia";
        } else if (lexema.matches("inicio")) {
            System.out.println("tk_inicio encontrado");
            lexema = "";
            token = "tk_inicio";
        } else if (lexema.matches("fim")) {
            System.out.println("tk_fim encontrado");
            lexema = "";
            token = "tk_fim";
        } else if (lexema.matches("se")) {
            System.out.printf("tk_se encontrado");
            lexema = "";
            token = "tk_se";
        } else if (lexema.matches("fimse")) {
            System.out.println("tk_fimse encontrado");
            lexema = "";
            token = "tk_fimse ";
        } else if (lexema.matches("\".*\"")) {
            System.out.println("tk_texto encontrado");
            lexema = "";
            token = "tk_texto ";
        } else if (lexema.matches("[a-z][a-z0-9]*")) {
            System.out.println("tk_id encontrado");
            lexema = "";
            token = "tk_id";
        } else if (lexema.matches("[0-9]+(,[0-9]+)?")) {
            System.out.println("tk_numero encontrado");
            lexema = "";
            token = "tk_numero";
        } else if (lexema.matches("\\+|\\-|\\*|/")) {
            System.out.println("tk_operador encontrado");
            lexema = "";
            token = "tk_operador";
        } else if (lexema.matches("=")) {
            System.out.println("tk_atribuicao encontrado");
            lexema = "";
            token = "tk_atribuicao";
        } else if (lexema.matches(">|<|>=|<=|==|!=")) {
            System.out.println("tk_relop encontrado");
            lexema = "";
            token = "tk_relop ";

        }

        return token;

    }

    public static void main(String[] args) throws IOException {

        FileReader inputStream = null;
        FileWriter outputStream = null;

        HashMap tabelaSimbolos = new HashMap();

        ArrayList<String> listaDeSimbolos = new ArrayList<>();



        try {
            // arquivo a ser lido
            inputStream = new FileReader("entrada.txt");
            // arquivo em que serão colocados os tokens
            outputStream = new FileWriter("saida.txt");

            //inicializa string
            String lexema = "";

            // valor da tabela ascii do caractere
            int c;

            // vai indicar quando uma string for aberta, contornando o problema dos delimitadores
            boolean abreString = false;

            // contador de linha
            int contadorLinha = 0;
            int contadorCaractere = 0;


            // enquanto quando o metodo alcanca o fim do stream ele retorna -1 e sai do loop
            while ((c = inputStream.read()) != -1) {
                contadorCaractere++;
                // concactena os caracteres numa string até que nao tenha mais nenhum caractere
                lexema += (char) c;

                // imprime o lexema no console
                System.out.println("lexema -> '" + lexema + "'");
//                System.out.println((char) c + " (" + c + ")");

                if ((char) c == '"') {
                    abreString = !abreString;
                }

                // delimitadores, 10 = quebra de linha e espaco
                // ao encontrar um desses dois caracteres, a condicao vai verificar o lexema, removendo o caractere
                if (c == 10 || c == ' ' && !abreString) {
                    // remove o ultimo caractere, que seria o delimitador, para depois comparar nos IFS
                    lexema = lexema.substring(0, lexema.length() - 1);
                    if (c == 10)
                        contadorLinha++;

//                     verificar o que corresponde ao lexema encontrado
                    if (lexema.equals("escreva")) {
                        System.out.println("tk_escreva encontrado");
                        //limpa a string, pra concatenar a partir de uma string vazia
                        lexema = "";
                        // escreve no arquivo de saida.txt
                        outputStream.write("tk_escreva ");
                    } else if (lexema.equals("leia")) {
                        System.out.println("tk_leia encontrado");
                        lexema = "";
                        outputStream.write("tk_leia ");
                    } else if (lexema.matches("inicio")) {
                        System.out.println("tk_inicio encontrado");
                        lexema = "";
                        outputStream.write("tk_inicio ");
                    } else if (lexema.matches("fim")) {
                        System.out.println("tk_fim encontrado");
                        lexema = "";
                        outputStream.write("tk_fim");
                    } else if (lexema.matches("se")) {
                        System.out.printf("tk_se encontrado");
                        lexema = "";
                        outputStream.write("tk_se ");
                    } else if (lexema.matches("fimse")) {
                        System.out.println("tk_fimse encontrado");
                        lexema = "";
                        outputStream.write("tk_fimse ");
                    } else if (lexema.matches("\".*\"")) {
                        tabelaSimbolos.put("tk_texto", lexema);
                        System.out.println("tk_texto encontrado");
                        lexema = "";
                        outputStream.write("tk_texto ");
                    } else if (lexema.matches("[a-z][a-z0-9]*")) {
                        tabelaSimbolos.put("tk_id", lexema);
                        System.out.println("tk_id encontrado");
                        lexema = "";
                        outputStream.write("tk_id ");
                    } else if (lexema.matches("[0-9]+(,[0-9]+)?")) {
                        tabelaSimbolos.put("tk_numero", lexema);
                        System.out.println("tk_numero encontrado");
                        lexema = "";
                        outputStream.write("tk_numero ");
                    } else if (lexema.matches("\\+|\\-|\\*|/")) {
                        System.out.println("tk_operador encontrado");
                        lexema = "";
                        outputStream.write("tk_operador ");
                    } else if (lexema.matches("=")) {
                        System.out.println("tk_atribuicao encontrado");
                        lexema = "";
                        outputStream.write("tk_atribuicao ");
                    } else if (lexema.matches(">|<|>=|<=|==|!=")) {
                        System.out.println("tk_relop encontrado");
                        lexema = "";
                        outputStream.write("tk_relop ");

                    } else {
                        System.out.println("tk_invalido encontrado");
                        System.out.printf("erro na linha %s e coluna %s\n", contadorLinha, contadorCaractere);
                        lexema = "";
                        outputStream.write("tk_invalido ");
                    }
                }

                // funcao que passa o lexema e verifica

//                System.out.println("ultimo lexema: " + lexemaExiste(lexema));


            }

            // escreve o ultimo lexema
            outputStream.write(lexemaExiste(lexema));

            Set set = tabelaSimbolos.entrySet();
            Iterator i = set.iterator();

            System.out.printf("");
            System.out.println("Tabela de símbolos");
            System.out.println("Chave : Valor");
            while (i.hasNext()) {

                Map.Entry me = (Map.Entry) i.next();
                System.out.print(me.getKey() + " : ");
                System.out.println(me.getValue());

            }

            System.out.println("Número de linhas: " + (contadorLinha + 1));
            System.out.println("Número de caracteres: "+ (contadorCaractere + 1));

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }


}
