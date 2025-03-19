package br.com.bancodigital.model;

import java.util.ArrayList;
import java.util.List;


public class Fatura {
    private List<Double> gastos; // Lista de gastos do cartão
    private List<Double> taxas;  // Lista de taxas aplicadas

    public Fatura() {
        this.gastos = new ArrayList<>();
        this.taxas = new ArrayList<>();
    }

    // Adiciona um gasto à fatura
    public void adicionarGasto(double valor) {
        if (valor > 0) {
            gastos.add(valor);
        }
    }

    // Adiciona uma taxa à fatura
    public void adicionarTaxa(double valor) {
        if (valor > 0) {
            taxas.add(valor);
        }
    }

    // Calcula o total de gastos
    public double calcularTotalGastos() {
        return gastos.stream().mapToDouble(Double::doubleValue).sum();
    }
    //Add comment
    // Calcula o total de taxas
    public double calcularTotalTaxas() {
        return taxas.stream().mapToDouble(Double::doubleValue).sum();
    }

    
    // Retorna o valor total da fatura (gastos + taxas)
    public double calcularTotalFatura() {
        return calcularTotalGastos() + calcularTotalTaxas();
    }
    
}