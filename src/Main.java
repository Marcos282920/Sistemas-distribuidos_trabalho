import model.*;
import stream.FaturaOutputStream;
import network.ServidorTelefonia;
import network.ClienteTelefonia;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Classe principal do sistema de telefonia
 * Permite testar streams e executar cliente/servidor
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║      SISTEMA DE TELEFONIA - TRABALHO SD          ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");
        
        System.out.println("Escolha uma opção:");
        System.out.println("1 - Testar FaturaOutputStream (System.out)");
        System.out.println("2 - Testar FaturaOutputStream (Arquivo)");
        System.out.println("3 - Iniciar Servidor TCP");
        System.out.println("4 - Iniciar Cliente TCP");
        System.out.println("5 - Executar todos os testes");
        System.out.print("\nOpção: ");
        
        int opcao = scanner.nextInt();
        scanner.nextLine();
        
        switch (opcao) {
            case 1:
                testeConsole();
                break;
            case 2:
                testeArquivo();
                break;
            case 3:
                System.out.println("\n🚀 Iniciando servidor...\n");
                ServidorTelefonia.main(args);
                break;
            case 4:
                System.out.println("\n🚀 Iniciando cliente...\n");
                ClienteTelefonia.main(args);
                break;
            case 5:
                executarTodosTestes();
                break;
            default:
                System.out.println("❌ Opção inválida!");
        }
        
        scanner.close();
    }
    
    /**
     * TESTE 1: Saída Padrão (System.out)
     */
    private static void testeConsole() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  TESTE 1: FaturaOutputStream -> System.out       ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");
        
        Fatura[] faturas = criarFaturasTeste();
        
        try {
            FaturaOutputStream fos = new FaturaOutputStream(
                faturas,      // Array de objetos
                3,            // Enviar 3 objetos
                20,           // 20 bytes para numeroLinha
                15,           // 15 bytes para dataVencimento
                12,           // 12 bytes para valorTotal
                System.out    // Destino: saída padrão
            );
            
            fos.enviarTodos();
            System.out.println("\n✅ Teste concluído com sucesso!\n");
            
        } catch (IOException e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
    
    /**
     * TESTE 2: Arquivo (FileOutputStream)
     */
    private static void testeArquivo() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║  TESTE 2: FaturaOutputStream -> Arquivo          ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");
        
        String nomeArquivo = "faturas_output.txt";
        Fatura[] faturas = criarFaturasTeste();
        
        try (FileOutputStream arquivo = new FileOutputStream(nomeArquivo)) {
            FaturaOutputStream fos = new FaturaOutputStream(
                faturas,      // Array de objetos
                5,            // Enviar todos os 5 objetos
                25,           // 25 bytes para numeroLinha
                20,           // 20 bytes para dataVencimento
                15,           // 15 bytes para valorTotal
                arquivo       // Destino: arquivo
            );
            
            fos.enviarTodos();
            System.out.println("✅ Faturas gravadas no arquivo: " + nomeArquivo);
            System.out.println("📄 Abra o arquivo para visualizar os dados formatados\n");
            
        } catch (IOException e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
    
    /**
     * TESTE 3: Executar todos os testes sequencialmente
     */
    private static void executarTodosTestes() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║        EXECUTANDO TODOS OS TESTES                ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");
        
        // Teste 1: Console
        testeConsole();
        
        // Aguardar
        aguardar(2);
        
        // Teste 2: Arquivo
        testeArquivo();
        
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║ TESTE 3: Para testar TCP, execute:              ║");
        System.out.println("║                                                  ║");
        System.out.println("║ Terminal 1: java Main                            ║");
        System.out.println("║             Opção 3 (Servidor)                   ║");
        System.out.println("║                                                  ║");
        System.out.println("║ Terminal 2: java Main                            ║");
        System.out.println("║             Opção 4 (Cliente)                    ║");
        System.out.println("╚══════════════════════════════════════════════════╝\n");
    }
    
    /**
     * Criar faturas de teste
     */
    private static Fatura[] criarFaturasTeste() {
        return new Fatura[] {
            new Fatura("11987654321", LocalDate.of(2025, 11, 15), 125.50, false),
            new Fatura("11912345678", LocalDate.of(2025, 11, 20), 89.90, true),
            new Fatura("21987651234", LocalDate.of(2025, 11, 18), 234.75, false),
            new Fatura("85988776655", LocalDate.of(2025, 11, 25), 67.30, false),
            new Fatura("47999887766", LocalDate.of(2025, 11, 22), 156.20, true)
        };
    }
    
    private static void aguardar(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
