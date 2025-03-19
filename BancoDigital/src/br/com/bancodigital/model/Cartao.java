package br.com.bancodigital.model;

public abstract class Cartao {
    private String numero;
    private Cliente cliente;


	public Cartao(String numero, Cliente cliente) {
        this.cliente = cliente;
        this.numero = numero; 
    }

	public String getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    // Método abstrato para gerar extrato (deve ser implementado pelas subclasses)
    public abstract String gerarExtrato();

}
