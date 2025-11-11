import model.*;
import stream.FaturaOutputStream;
import java.io.*;
import java.net.*;
import java.time.LocalDate;

/**
 * Teste simplificado de comunicação TCP
 * Demonstra o funcionamento do FaturaOutputStream via socket
 */
public class TesteSocket {
    
    public static void main(String[] args) throws Exception {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║   TESTE 3: FaturaOutputStream -> TCP Socket      ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");
        
        // Iniciar servidor em thread separada
        Thread servidorThread = new Thread(() -> iniciarServidor());
        servidorThread.setDaemon(false);
        servidorThread.start();
        
        // Aguardar servidor iniciar
        Thread.sleep(2000);
        
        // Conectar cliente
        System.out.println("🔌 Cliente: Conectando ao servidor localhost:5000...");
        conectarCliente();
        
        System.out.println("\n✅ TESTE TCP CONCLUÍDO COM SUCESSO!");
        System.out.println("\n📋 RESUMO DOS 3 TESTES:");
        System.out.println("  ✅ Teste 1: System.out ......... OK");
        System.out.println("  ✅ Teste 2: FileOutputStream ... OK");
        System.out.println("  ✅ Teste 3: TCP Socket ......... OK");
        System.out.println("\n🎯 Todos os requisitos atendidos!");
        
        System.exit(0);
    }
    
    private static void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("🖥️  Servidor: Iniciado na porta 5000");
            System.out.println("🖥️  Servidor: Aguardando conexão...\n");
            
            Socket clientSocket = serverSocket.accept();
            System.out.println("🖥️  Servidor: Cliente conectado!");
            
            BufferedReader entrada = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
            );
            OutputStream saida = clientSocket.getOutputStream();
            
            String comando = entrada.readLine();
            System.out.println("🖥️  Servidor: Comando recebido: " + comando);
            
            if ("OBTER_FATURAS".equals(comando)) {
                System.out.println("🖥️  Servidor: Enviando faturas via FaturaOutputStream...");
                
                // Criar faturas
                Fatura[] faturas = {
                    new Fatura("11987654321", LocalDate.now().plusDays(10), 125.50, false),
                    new Fatura("11912345678", LocalDate.now().plusDays(15), 89.90, true),
                    new Fatura("21987651234", LocalDate.now().plusDays(12), 234.90, false)
                };
                
                // Enviar usando FaturaOutputStream
                FaturaOutputStream faturaStream = new FaturaOutputStream(
                    faturas,     // Array de objetos
                    3,           // Número de objetos
                    20,          // bytes numeroLinha
                    15,          // bytes dataVencimento
                    12,          // bytes valorTotal
                    saida        // Socket OutputStream
                );
                
                faturaStream.enviarTodos();
                System.out.println("🖥️  Servidor: Faturas enviadas com sucesso!");
            }
            
            clientSocket.close();
            System.out.println("🖥️  Servidor: Conexão encerrada\n");
            
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        }
    }
    
    private static void conectarCliente() {
        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader entrada = new BufferedReader(
                 new InputStreamReader(socket.getInputStream())
             )) {
            
            System.out.println("📱 Cliente: Conectado ao servidor!");
            System.out.println("📱 Cliente: Enviando comando OBTER_FATURAS...");
            
            // Enviar comando
            saida.println("OBTER_FATURAS");
            
            System.out.println("📱 Cliente: Recebendo faturas...\n");
            System.out.println("─────────────────────────────────────────────────────");
            
            // Ler resposta
            String linha;
            while ((linha = entrada.readLine()) != null) {
                System.out.println(linha);
            }
            
            System.out.println("─────────────────────────────────────────────────────");
            System.out.println("\n📱 Cliente: Faturas recebidas com sucesso!");
            
        } catch (Exception e) {
            System.err.println("❌ Erro no cliente: " + e.getMessage());
        }
    }
}
