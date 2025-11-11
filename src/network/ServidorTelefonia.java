package network;

import model.*;
import stream.FaturaOutputStream;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Servidor TCP para receber conexões e processar requisições de faturas
 */
public class ServidorTelefonia {
    private static final int PORTA = 5000;
    private static GerenciadorLinhas gerenciador = new GerenciadorLinhas();
    private static List<Fatura> faturasGlobais = new ArrayList<>();

    public static void main(String[] args) {
        inicializarDados();
        
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║   SERVIDOR DE TELEFONIA - PORTA " + PORTA + "           ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println("🔌 Aguardando conexões...\n");

        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("✅ Cliente conectado: " + clientSocket.getInetAddress());
                    
                    // Processar requisição do cliente
                    processarCliente(clientSocket);
                    
                } catch (IOException e) {
                    System.err.println("❌ Erro ao processar cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Erro ao iniciar servidor: " + e.getMessage());
        }
    }

    private static void processarCliente(Socket clientSocket) {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream saida = clientSocket.getOutputStream()) {
            
            String comando = entrada.readLine();
            System.out.println("📨 Comando recebido: " + comando);
            
            if ("OBTER_FATURAS".equals(comando)) {
                // Enviar faturas usando FaturaOutputStream
                enviarFaturas(saida);
                System.out.println("📤 Faturas enviadas com sucesso!");
            } else {
                String resposta = "ERRO: Comando desconhecido\n";
                saida.write(resposta.getBytes());
            }
            
            saida.flush();
            clientSocket.close();
            System.out.println("🔌 Conexão encerrada\n");
            
        } catch (IOException e) {
            System.err.println("❌ Erro na comunicação: " + e.getMessage());
        }
    }

    private static void enviarFaturas(OutputStream destino) throws IOException {
        Fatura[] faturas = faturasGlobais.toArray(new Fatura[0]);
        
        // Criar FaturaOutputStream conforme especificação
        FaturaOutputStream faturaStream = new FaturaOutputStream(
            faturas,           // Array de objetos
            faturas.length,    // Número de objetos
            20,                // bytes para numeroLinha
            15,                // bytes para dataVencimento
            12,                // bytes para valorTotal
            destino            // OutputStream destino
        );
        
        faturaStream.enviarTodos();
    }

    private static void inicializarDados() {
        System.out.println("🔧 Inicializando dados de teste...");
        
        // Criar clientes
        Cliente cliente1 = new Cliente("João Silva", "123.456.789-00", "11987654321", "joao@email.com");
        Cliente cliente2 = new Cliente("Maria Santos", "987.654.321-00", "11912345678", "maria@email.com");
        Cliente cliente3 = new Cliente("Pedro Oliveira", "456.789.123-00", "21987651234", "pedro@email.com");
        
        // Criar linhas
        Linha linha1 = gerenciador.criarLinha("11987654321", cliente1);
        linha1.adicionarServico("Internet 100MB", 89.90);
        linha1.adicionarServico("Chamadas Ilimitadas", 35.00);
        
        Linha linha2 = gerenciador.criarLinha("11912345678", cliente2);
        linha2.adicionarServico("Internet 200MB", 119.90);
        linha2.adicionarServico("SMS Ilimitado", 15.00);
        
        Linha linha3 = gerenciador.criarLinha("21987651234", cliente3);
        linha3.adicionarServico("Internet 500MB", 149.90);
        linha3.adicionarServico("Roaming Internacional", 85.00);
        
        // Gerar faturas
        faturasGlobais.add(new Fatura("11987654321", LocalDate.now().plusDays(10), 124.90, false));
        faturasGlobais.add(new Fatura("11912345678", LocalDate.now().plusDays(15), 134.90, true));
        faturasGlobais.add(new Fatura("21987651234", LocalDate.now().plusDays(12), 234.90, false));
        faturasGlobais.add(new Fatura("11987654321", LocalDate.now().minusMonths(1), 124.90, true));
        faturasGlobais.add(new Fatura("11912345678", LocalDate.now().plusDays(20), 89.90, false));
        
        System.out.println("✅ Dados inicializados: " + gerenciador.listarLinhas().size() + " linhas, " + 
                          faturasGlobais.size() + " faturas\n");
    }
}
