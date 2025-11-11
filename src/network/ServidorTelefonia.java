package network;

import model.*;
import stream.*;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Servidor TCP com SERIALIZAÇÃO de objetos
 * Empacota e desempacota mensagens usando ObjectInputStream/ObjectOutputStream
 */
public class ServidorTelefonia {
    private static final int PORTA = 5000;
    private static GerenciadorLinhas gerenciador = new GerenciadorLinhas();
    private static List<Fatura> faturasGlobais = new ArrayList<>();

    public static void main(String[] args) {
        inicializarDados();
        
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║   SERVIDOR COM SERIALIZAÇÃO - PORTA " + PORTA + "       ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println("🔌 Aguardando conexões...\n");

        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("✅ Cliente conectado: " + clientSocket.getInetAddress());
                    
                    // Processar requisição do cliente com SERIALIZAÇÃO
                    processarClienteComSerializacao(clientSocket);
                    
                } catch (IOException e) {
                    System.err.println("❌ Erro ao processar cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Erro ao iniciar servidor: " + e.getMessage());
        }
    }

    /**
     * Processa cliente COM SERIALIZAÇÃO de objetos
     * DESEMPACOTA a mensagem do cliente e EMPACOTA a resposta
     */
    private static void processarClienteComSerializacao(Socket clientSocket) {
        try {
            // 1️⃣ DESEMPACOTAMENTO: Receber e desserializar objeto MensagemRequest
            ObjectInputStream entrada = new ObjectInputStream(clientSocket.getInputStream());
            MensagemRequest request = (MensagemRequest) entrada.readObject();
            
            System.out.println("📦 DESEMPACOTANDO request: " + request);
            
            // 2️⃣ PROCESSAR: Executar operação solicitada
            MensagemReply reply = processarRequest(request);
            
            System.out.println("� EMPACOTANDO reply: " + reply);
            
            // 3️⃣ EMPACOTAMENTO: Serializar e enviar objeto MensagemReply
            ObjectOutputStream saida = new ObjectOutputStream(clientSocket.getOutputStream());
            saida.writeObject(reply);
            saida.flush();
            
            System.out.println("✅ Resposta enviada!\n");
            
            clientSocket.close();
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Erro na comunicação: " + e.getMessage());
        }
    }
    
    /**
     * Processa a requisição e retorna a resposta
     */
    private static MensagemReply processarRequest(MensagemRequest request) {
        try {
            switch (request.getOperacao()) {
                case OBTER_FATURAS:
                case LISTAR_TODAS:
                    return new MensagemReply(true, "Faturas obtidas com sucesso", 
                                            new ArrayList<>(faturasGlobais));
                    
                case OBTER_FATURA_POR_LINHA:
                    List<Fatura> faturasFiltradas = new ArrayList<>();
                    for (Fatura f : faturasGlobais) {
                        if (f.getNumeroLinha().equals(request.getNumeroLinha())) {
                            faturasFiltradas.add(f);
                        }
                    }
                    return new MensagemReply(true, "Faturas encontradas", faturasFiltradas);
                    
                default:
                    return new MensagemReply(false, "Operação não suportada");
            }
        } catch (Exception e) {
            return new MensagemReply(false, "Erro: " + e.getMessage());
        }
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
