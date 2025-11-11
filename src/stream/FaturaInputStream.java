package stream;

import model.Fatura;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * FaturaInputStream - Subclasse de InputStream
 * Lê dados de faturas enviados por FaturaOutputStream
 */
public class FaturaInputStream extends InputStream {
    private InputStream origem;
    private BufferedReader reader;

    public FaturaInputStream(InputStream origem) {
        this.origem = origem;
        this.reader = new BufferedReader(new InputStreamReader(origem));
    }

    @Override
    public int read() throws IOException {
        return origem.read();
    }

    /**
     * Lê todas as faturas do stream
     */
    public List<Fatura> lerTodas() throws IOException {
        List<Fatura> faturas = new ArrayList<>();
        String linha;
        
        Fatura faturaAtual = null;
        
        while ((linha = reader.readLine()) != null) {
            linha = linha.trim();
            
            if (linha.startsWith("--- FATURA")) {
                if (faturaAtual != null) {
                    faturas.add(faturaAtual);
                }
                faturaAtual = new Fatura();
            } else if (linha.startsWith("Número da Linha") && faturaAtual != null) {
                String numeroLinha = extrairValorEntreColchetes(linha);
                faturaAtual.setNumeroLinha(numeroLinha.trim());
            } else if (linha.startsWith("Data Vencimento") && faturaAtual != null) {
                String data = extrairValorEntreColchetes(linha);
                faturaAtual.setDataVencimento(LocalDate.parse(data.trim()));
            } else if (linha.startsWith("Valor Total") && faturaAtual != null) {
                String valor = extrairValorEntreColchetes(linha);
                faturaAtual.setValorTotal(Double.parseDouble(valor.trim()));
            } else if (linha.startsWith("Status") && faturaAtual != null) {
                boolean pago = linha.contains("PAGO") && !linha.contains("EM ABERTO");
                faturaAtual.setPago(pago);
            }
        }
        
        if (faturaAtual != null) {
            faturas.add(faturaAtual);
        }
        
        return faturas;
    }

    private String extrairValorEntreColchetes(String linha) {
        int inicio = linha.indexOf("[");
        int fim = linha.indexOf("]");
        if (inicio >= 0 && fim > inicio) {
            return linha.substring(inicio + 1, fim);
        }
        return "";
    }

    @Override
    public void close() throws IOException {
        reader.close();
        origem.close();
    }
}
