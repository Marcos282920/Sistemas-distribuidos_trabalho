package stream;

import model.Fatura;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * FaturaOutputStream - Subclasse de OutputStream
 * 
 * Envia dados de um array de Faturas através de um OutputStream,
 * formatando 3 atributos principais: numeroLinha, dataVencimento, valorTotal
 */
public class FaturaOutputStream extends OutputStream {
    private Fatura[] faturas;
    private int numeroObjetos;
    private int bytesNumeroLinha;
    private int bytesDataVencimento;
    private int bytesValorTotal;
    private OutputStream destino;

    /**
     * Construtor conforme especificação do trabalho
     * 
     * @param faturas Array de objetos Fatura a serem transmitidos
     * @param numeroObjetos Número de objetos que terão dados enviados pelo stream
     * @param bytesNumeroLinha Número de bytes para o atributo numeroLinha
     * @param bytesDataVencimento Número de bytes para o atributo dataVencimento
     * @param bytesValorTotal Número de bytes para o atributo valorTotal
     * @param destino OutputStream de destino onde as informações serão enviadas
     */
    public FaturaOutputStream(Fatura[] faturas, int numeroObjetos, 
                             int bytesNumeroLinha, int bytesDataVencimento, 
                             int bytesValorTotal, OutputStream destino) {
        this.faturas = faturas;
        this.numeroObjetos = Math.min(numeroObjetos, faturas.length);
        this.bytesNumeroLinha = bytesNumeroLinha;
        this.bytesDataVencimento = bytesDataVencimento;
        this.bytesValorTotal = bytesValorTotal;
        this.destino = destino;
    }

    @Override
    public void write(int b) throws IOException {
        destino.write(b);
    }

    /**
     * Envia todos os objetos Fatura para o OutputStream de destino
     */
    public void enviarTodos() throws IOException {
        // Cabeçalho
        String cabecalho = String.format("========== TRANSMISSÃO DE FATURAS ==========\n");
        cabecalho += String.format("Total de objetos: %d\n", numeroObjetos);
        cabecalho += String.format("Formato: [%d bytes | %d bytes | %d bytes]\n", 
                                   bytesNumeroLinha, bytesDataVencimento, bytesValorTotal);
        cabecalho += "============================================\n\n";
        destino.write(cabecalho.getBytes(StandardCharsets.UTF_8));

        // Enviar cada fatura
        for (int i = 0; i < numeroObjetos; i++) {
            enviarFatura(faturas[i], i + 1);
        }

        // Rodapé
        String rodape = "\n========== FIM DA TRANSMISSÃO ==========\n";
        destino.write(rodape.getBytes(StandardCharsets.UTF_8));
        destino.flush();
    }

    /**
     * Envia uma única fatura formatada
     */
    private void enviarFatura(Fatura fatura, int sequencia) throws IOException {
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format("--- FATURA #%d ---\n", sequencia));
        
        // ATRIBUTO 1: Número da Linha (formatado com bytes especificados)
        String numeroLinha = formatarCampo(fatura.getNumeroLinha(), bytesNumeroLinha);
        sb.append(String.format("  Número da Linha.: [%s] (%d bytes)\n", 
                                numeroLinha, bytesNumeroLinha));
        
        // ATRIBUTO 2: Data de Vencimento (formatado com bytes especificados)
        String dataVencimento = formatarCampo(fatura.getDataVencimento().toString(), 
                                             bytesDataVencimento);
        sb.append(String.format("  Data Vencimento.: [%s] (%d bytes)\n", 
                                dataVencimento, bytesDataVencimento));
        
        // ATRIBUTO 3: Valor Total (formatado com bytes especificados)
        String valorTotal = formatarCampo(String.format("%.2f", fatura.getValorTotal()), 
                                         bytesValorTotal);
        sb.append(String.format("  Valor Total.....: [%s] (%d bytes)\n", 
                                valorTotal, bytesValorTotal));
        
        // Atributo adicional (não conta nos 3 obrigatórios)
        sb.append(String.format("  Status..........: %s\n", 
                                fatura.isPago() ? "PAGO" : "EM ABERTO"));
        sb.append("\n");
        
        destino.write(sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Formata um campo para ter exatamente o número de bytes especificado
     * Trunca se for maior, preenche com espaços se for menor
     */
    private String formatarCampo(String valor, int numBytes) {
        if (valor == null) {
            valor = "";
        }
        
        if (valor.length() > numBytes) {
            // Trunca se for maior
            return valor.substring(0, numBytes);
        } else if (valor.length() < numBytes) {
            // Preenche com espaços à direita se for menor
            return String.format("%-" + numBytes + "s", valor);
        }
        return valor;
    }

    @Override
    public void close() throws IOException {
        if (destino != null) {
            destino.close();
        }
    }

    @Override
    public void flush() throws IOException {
        if (destino != null) {
            destino.flush();
        }
    }
}
