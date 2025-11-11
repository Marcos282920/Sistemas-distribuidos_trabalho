package network;

import stream.FaturaInputStream;
import model.*;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;

/**
 * Cliente TCP COM SERIALIZAÇÃO de objetos
 * Empacota requests e desempacota replies
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

    /**
     * Solicita faturas com SERIALIZAÇÃO de objetos
     * EMPACOTA request e DESEMPACOTA reply
     */
    private static void solicitarFaturasDoServidor() {
        System.out.println("\n🔌 Conectando ao servidor " + HOST + ":" + PORTA + "...");
        
        try {
            Socket socket = new Socket(HOST, PORTA);
            System.out.println("✅ Conectado ao servidor!");
            
            // 1️⃣ EMPACOTAMENTO: Criar e enviar MensagemRequest
            MensagemRequest request = new MensagemRequest(MensagemRequest.TipoOperacao.OBTER_FATURAS);
            
            System.out.println("📦 EMPACOTANDO request: " + request);
            
            ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
            saida.writeObject(request);
            saida.flush();
            
            System.out.println("📨 Request enviado!");
            
            // 2️⃣ DESEMPACOTAMENTO: Receber e desserializar MensagemReply
            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            MensagemReply reply = (MensagemReply) entrada.readObject();
            
            System.out.println("� DESEMPACOTANDO reply: " + reply);
            
            // 3️⃣ EXIBIR: Mostrar resultado
            if (reply.isSucesso()) {
                System.out.println("\n✅ " + reply.getMensagem());
                List<Fatura> faturas = reply.getFaturas();
                
                if (faturas != null && !faturas.isEmpty()) {
                    System.out.println("\n╔══════════════════════════════════════════════════╗");
                    System.out.println("║              FATURAS RECEBIDAS                   ║");
                    System.out.println("╚══════════════════════════════════════════════════╝\n");
                    
                    for (Fatura f : faturas) {
                        System.out.println(f);
                        System.out.println("─────────────────────────────────────────────");
                    }
                    System.out.println("\n📊 Total de faturas: " + faturas.size());
                } else {
                    System.out.println("ℹ️  Nenhuma fatura disponível");
                }
            } else {
                System.err.println("❌ Erro: " + reply.getMensagem());
            }
            
            socket.close();
            System.out.println("\n✅ Conexão encerrada!");
            
        } catch (IOException e) {
            System.err.println("❌ Erro de conexão: " + e.getMessage());
            System.err.println("💡 Certifique-se de que o servidor está rodando!");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Erro ao desserializar resposta: " + e.getMessage());
        }
    }
}
