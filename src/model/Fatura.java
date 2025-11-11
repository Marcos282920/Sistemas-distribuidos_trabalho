package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * POJO 2 - Representa uma fatura de serviços telefônicos
 */
public class Fatura implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String numeroLinha;
    private LocalDate dataVencimento;
    private double valorTotal;
    private boolean pago;

    public Fatura() {}

    public Fatura(String numeroLinha, LocalDate dataVencimento, double valorTotal, boolean pago) {
        this.numeroLinha = numeroLinha;
        this.dataVencimento = dataVencimento;
        this.valorTotal = valorTotal;
        this.pago = pago;
    }

    public String getNumeroLinha() { return numeroLinha; }
    public void setNumeroLinha(String numeroLinha) { this.numeroLinha = numeroLinha; }
    
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    
    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
    
    public boolean isPago() { return pago; }
    public void setPago(boolean pago) { this.pago = pago; }

    @Override
    public String toString() {
        return "Fatura{linha='" + numeroLinha + "', vencimento=" + dataVencimento + 
               ", valor=R$" + String.format("%.2f", valorTotal) + ", pago=" + pago + "}";
    }
}
