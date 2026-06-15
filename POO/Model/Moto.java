package POO.Model;

public class Moto extends Veiculo{

    //OPCIONAIS EXCLUSIVOS
    private boolean freioABS, partidaEletrica, bau, parabrisaElevado;

    //CONSTRUCTORS DA CLASSE
    public Moto(){}
    public Moto (String marca,
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
                 boolean partidaEletrica,
                 boolean bau,
                 boolean parabrisaElevado
    ){
        setPreco(preco);
        setPotenciaMotor(potenciaMotor);
        setCombustivel(combustivel);
        setMarca(marca);
        setModelo(modelo);
        setPlaca(placa.toUpperCase());
        setQuilometragem(quilometragem);
        setCor(cor);
        setAno(ano);
        setDisponivel(disponivel);
        setPartidaEletrica(partidaEletrica);
        setBau(bau);
        setFreioABS(freioABS);
        setParabrisaElevado(parabrisaElevado);
    }


    //GETTERS E SETTERS
    public void setFreioABS(boolean freioABS) {
        this.freioABS = freioABS;
    }

    public void setPartidaEletrica(boolean partidaEletrica) {
        this.partidaEletrica = partidaEletrica;
    }

    public void setBau(boolean bau) {
        this.bau = bau;
    }

    public void setParabrisaElevado(boolean parabrisaElevado) {
        this.parabrisaElevado = parabrisaElevado;
    }

    public boolean isFreioABS() {
        return freioABS;
    }

    public boolean isPartidaEletrica() {
        return partidaEletrica;
    }

    public boolean isBau() {
        return bau;
    }

    public boolean isParabrisaElevado() {
        return parabrisaElevado;
    }
}