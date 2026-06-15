package POO.Control;

import java.sql.*;

public final class Conexao {

    private static final String url = "jdbc:sqlite:DB/VeiculosDB.db";

    public static String getURL(){
        return url;
    }

    public static Connection abrirConexao(String url) throws SQLException {
        Connection conectar = DriverManager.getConnection(url);
        return conectar;
    }

    private static String criarDBCarros = """
                CREATE TABLE IF NOT EXISTS Carros (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                marca TEXT NOT NULL,
                modelo TEXT NOT NULL,
                placa TEXT NOT NULL UNIQUE,
                cor TEXT,
                combustivel TEXT,
                ano INTEGER,
                quilometragem INTEGER,
                preco REAL NOT NULL DEFAULT 0.0,
                potenciaMotor REAL,
                disponivel INTEGER NOT NULL DEFAULT 1,
                freioABS INTEGER NOT NULL DEFAULT 0,
                arCondicionado INTEGER NOT NULL DEFAULT 0,
                travaEletrica INTEGER NOT NULL DEFAULT 0,
                direcaoEletrica INTEGER NOT NULL DEFAULT 0
                );""";

    private static String criarDBMotos = """
                CREATE TABLE IF NOT EXISTS Motos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                marca TEXT NOT NULL,
                modelo TEXT NOT NULL,
                placa TEXT NOT NULL UNIQUE,
                cor TEXT,
                combustivel TEXT,
                ano INTEGER,
                quilometragem INTEGER,
                preco REAL NOT NULL DEFAULT 0.0,
                potenciaMotor REAL,
                disponivel INTEGER NOT NULL DEFAULT 1,
                freioABS INTEGER NOT NULL DEFAULT 0,
                partidaEletrica INTEGER NOT NULL DEFAULT 0,
                bau INTEGER NOT NULL DEFAULT 0,
                parabrisaElevado INTEGER NOT NULL DEFAULT 0
                );""";

       public static void criarDB(){


        try (Connection conexao = abrirConexao(Conexao.getURL())){
            if (conexao != null) {
                System.out.println("Conexão Estabelecida e Banco de Dados Criado");
            }
            Statement statement = conexao.createStatement();
            statement.execute(criarDBCarros);
            System.out.println("Tabela 'Carros' criada com sucesso.");

            statement.execute(criarDBMotos);
            System.out.println("Tabela 'Motos' criada com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao criar tabelas: " + e.getMessage());

        }
    }


}