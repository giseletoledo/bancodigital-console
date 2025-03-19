package br.com.bancodigital.model;


public class ContaPoupanca extends Conta {
    public ContaPoupanca(String numero, Cliente cliente, String chavePix) {
        super(numero, cliente, chavePix, 0); // Conta poupança não tem limite especial
    }

    @Override
    public String gerarExtrato() {
        return "Extrato da Conta Poupança - Número: " + getNumero() + "\nSaldo: R$ " + getSaldo();
    }

}
