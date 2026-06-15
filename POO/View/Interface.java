package POO.View;

import POO.Control.ListaVeiculos;
import POO.Control.VeiculosDAO;
import POO.Control.Conexao;
import POO.Model.Carro;
import POO.Model.Moto;
import POO.Model.Veiculo;

import java.time.Year;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public final class Interface {

    private static ListaVeiculos listaCarros = VeiculosDAO.carregarDB(Conexao.getURL(), "Carros");
    private static ListaVeiculos listaMotos = VeiculosDAO.carregarDB(Conexao.getURL(),"Motos");
    private static final Scanner leitura = new Scanner(System.in);
    private static Veiculo carro;
    private static Veiculo moto;

    /*
    private static void verificaLista(ListaVeiculos lista, String tabela){
        ListaVeiculos teste = VeiculosDAO.carregarDB(tabela);
        if (teste != null){
            lista = teste;
        }
    }

     */

    //MENU PRINCIPAL
    public static void menuPrincipal() {

        var menuPrincipal = new MenuRapido("Menu Principal", "Cadastrar Novo Carro", "Cadastrar Nova Moto", "Listar Carros", "Listar Motos");

        switch (menuPrincipal.exibirMenuFixo()) {
            case 1:
                carro = new Carro();
                menuAdd(carro);
                break;

            case 2:
                moto = new Moto();
                menuAdd(moto);
                break;

            case 3:
                menuListar(listaCarros, "Carros");
                break;

            case 4:
                menuListar(listaMotos, "Motos");
                break;

            case 0:
                System.out.println("|" + "=".repeat(14) + " APLICAÇÃO ENCERRADA! " + "=".repeat(14) + "|");
                break;
        }

    }

    //MENU EXIBE LISTA DE VEICULOS
    private static void menuListar(ListaVeiculos lista, String tabela) {

        var menuListar = new MenuRapido(" LISTA DE " + tabela);
        menuListar.exibirMenuFixo();
        int c = 0;
        ArrayList<Veiculo> veiculos = lista.getListaVeiculos();
        int espacoTotal = 100;
        for (Veiculo veiculo : veiculos) {
            c++;
            String linha = ("|| " + c + ".  " + veiculo);
            int espaco = espacoTotal - linha.length() - 2;
            System.out.println(linha + " ".repeat(espaco) + "||");
        }
        System.out.println("||" + "=".repeat(espacoTotal-4) + "||");

        MenuRapido menuLista = new MenuRapido("", "Selecionar Veiculo", "Filtrar");

        switch (menuLista.exibirMenuFixo()) {
            case 1:
                menuSelecionar(lista);
                break;
            case 2:
                menuFiltrar(lista);
                break;
            case 0:
                menuPrincipal();
                break;
        }
    }

    //APLICA UM FILTRO E EXIBE SOMENTE OS VEICULOS RELEVANTES
    private static void menuFiltrar(ListaVeiculos lista) {
        MenuRapido menuFiltrar = new MenuRapido("Filtrar Veiculos",
                "Insira a Marca ('ENTER' para ignorar): ",
                "Insira o Modelo ('ENTER' para ignorar): ",
                "Insira a Cor ('ENTER' para ignorar): ",
                "Insira o Ano de Fabricação INICIAL ('ENTER' para ignorar): ",
                "Insira o Preço LIMITE ('ENTER' para ignorar): "
        );

        var resposta = menuFiltrar.exibirMenuCascata();

        ArrayList<Veiculo> filtro = lista.filtrar(resposta.get(0), resposta.get(1), resposta.get(2), resposta.get(3), resposta.get(4));
        int v = 1;

        for (Veiculo veiculo : filtro) {
            System.out.println(v + ". " + veiculo);
            v++;
        }

        MenuRapido opcoesFiltrar = new MenuRapido("", "Selecionar Veiculo");

        switch (opcoesFiltrar.exibirMenuFixo()) {
            case 1:
                menuSelecionar(lista);
                break;
            case 0:
                menuPrincipal();
                break;
        }
    }

    //PROCURA UM VEICULO ESPECIFICO À PARTIR DA PLACA
    private static void menuSelecionar(ListaVeiculos lista) {
        MenuRapido menuBuscar = new MenuRapido("Selecionar Veiculo", "Insira a Placa ou '0' para CANCELAR: ");
        Veiculo veiculo = null;
        do {
            menuBuscar.exibirMenuCascata();
            String resposta = menuBuscar.getCascataRespostas(0);
            if ("0".equals(resposta)){
                menuPrincipal();
                return;
            }
            veiculo = lista.buscarPlaca(resposta);
        } while (veiculo == null);

        menuVeiculoInfo(veiculo);
    }


    private static void menuExcluir(ListaVeiculos lista, Veiculo veiculo) {
        System.out.println("\nTem certeza que deseja excluir [" + veiculo + "]?\n1. EXCLUIR\n0. CANCELAR");

        switch (leitura.nextInt()) {
            case 1:
                if ((veiculo instanceof Carro c && VeiculosDAO.remover(c)) ||
                        (veiculo instanceof Moto m && VeiculosDAO.remover(m))) {
                    lista.excluir(veiculo);
                    System.out.println("Veiculo Removido do Sistema!");
                }
                System.out.println("Pressione 'Enter' para voltar ao Menu Principal...");
                leitura.nextLine();
                leitura.nextLine();
                menuPrincipal();
                break;

            case 0:
                leitura.nextLine();
                menuPrincipal();
                break;
        }
    }

    private static void menuVeiculoInfo(Veiculo veiculo) {
        ListaVeiculos lista = new ListaVeiculos();
        int opcao;
        do {
            ArrayList<String> opcionais = new ArrayList<>();
            if (veiculo instanceof Carro c) {
                lista = listaCarros;
                opcionais.add("Freio ABS" + verificaItem(c.isFreioABS()));
                opcionais.add("Ar Condicionado" + verificaItem(c.isArCondicionado()));
                opcionais.add("Trava Eletrica" + verificaItem(c.isTravaEletrica()));
                opcionais.add("Direção Elétrica" + verificaItem(c.isDirecaoEletrica()));
            }
            if (veiculo instanceof Moto m) {
                lista = listaMotos;
                opcionais.add("Freio ABS" + verificaItem(m.isFreioABS()));
                opcionais.add("Partida Eletrica" + verificaItem(m.isPartidaEletrica()));
                opcionais.add("Baú Traseiro" + verificaItem(m.isBau()));
                opcionais.add("Parabrisa Elevado" + verificaItem(m.isParabrisaElevado()));
            }
            String disponivel;
            if (veiculo.isDisponivel()){
                disponivel = "Em Estoque";
            } else {
                disponivel = "Vendido";
            }

            MenuRapido menu = new MenuRapido("INFORMAÇÕES DO VEICULO", "Marca: " + veiculo.getMarca(),
                    "Modelo: " + veiculo.getModelo(),
                    "Placa: " + veiculo.getPlaca(),
                    "Cor: " + veiculo.getCor(),
                    "Combustível: " + veiculo.getCombustivel(),
                    "Ano: " + veiculo.getAno(),
                    "Quilometragem: " + veiculo.getQuilometragem(),
                    "Preço: R$ " + veiculo.getPreco(),
                    "Potencia (CV): " + veiculo.getPotenciaMotor(),
                    opcionais.get(0),
                    opcionais.get(1),
                    opcionais.get(2),
                    opcionais.get(3),
                    "Disponibilidade: " + disponivel,
                    ":::REMOVER VEICULO:::"
            );
            opcao = menu.exibirMenuFixo();
            switch (opcao) {
                case 1 -> {
                    System.out.print("Nova Marca: ");
                    veiculo.setMarca(leitura.nextLine());
                }
                case 2 -> {
                    System.out.print("Novo Modelo: ");
                    veiculo.setModelo(leitura.nextLine());
                }
                case 3 -> {
                    VeiculosDAO.setPlacaAnterior(veiculo.getPlaca());

                    System.out.print("Nova Placa: ");
                    veiculo.setPlaca(leitura.nextLine());

                    if (!VeiculosDAO.atualizarDB(veiculo)) {
                        veiculo.setPlaca(VeiculosDAO.getPlacaAnterior());
                    }
                }
                case 4 -> {
                    System.out.print("Nova Cor: ");
                    veiculo.setCor(leitura.nextLine());
                }
                case 5 -> {
                    System.out.print("Novo Combustível: ");
                    veiculo.setCombustivel(leitura.nextLine());
                }
                case 6 -> {
                    System.out.print("Novo Ano: ");
                    veiculo.setAno(leitura.nextInt());
                    leitura.nextLine();
                }
                case 7 -> {
                    System.out.print("Nova Quilometragem: ");
                    veiculo.setQuilometragem(leitura.nextInt());
                    leitura.nextLine();
                }
                case 8 -> {
                    System.out.print("Novo Preço: ");
                    veiculo.setPreco(leitura.nextDouble());
                    leitura.nextLine();
                }
                case 9 -> {
                    System.out.print("Nova Potencia (CV): ");
                    veiculo.setPotenciaMotor(leitura.useLocale(Locale.US).nextDouble());
                    leitura.useLocale(Locale.getDefault());
                    leitura.nextLine();
                }
                case 14 -> {
                    veiculo.setDisponivel(!veiculo.isDisponivel());
                    System.out.print("        ::Item Atualizado!::");
                    System.out.println("Pressione 'ENTER' para prosseguir...");
                    leitura.nextLine();
                }
                case 15 -> {
                    menuExcluir(lista, veiculo);
                    break;
                }

                case 10, 11, 12, 13 -> {

                    if (veiculo instanceof Carro c) {
                        switch (opcao) {
                            case 10 -> {
                                c.setFreioABS(!c.isFreioABS());
                                System.out.print("        ::Item Atualizado!::");
                                System.out.println("Pressione 'ENTER' para prosseguir...");
                                leitura.nextLine();
                            }
                            case 11 -> {
                                c.setArCondicionado(!c.isArCondicionado());
                                System.out.print("        ::Item Atualizado!::");
                                System.out.println("Pressione 'ENTER' para prosseguir...");
                                leitura.nextLine();
                            }
                            case 12 -> {
                                c.setTravaEletrica(!c.isTravaEletrica());
                                System.out.print("        ::Item Atualizado!::");
                                System.out.println("Pressione 'ENTER' para prosseguir...");
                                leitura.nextLine();
                            }
                            case 13 -> {
                                c.setDirecaoEletrica(!c.isDirecaoEletrica());
                                System.out.print("        ::Item Atualizado!::");
                                System.out.println("Pressione 'ENTER' para prosseguir...");
                                leitura.nextLine();
                            }
                        }
                    }

                    if (veiculo instanceof Moto m) {
                        switch (opcao) {
                            case 10 -> {
                                m.setFreioABS(!m.isFreioABS());
                                System.out.print("        ::Item Atualizado!::");
                                System.out.println("Pressione 'ENTER' para prosseguir...");
                                leitura.nextLine();
                            }
                            case 11 -> {
                                m.setPartidaEletrica(!m.isPartidaEletrica());
                                System.out.print("        ::Item Atualizado!::");
                                System.out.println("Pressione 'ENTER' para prosseguir...");
                                leitura.nextLine();
                            }
                            case 12 -> {
                                m.setBau(!m.isBau());
                                System.out.print("        ::Item Atualizado!::");
                                System.out.println("Pressione 'ENTER' para prosseguir...");
                                leitura.nextLine();
                            }
                            case 13 -> {
                                m.setParabrisaElevado(!m.isParabrisaElevado());
                                System.out.print("        ::Item Atualizado!::");
                                System.out.println("Pressione 'ENTER' para prosseguir...");
                                leitura.nextLine();
                            }
                        }
                    }
                }

            }
        } while (opcao != 0);
        VeiculosDAO.atualizarDB(veiculo);
        menuPrincipal();
    }

    private static String verificaItem(Boolean estado) {
        if (estado == null || !estado) {
            return " - Não Incluido";
        }
        return " - Incluido";
    }

    //MENU ADD NOVO VEICULO
    private static void menuAdd(Veiculo veiculo) {
        var menuAdd = new MenuRapido("Cadastrar Veiculo", "[OBRIGATÓRIO]Insira a Marca: ",
                "[OBRIGATÓRIO]Insira o Modelo: ",
                "[OBRIGATÓRIO]Insira a Placa: ");

        int ano = 0;
        menuAdd.exibirMenuCascata();
        var atributos = menuAdd.getCascataRespostas();
        System.out.print("[OBRIGATÓRIO]Insira o Ano de Fabricação: ");
        do {
            int anoInserido = leitura.nextInt();
            int anoAtual = Year.now().getValue();
            if (anoInserido < 1920 || anoInserido > anoAtual){
                System.out.print("          Ano Inválido!\nInsira um ANO entre 1920 e " + anoAtual + ": ");
            } else {
                ano = anoAtual;
            }
        } while (ano == 0);

        veiculo.setMarca(atributos.get(0));
        veiculo.setModelo(atributos.get(1));
        veiculo.setPlaca(atributos.get(2));
        veiculo.setAno(ano);

        if (veiculo instanceof Carro) {
            listaCarros.adicionar(veiculo);

        } else if (veiculo instanceof Moto){
            listaMotos.adicionar(veiculo);
        }

        if (VeiculosDAO.adicionar(Conexao.getURL(), veiculo)) {
            String texto = "Novo Veiculo Registrado!";
            int espaco = (texto.length() - 98)/2;
            System.out.println("|" + "=".repeat(espaco) + texto + "=".repeat(espaco) + "|");
        }

        menuVeiculoInfo(veiculo);
    }

}

