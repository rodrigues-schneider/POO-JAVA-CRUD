package POO.Control;

import POO.Model.Carro;
import POO.Model.Moto;
import POO.Model.Veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class VeiculosDAO{

    private static String string1 = "";
    private static String string2 = "";
    private static String string3 = "";
    private static String placaAnterior;

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


    //VERIFICA SE EXISTE UMA TABELA COM O NOME INFORMADO E AJUSTA OS OPCIONAIS
    private static boolean validarTabela(String tabelaNome){
        if ("Carros".equals(tabelaNome)){
            definirStrings("Carros");
            return true;
        }

        if ("Motos".equals(tabelaNome)){
            definirStrings("Motos");
            return true;
        }
        System.out.println("Tabela não Encontrada");
        return false;
    }

    //VERIFICA A INSTANCIA DO VEICULO, AJUSTA OS OPCIONAIS, RETORNA A TABELA CORRETA
    private static String verificaTabela(Veiculo veiculo){
        if (veiculo instanceof Carro) {
            definirStrings("Carros");
            return "Carros";
        }
        if (veiculo instanceof Moto){
            definirStrings("Motos");
            return "Motos";
        }
        return "Erro";
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


    //CARREGA OS ITENS DO DB PARA UMA LISTAVEICULOS ESTABELECIDA
    public static ListaVeiculos carregarDB(String url, String tabelaNome){
        ListaVeiculos lista = new ListaVeiculos();

        String instrucao = "SELECT * FROM " + tabelaNome;

        try (Connection conectar = Conexao.abrirConexao(url);
             PreparedStatement comandoCarro = conectar.prepareStatement(instrucao);
             ResultSet resultado = comandoCarro.executeQuery()) {

            if (!validarTabela(tabelaNome)){
                return null;
            }

            while (resultado.next()){
                String marca = resultado.getString("marca"),
                        modelo = resultado.getString("modelo"),
                        placa = resultado.getString("placa"),
                        cor = resultado.getString("cor"),
                        combustivel = resultado.getString("combustivel");
                int ano = resultado.getInt("ano");
                int quilometragem = resultado.getInt("quilometragem");
                double preco = resultado.getDouble("preco"),
                        potenciaMotor = resultado.getDouble("potenciaMotor");
                boolean disponivel = resultado.getBoolean("disponivel"),
                        freioABS = resultado.getBoolean("freioABS"),
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
                        quilometragem,
                        preco,
                        potenciaMotor,
                        disponivel,
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

    private static String validaPlaca(Veiculo veiculo){
        if (veiculo.getPlaca().equalsIgnoreCase(placaAnterior)){
            return veiculo.getPlaca();
        }
        else {
            return placaAnterior;
        }
    }

    //RECEBE UM OU MAIS VEICULOS E ATUALIZA A TABELA CORRESPONDENTE
    public static boolean atualizarDB(Veiculo veiculo){

        try (Connection conectar = Conexao.abrirConexao(Conexao.getURL())){
            String instrucoes = """
                        UPDATE %s SET
                        marca = ?,
                        modelo = ?,
                        placa = ?,
                        cor = ?,
                        combustivel = ?,
                        ano = ?,
                        quilometragem = ?,
                        preco = ?,
                        potenciaMotor = ?,
                        disponivel = ?,
                        freioABS = ?,
                        %s = ?,
                        %s = ?,
                        %s = ?
                        
                        WHERE placa = ?
                        """.formatted(verificaTabela(veiculo), string1, string2, string3);

            try (PreparedStatement comando = conectar.prepareStatement(instrucoes)) {
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
                    i++;
                }
                else if (veiculo instanceof Moto moto) {
                    comando.setBoolean(i, moto.isFreioABS());
                    i++;
                    comando.setBoolean(i, moto.isPartidaEletrica());
                    i++;
                    comando.setBoolean(i, moto.isBau());
                    i++;
                    comando.setBoolean(i, moto.isParabrisaElevado());
                    i++;
                }

                comando.setString(i, validaPlaca(veiculo));

                comando.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Erro ao atualizar dados no SQLite: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar dados no SQLite: " + e.getMessage());
            return false;
        }

        System.out.println("Todos os veiculos foram atualizados com sucesso!");
        return true;
    }


/*
    public static void atualizarDB(ListaVeiculos carros){

        String instrucoes = """
UPDATE Carros SET
marca = ?,
modelo = ?,
cor = ?,
combustivel = ?,
ano = ?,
preco = ?,
potenciaMotor = ?,
freioABS = ?,
arCondicionado = ?,
travaEletrica = ?,
direcaoEletrica = ?

WHERE placa = ?
""";

        try (Connection conectar = Conexao.abrirConexao();
             PreparedStatement comando = conectar.prepareStatement(instrucoes)) {

            for (Object veiculo : carros.getListaVeiculos()) {
                Carro carro = (Carro) veiculo;
                comando.setString(1, carro.getMarca());
                comando.setString(2, carro.getModelo());
                comando.setString(3, carro.getCor());
                comando.setString(4, carro.getCombustivel());
                comando.setInt(5, carro.getAno());
                comando.setDouble(6, carro.getPreco());
                comando.setDouble(7, carro.getPotenciaMotor());
                comando.setBoolean(8, carro.isFreioABS());
                comando.setBoolean(9, carro.isArCondicionado());
                comando.setBoolean(10, carro.isTravaEletrica());
                comando.setBoolean(11, carro.isDirecaoEletrica());
                comando.setString(12, carro.getPlaca());

                comando.executeUpdate();
            }
            System.out.println("Todos os carros foram atualizados com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar dados no SQLite: " + e.getMessage());
        }
    }
*/

    //TENTA REMOVER UM VEICULO DO DB E RETORNA SE A OPEÇÃO FOI CONCLUIDA OU NÃO
    public static boolean remover(Veiculo veiculo) {

        String instrucao = """
        DELETE FROM %s
        WHERE placa = ?
        """.formatted(verificaTabela(veiculo));

        try (Connection conexao = Conexao.abrirConexao(Conexao.getURL());
             PreparedStatement comando = conexao.prepareStatement(instrucao)){

            comando.setString(1, veiculo.getPlaca());

            int linhasAfetadas = comando.executeUpdate();
            if (linhasAfetadas > 0){
                return true;

            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
            return false;
        }
        return false;
    }


    //TENTA ADICIONAR UM VEICULO AO DB E RETORNA SE A OPEÇÃO FOI CONCLUIDA OU NÃO
    public static boolean adicionar(String url, Veiculo veiculo) {

        String instrucao = """
                        INSERT INTO %s
                        (marca, modelo, placa, cor, combustivel, ano, quilometragem, preco, potenciaMotor, freioABS, %s, %s, %s)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                        """.formatted(verificaTabela(veiculo), string1, string2, string3);

        try (Connection conexao = Conexao.abrirConexao(Conexao.getURL());
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

    public static String getPlacaAnterior() {
        return placaAnterior;
    }

    public static void setPlacaAnterior(String placaAnterior){
        VeiculosDAO.placaAnterior = placaAnterior;
    }
}
