package br.com.bancodigital.model;

public class CartaoCredito extends Cartao {
    private double limite;
    private Fatura fatura;

    // Construtor de CartaoCredito
    public CartaoCredito(String numero, Cliente cliente) {
        super(numero, cliente); // Chama o construtor da classe base (Cartao)
        this.limite = limite;
        this.fatura = new Fatura();
    }

    // Getters e métodos específicos de CartaoCredito
    public double getLimite() {
        return limite;
    }

    //fatura
    public Fatura getFatura() {
        return fatura;
    }

    // Aplica a taxa de utilização (5% se os gastos excederem 80% do limite)
    public void aplicarTaxaUtilizacao() {
        double totalGastos = fatura.calcularTotalGastos();
        if (totalGastos > 0.8 * limite) {
            double taxa = totalGastos * 0.05; // 5% de taxa
            fatura.adicionarTaxa(taxa);
            System.out.println("Taxa de utilização aplicada: R$ " + taxa);
        }
    }

    // Aplica o seguro viagem (gratuito para Premium, R$ 50 para outros)
    public void aplicarSeguroViagem() {
        if (getCliente().getTipoCliente() == TipoCliente.PREMIUM) {
            System.out.println("Seguro viagem gratuito aplicado para cliente Premium.");
        } else {
            double taxaSeguro = 50.00; // R$ 50,00 por mês
            fatura.adicionarTaxa(taxaSeguro);
            System.out.println("Seguro viagem aplicado: R$ " + taxaSeguro);
        }
    }

    // Aplica o seguro de fraude (cobertura automática de R$ 5.000,00)
    public void aplicarSeguroFraude() {
        double valorApolice = 5000.00; // R$ 5.000,00 de cobertura
        System.out.println("Seguro de fraude aplicado com apólice de R$ " + valorApolice);
    }

    @Override
    public String gerarExtrato() {
        return String.format("Cartão de Crédito %s | Limite: R$ %.2f | Fatura: R$ %.2f",
                getNumero(), limite, fatura.calcularTotalFatura());
    }
}