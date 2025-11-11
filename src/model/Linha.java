package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de Modelo 1 - Gerencia uma linha telefônica e seus serviços
 */
public class Linha implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String numero;
    private List<String> servicos;
    private Cliente titular;
    private double valorMensal;

    public Linha() {
        this.servicos = new ArrayList<>();
    }

    public Linha(String numero) {
        this.numero = numero;
        this.servicos = new ArrayList<>();
        this.valorMensal = 0.0;
    }

    public Linha(String numero, Cliente titular) {
        this.numero = numero;
        this.titular = titular;
        this.servicos = new ArrayList<>();
        this.valorMensal = 0.0;
    }

    public void adicionarServico(String servico, double valor) {
        servicos.add(servico);
        valorMensal += valor;
    }

    public boolean removerServico(String servico) {
        return servicos.remove(servico);
    }

    public double calcularValorTotal() {
        return valorMensal;
    }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    
    public List<String> getServicos() { return new ArrayList<>(servicos); }
    
    public Cliente getTitular() { return titular; }
    public void setTitular(Cliente titular) { this.titular = titular; }
    
    public double getValorMensal() { return valorMensal; }
    public void setValorMensal(double valorMensal) { this.valorMensal = valorMensal; }

    @Override
    public String toString() {
        return "Linha{numero='" + numero + "', titular=" + 
               (titular != null ? titular.getNome() : "N/A") + 
               ", servicos=" + servicos.size() + ", valor=R$" + String.format("%.2f", valorMensal) + "}";
    }
}
