package network;

import stream.FaturaInputStream;
import model.Fatura;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;

/**
 * Cliente TCP que se conecta ao servidor e recebe faturas
 */
public class ClienteTelefonia {
    private static final String HOST = "localhost";
    private static final int PORTA = 5000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║        CLIENTE DE TELEFONIA - TCP                ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");
        
        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    solicitarFaturasDoServidor();
                    break;
                case 0:
                    System.out.println("\n👋 Encerrando cliente...");
                    break;
                default:
                    System.out.println("❌ Opção inválida!");
            }
        } while (opcao != 0);
        
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== MENU DO CLIENTE ===");
        System.out.println("1 - Solicitar Faturas do Servidor");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void solicitarFaturasDoServidor() {
        System.out.println("\n🔌 Conectando ao servidor " + HOST + ":" + PORTA + "...");
        
        try (Socket socket = new Socket(HOST, PORTA);
             PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
             InputStream entrada = socket.getInputStream()) {
            
            System.out.println("✅ Conectado ao servidor!");
            
            // Enviar comando
            saida.println("OBTER_FATURAS");
            System.out.println("📨 Comando enviado: OBTER_FATURAS");
            
            // Receber faturas usando FaturaInputStream
            System.out.println("📥 Recebendo faturas...\n");
            
            FaturaInputStream faturaStream = new FaturaInputStream(entrada);
            
            // Exibir o stream diretamente (como vem do servidor)
            BufferedReader reader = new BufferedReader(new InputStreamReader(entrada));
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
            
            System.out.println("\n✅ Recebimento concluído!");
            
        } catch (IOException e) {
            System.err.println("❌ Erro de conexão: " + e.getMessage());
            System.err.println("💡 Certifique-se de que o servidor está rodando!");
        }
    }
}
