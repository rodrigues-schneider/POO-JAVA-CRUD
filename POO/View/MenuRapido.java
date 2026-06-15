package POO.View;

import POO.Control.ListaVeiculos;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class MenuRapido {

    private final String[] opcoes;
    private String cabecalho;
    private final Scanner scanner = new Scanner(System.in);
    private ArrayList<String> cascataRespostas;
    private int valorInferior = 0;
    private int valorSuperior = 0;

    //Construtor do Menu:
    public MenuRapido(String cabecalho, String... opcoes){
        this.cabecalho = cabecalho;
        this.opcoes = opcoes;
        cascataRespostas = new ArrayList<>();
    }

    private String titulo() {
        int espaco = 0;
        if (cabecalho == null || cabecalho.isEmpty()){
            return "||" + "=".repeat(96) + "||";
        }
        else {
            cabecalho = cabecalho.toUpperCase();
            if (cabecalho.length() < 100) {
                espaco = (96 - cabecalho.length())/2;
            }
            return """
                
                
                ||%s||
                ||%s||
                ||%s||""".formatted("=".repeat(96), " ".repeat(espaco) + cabecalho + " ".repeat(espaco), "=".repeat(96));
        }
    }

    public int lerOpcao(){
        boolean entradaValida = false;
        int escolha = 0;
        System.out.print(":: Escolha uma opção: ");
        while(!entradaValida){
            try {
                escolha = scanner.nextInt();
                if (escolha < 0 || escolha > opcoes.length) {
                    System.out.println("Opção Inválida\nDigite um número entre \"0\" e \"" + opcoes.length + "\"");
                    scanner.nextLine();
                } else {
                    entradaValida = true;
                }
            } catch (InputMismatchException e){
                System.out.println("Você Digitou um Caractere Inválido!\nInsira um número entre \"0\" e \"" + opcoes.length + "\"");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        return escolha;
    }


    public int exibirMenuFixo(){
        System.out.println(titulo());
        int i = 1;
        int espacoTotal = 100;
        if (opcoes.length > 0) {
            for (String opcao : opcoes){

                String linha = "|| [" + i + "] " + opcao;

                int espaco = espacoTotal - linha.length() - 2;

                if (espaco < 0){espaco = 0;}

                System.out.println(linha + " ".repeat(espaco) + "||");
                i++;
            }
            String linhaSair = "|| [0] SAIR";
            int espacoSair = espacoTotal - linhaSair.length() - 2;
            System.out.println(linhaSair + " ".repeat(espacoSair) + "||");
            System.out.println("||" + "=".repeat(espacoTotal-4) + "||");
            return lerOpcao();
        }
        return -1;
    }


    public ArrayList<String> exibirMenuCascata(){
        System.out.println(titulo());
        cascataRespostas.clear();
        for (String opcao : opcoes){
            System.out.print(":: " + opcao);
            cascataRespostas.add(scanner.nextLine());

        }

        System.out.println("||" + "=".repeat(96) + "||");
        return cascataRespostas;
    }


    public ArrayList<String> getCascataRespostas() {
        return this.cascataRespostas;
    }

    public String getCascataRespostas(int indice) {
        return this.cascataRespostas.get(indice);
    }
}

