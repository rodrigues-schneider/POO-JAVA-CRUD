package POO.Control;

import POO.Model.Veiculo;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class ListaVeiculos {

    private ArrayList<Veiculo> lista = new ArrayList<>();
    private static final Scanner leitura = new Scanner(System.in);


    public void adicionar(Veiculo veiculo){
        lista.add(veiculo);
    }

    public boolean excluir(String placa){
        Veiculo veiculo = buscarPlaca(placa);
        if (buscarPlaca(placa) != null){
            lista.remove(veiculo);
            return true;
        }
        return false;
    }

    public boolean excluir(Veiculo veiculo){
        if(lista.remove(veiculo)){
            return true;
        }
        return false;
    }

    public String lerEntrada(){

        return leitura.nextLine();
    }

    public Veiculo buscarPlaca(String placa) {
        for (Veiculo veiculo : lista) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)){
                return veiculo;
            }
        }
        String mensagem = "VEICULO NÃO ENCONTRADO!";
        int tamanhoMensagem = mensagem.length();
        int espaco = 0;
        if (tamanhoMensagem < 100){
            espaco = (98 - mensagem.length())/2;
        }

        System.out.println("|" + " ".repeat(espaco) + mensagem + " ".repeat(espaco) + "|");
        System.out.println("| Pressione 'Enter' para retonar...");
        leitura.nextLine();
        return null;
    }

    public ArrayList<Veiculo> filtrar(String marca, String modelo, String cor, String anoInferior, String precoSuperior){

        ArrayList<Veiculo> listaFiltro = new ArrayList<>();

        for (Veiculo veiculo : lista) {

            boolean marcaOk = marca.isBlank() || veiculo.getMarca().equalsIgnoreCase(marca);

            boolean modeloOk = modelo.isBlank() || veiculo.getModelo().equalsIgnoreCase(modelo);

            boolean corOk = cor.isBlank() || veiculo.getCor().equalsIgnoreCase(cor);

            boolean anoOk = false;
            if (anoInferior.isBlank()){
                anoOk = true;
            } else {
                try {
                    if (Integer.valueOf(anoInferior.trim()) <= veiculo.getAno()) {
                        anoOk = true;
                    }
                } catch (NumberFormatException e) {
                    anoOk = false;
                }
            }

                boolean precoOk = false;
                if (precoSuperior.isBlank()){
                    precoOk = true;
                } else {
                    try{
                        if (Double.valueOf(precoSuperior.trim()) >= veiculo.getPreco()){
                            precoOk = true;
                        }
                    } catch (NumberFormatException e) {
                        precoOk = false;
                    }
                }

                if (marcaOk && modeloOk && corOk && anoOk && precoOk){
                    listaFiltro.add(veiculo);
                }
            }
            return listaFiltro;
        }


        public ArrayList<Veiculo> getListaVeiculos(){
            return this.lista;
        }

    }



