package br.com.bancodigital.model;

public class CartaoDebito extends Cartao {
    private double limiteDiario;

    public CartaoDebito(String numero, Cliente cliente, double limiteDiario) {
		super(numero, cliente);
		this.limiteDiario = limiteDiario;
	}

	// Método para ajustar o limite diário
    public void ajustarLimiteDiario(double novoLimite) {
        if (novoLimite >= 0) {
            this.limiteDiario = novoLimite;
            System.out.println("🔄 Limite diário ajustado para: R$ " + novoLimite);
        } else {
            throw new IllegalArgumentException("O limite diário não pode ser negativo.");
        }
    }

    // Método para gerar o extrato do cartão de débito
    @Override
    public String gerarExtrato() {
        return String.format("Cartão de Débito %s | Limite Diário: R$ %.2f",
                getNumero(), limiteDiario);
    }
}