package br.com.bancodigital.model;

public class ContaCorrente extends Conta {
    public ContaCorrente(String numero, Cliente cliente, String chavePix, double limiteEspecial) {
        super(numero, cliente, chavePix, limiteEspecial);
    }

    @Override
    public String gerarExtrato() {
        return "Extrato da Conta Corrente - Número: " + getNumero() + "\nSaldo: R$ " + getSaldo();
    }

}
