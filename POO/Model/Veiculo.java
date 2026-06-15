package POO.Model;

public abstract class Veiculo {
    private String marca = "", modelo = "", placa = "", cor = "Cor Não Informada", combustivel = "Não Informado";
    private int ano = 1900, quilometragem = 0;
    private double preco = 0.00, potenciaMotor = 0.0;
    private boolean disponivel = true;

    @Override
    public String toString(){
        return marca + " " + modelo + ", " + cor + ", Ano: " + ano + ", Placa: " + placa;
    }


    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setPlaca(String placa) {
        this.placa = placa.toUpperCase();
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setPotenciaMotor(double potenciaMotor) {
        this.potenciaMotor = potenciaMotor;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setQuilometragem(int quilometragem) {
        this.quilometragem = quilometragem;
    }

    public int getQuilometragem() {
        return quilometragem;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public String getCor() {
        return cor;
    }

    public int getAno() {
        return ano;
    }

    public double getPreco() {
        return preco;
    }

    public double getPotenciaMotor() {
        return potenciaMotor;
    }



}
