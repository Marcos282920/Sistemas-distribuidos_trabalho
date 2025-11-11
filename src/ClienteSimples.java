import java.io.*;
import java.net.Socket;

/**
 * Cliente simples para teste rápido de conexão TCP
 */
public class ClienteSimples {
    public static void main(String[] args) {
        System.out.println("=== CLIENTE SIMPLES - TESTE TCP ===\n");
        
        try (Socket socket = new Socket("localhost", 5000);
             PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            System.out.println("✅ Conectado ao servidor!");
            System.out.println("📤 Enviando comando: OBTER_FATURAS\n");
            
            // Enviar comando
            saida.println("OBTER_FATURAS");
            
            // Receber e exibir resposta
            System.out.println("📥 Resposta do servidor:");
            System.out.println("━".repeat(60));
            
            String linha;
            while ((linha = entrada.readLine()) != null) {
                System.out.println(linha);
            }
            
            System.out.println("━".repeat(60));
            System.out.println("\n✅ Comunicação concluída!");
            
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
            System.err.println("💡 Certifique-se de que o servidor está rodando!");
        }
    }
}
