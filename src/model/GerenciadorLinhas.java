package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de Modelo 2 - Gerencia operações sobre linhas telefônicas
 */
public class GerenciadorLinhas {
    private List<Linha> linhas;
    private List<Fatura> faturas;
    private List<Contrato> contratos;
    private int contadorContratos = 1;

    public GerenciadorLinhas() {
        this.linhas = new ArrayList<>();
        this.faturas = new ArrayList<>();
        this.contratos = new ArrayList<>();
    }

    // Operações de Linha
    public Linha criarLinha(String numero, Cliente titular) {
        Linha linha = new Linha(numero, titular);
        linhas.add(linha);
        return linha;
    }

    public Linha buscarLinha(String numero) {
        for (Linha linha : linhas) {
            if (linha.getNumero().equals(numero)) {
                return linha;
            }
        }
        return null;
    }

    public List<Linha> listarLinhas() {
        return new ArrayList<>(linhas);
    }

    // Operações de Fatura
    public Fatura gerarFatura(String numeroLinha) {
        Linha linha = buscarLinha(numeroLinha);
        if (linha == null) return null;

        LocalDate dataVencimento = LocalDate.now().plusDays(10);
        double valorTotal = linha.calcularValorTotal();
        Fatura fatura = new Fatura(numeroLinha, dataVencimento, valorTotal, false);
        faturas.add(fatura);
        return fatura;
    }

    public boolean pagarFatura(String numeroLinha) {
        for (Fatura fatura : faturas) {
            if (fatura.getNumeroLinha().equals(numeroLinha) && !fatura.isPago()) {
                fatura.setPago(true);
                return true;
            }
        }
        return false;
    }

    public List<Fatura> listarFaturas() {
        return new ArrayList<>(faturas);
    }

    public List<Fatura> listarFaturasEmAberto() {
        List<Fatura> emAberto = new ArrayList<>();
        for (Fatura fatura : faturas) {
            if (!fatura.isPago()) {
                emAberto.add(fatura);
            }
        }
        return emAberto;
    }

    // Operações de Contrato
    public Contrato criarContrato(Cliente cliente, String numeroLinha, String plano, int duracaoMeses) {
        String numeroContrato = String.format("CTR-%05d", contadorContratos++);
        LocalDate dataInicio = LocalDate.now();
        LocalDate dataFim = dataInicio.plusMonths(duracaoMeses);
        
        Contrato contrato = new Contrato(numeroContrato, cliente.getNome(), numeroLinha, 
                                         dataInicio, dataFim, plano);
        contratos.add(contrato);
        return contrato;
    }

    public boolean cancelarContrato(String numeroContrato) {
        return contratos.removeIf(c -> c.getNumeroContrato().equals(numeroContrato));
    }

    public List<Contrato> listarContratos() {
        return new ArrayList<>(contratos);
    }
}
