package POO.Model;

public class Carro extends Veiculo{

    //OPCIONAIS EXCLUSIVOS
    private boolean freioABS, arCondicionado, travaEletrica, direcaoEletrica;


    //CONSTRUCTORS DA CLASSE
    public Carro(){}
    public Carro (String marca,
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
                  boolean arCondicionado,
                  boolean travaEletrica,
                  boolean direcaoEletrica
    ){
        setPreco(preco);
        setPotenciaMotor(potenciaMotor);
        setCombustivel(combustivel);
        setMarca(marca);
        setModelo(modelo);
        setPlaca(placa);
        setCor(cor);
        setQuilometragem(quilometragem);
        setDisponivel(disponivel);
        setAno(ano);
        setArCondicionado(arCondicionado);
        setTravaEletrica(travaEletrica);
        setFreioABS(freioABS);
        setDirecaoEletrica(direcaoEletrica);
    }

    public void setFreioABS(boolean freioABS) {
        this.freioABS = freioABS;
    }

    public void setArCondicionado(boolean arCondicionado) {
        this.arCondicionado = arCondicionado;
    }

    public void setTravaEletrica(boolean travaEletrica) {
        this.travaEletrica = travaEletrica;
    }

    public void setDirecaoEletrica(boolean direcaoEletrica) {
        this.direcaoEletrica = direcaoEletrica;
    }

    public boolean isFreioABS() {
        return freioABS;
    }

    public boolean isArCondicionado() {
        return arCondicionado;
    }

    public boolean isTravaEletrica() {
        return travaEletrica;
    }

    public boolean isDirecaoEletrica() {
        return direcaoEletrica;
    }

}
