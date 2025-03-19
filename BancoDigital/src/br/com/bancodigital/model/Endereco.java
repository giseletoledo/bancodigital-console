package br.com.bancodigital.model;

public class Endereco {
    private String rua;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;

    // Construtor
    public Endereco(String rua, String numero, String complemento, String cidade, String estado, String cep) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    // Getters e Setters
    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    // Método para validar o CEP
    public boolean validarCEP() {
        return cep != null && cep.matches("\\d{5}-\\d{3}");
    }

    // Método para formatar o endereço
    public String formatarEndereco() {
        return String.format(
            "Rua: %s, Número: %s, Complemento: %s, Cidade: %s, Estado: %s, CEP: %s",
            rua, numero, complemento, cidade, estado, cep
        );
    }

    @Override
    public String toString() {
        return formatarEndereco();
    }
}
