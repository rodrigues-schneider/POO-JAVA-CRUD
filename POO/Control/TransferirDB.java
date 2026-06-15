package POO.Control;

import POO.Model.Carro;
import POO.Model.Moto;
import POO.Model.Veiculo;

import java.sql.*;

public final class TransferirDB {

    private static String string1 = "", string2 = "", string3 = "";

    public static void transferir(String dbAntigaURL, String dbNovaURL, String tabela) {
        ListaVeiculos listaVeiculos = TransferirDB.carregarDBAntiga(dbAntigaURL, tabela);
        for (Veiculo veiculo : listaVeiculos.getListaVeiculos()) {
            TransferirDB.adicionar(dbNovaURL, veiculo, tabela);
        }
    }

    private static ListaVeiculos carregarDBAntiga(String url, String tabelaNome){
        ListaVeiculos lista = new ListaVeiculos();
        definirStrings(tabelaNome);
        String instrucao = "SELECT * FROM " + tabelaNome;

        try (Connection conectar = Conexao.abrirConexao(url);
             PreparedStatement comando = conectar.prepareStatement(instrucao);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()){
                String marca = resultado.getString("marca"),
                        modelo = resultado.getString("modelo"),
                        placa = resultado.getString("placa"),
                        cor = resultado.getString("cor"),
                        combustivel = resultado.getString("combustivel");
                int ano = resultado.getInt("ano");
                double preco = resultado.getDouble("preco"),
                        potenciaMotor = resultado.getDouble("potenciaMotor");
                boolean freioABS = resultado.getBoolean("freioABS"),
                        bool1 = resultado.getBoolean(string1),
                        bool2 = resultado.getBoolean(string2),
                        bool3 = resultado.getBoolean(string3);

                var veiculo = criarVeiculo(tabelaNome,
                        marca,
                        modelo,
                        placa,
                        cor,
                        combustivel,
                        ano,
                        0,
                        preco,
                        potenciaMotor,
                        true,
                        freioABS,
                        bool1,
                        bool2,
                        bool3);
                lista.adicionar(veiculo);

            }
        }
        catch (SQLException e) {
            System.out.println("Erro ao buscar dados do SQLite: " + e.getMessage());
        }
        return lista;
    }

    //RETORNA UM NOVO OBJETO DE ACORDO COM AS INFORMAÇÕES INSERIDAS
    private static Veiculo criarVeiculo(String tabelaNome,
                                        String marca,
                                        String modelo,
                                        String placa,
                                        String cor,
                                        String combustivel,
                                        int ano,
                                        int quilometragem,
                                        double preco,
                                        double potenciaMotor,
                                        boolean disponivel,
                                        boolean freioABS,
                                        boolean bool1,
                                        boolean bool2,
                                        boolean bool3) {
        if ("Carros".equals(tabelaNome)) {
            return new Carro(marca,
                    modelo,
                    placa,
                    cor,
                    combustivel,
                    ano,
                    quilometragem,
                    preco,
                    potenciaMotor,
                    disponivel,
                    freioABS,
                    bool1,
                    bool2,
                    bool3);
        }
        if ("Motos".equals(tabelaNome)) {
            return new Moto(marca,
                    modelo,
                    placa,
                    cor,
                    combustivel,
                    ano,
                    quilometragem,
                    preco,
                    potenciaMotor,
                    disponivel,
                    freioABS,
                    bool1,
                    bool2,
                    bool3);
        }
        throw new IllegalArgumentException(
                "Tabela inválida: " + tabelaNome);
    }

    //AJUSTA OS VALORES DAS STRINGS À PARTRIR DA TABELA
    private static void definirStrings(String tabelaNome){
        if ("Carros".equals(tabelaNome)){
            string1 = "arCondicionado";
            string2 = "travaEletrica";
            string3 = "direcaoEletrica";
        }
        if ("Motos".equals(tabelaNome)){
            string1 = "partidaEletrica";
            string2 = "bau";
            string3 = "parabrisaElevado";
        }
    }

    //TENTA ADICIONAR UM VEICULO AO DB E RETORNA SE A OPEÇÃO FOI CONCLUIDA OU NÃO
    public static boolean adicionar(String url, Veiculo veiculo, String tabela) {

        String instrucao = """
                        INSERT INTO %s
                        (marca, modelo, placa, cor, combustivel, ano, quilometragem, preco, potenciaMotor, disponivel, freioABS, %s, %s, %s)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                        """.formatted(tabela, string1, string2, string3);

        try (Connection conexao = Conexao.abrirConexao(url);
             PreparedStatement comando = conexao.prepareStatement(instrucao)){

            int i = 1;
            comando.setString(i, veiculo.getMarca());
            i++;
            comando.setString(i, veiculo.getModelo());
            i++;
            comando.setString(i, veiculo.getPlaca());
            i++;
            comando.setString(i, veiculo.getCor());
            i++;
            comando.setString(i, veiculo.getCombustivel());
            i++;
            comando.setInt(i, veiculo.getAno());
            i++;
            comando.setInt(i, veiculo.getQuilometragem());
            i++;
            comando.setDouble(i, veiculo.getPreco());
            i++;
            comando.setDouble(i, veiculo.getPotenciaMotor());
            i++;
            comando.setBoolean(i, veiculo.isDisponivel());
            i++;


            // VERIFICA O TIPO E FAZ O TIPE CASTING
            if (veiculo instanceof Carro carro) {
                comando.setBoolean(i, carro.isFreioABS());
                i++;
                comando.setBoolean(i, carro.isArCondicionado());
                i++;
                comando.setBoolean(i, carro.isTravaEletrica());
                i++;
                comando.setBoolean(i, carro.isDirecaoEletrica());
            }
            else if (veiculo instanceof Moto moto) {
                comando.setBoolean(i, moto.isFreioABS());
                i++;
                comando.setBoolean(i, moto.isPartidaEletrica());
                i++;
                comando.setBoolean(i, moto.isBau());
                i++;
                comando.setBoolean(i, moto.isParabrisaElevado());
            }

            int linhasAfetadas = comando.executeUpdate();
            if (linhasAfetadas > 0) {
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }


}