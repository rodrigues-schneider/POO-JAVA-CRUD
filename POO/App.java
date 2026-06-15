package POO;

import POO.Control.Conexao;
import POO.Control.TransferirDB;
import POO.View.Interface;


public class App {

    static void main(String[] args) {
        Conexao.criarDB();
        Interface.menuPrincipal();
    }


}
