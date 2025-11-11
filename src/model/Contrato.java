package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * POJO 3 - Representa um contrato de serviço telefônico
 */
public class Contrato implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String numeroContrato;
    private String nomeCliente;
    private String numeroLinha;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String plano;

    public Contrato() {}

    public Contrato(String numeroContrato, String nomeCliente, String numeroLinha, 
                   LocalDate dataInicio, LocalDate dataFim, String plano) {
        this.numeroContrato = numeroContrato;
        this.nomeCliente = nomeCliente;
        this.numeroLinha = numeroLinha;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.plano = plano;
    }

    public String getNumeroContrato() { return numeroContrato; }
    public void setNumeroContrato(String numeroContrato) { this.numeroContrato = numeroContrato; }
    
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }
    
    public String getNumeroLinha() { return numeroLinha; }
    public void setNumeroLinha(String numeroLinha) { this.numeroLinha = numeroLinha; }
    
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    
    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    
    public String getPlano() { return plano; }
    public void setPlano(String plano) { this.plano = plano; }

    @Override
    public String toString() {
        return "Contrato{numero='" + numeroContrato + "', cliente='" + nomeCliente + 
               "', linha='" + numeroLinha + "', plano='" + plano + "'}";
    }
}
