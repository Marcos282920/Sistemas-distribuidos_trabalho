import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Linha> linhas = new ArrayList<>();
    private static Atendimendo atendimento = new Atendimendo();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    criarLinha();
                    break;
                case 2:
                    adicionarServico();
                    break;
                case 3:
                    fazerReclamacao();
                    break;
                case 4:
                    listarLinhasEServicos();
                    break;
                case 5:
                    calcularValorTotal();
                    break;
                case 6:
                    atendimento.listarReclamacao();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== SISTEMA DE GERENCIAMENTO DE LINHAS TELEFÔNICAS ===");
        System.out.println("1 - Criar Nova Linha");
        System.out.println("2 - Adicionar Serviço a uma Linha");
        System.out.println("3 - Fazer Reclamação");
        System.out.println("4 - Listar Linhas e Serviços");
        System.out.println("5 - Calcular Valor Total de uma Linha");
        System.out.println("6 - Listar Reclamações");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void criarLinha() {
        System.out.print("Digite o número da linha: ");
        String numero = scanner.nextLine();
        
        Linha linha = new Linha(numero);
        linhas.add(linha);
        System.out.println("Linha " + numero + " criada com sucesso!");
    }

    private static void adicionarServico() {
        if (linhas.isEmpty()) {
            System.out.println("Nenhuma linha cadastrada!");
            return;
        }
        
        System.out.print("Digite o número da linha: ");
        String numero = scanner.nextLine();
        
        Linha linha = buscarLinha(numero);
        if (linha == null) {
            System.out.println("Linha não encontrada!");
            return;
        }
        
        System.out.println("\nTipos de Serviço:");
        System.out.println("1 - Agenda");
        System.out.println("2 - Siga-me");
        System.out.println("3 - Secretária Eletrônica");
        System.out.print("Escolha o serviço: ");
        int tipoServico = scanner.nextInt();
        scanner.nextLine();
        
        switch (tipoServico) {
            case 1:
                adicionarAgenda(linha);
                break;
            case 2:
                adicionarSiga(linha);
                break;
            case 3:
                adicionarSecretaria(linha);
                break;
            default:
                System.out.println("Serviço inválido!");
        }
    }

    private static void adicionarAgenda(Linha linha) {
        System.out.print("Nome do contato: ");
        String nome = scanner.nextLine();
        System.out.print("Número do contato: ");
        String numeroContato = scanner.nextLine();
        System.out.print("Preço do serviço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
        
        Agenda agenda = new Agenda("Agenda", preco, nome, numeroContato);
        linha.adicionarServico(agenda);
        System.out.println("Serviço de Agenda adicionado com sucesso!");
    }

    private static void adicionarSiga(Linha linha) {
        System.out.print("Número de origem: ");
        String origem = scanner.nextLine();
        System.out.print("Número de destino: ");
        String destino = scanner.nextLine();
        System.out.print("Preço do serviço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
        
        Siga siga = new Siga("Siga-me", preco, destino, origem);
        linha.adicionarServico(siga);
        System.out.println("Serviço Siga-me adicionado com sucesso!");
    }

    private static void adicionarSecretaria(Linha linha) {
        System.out.print("Mensagem padrão: ");
        String mensagem = scanner.nextLine();
        System.out.print("Limite de mensagens: ");
        int limite = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Preço do serviço: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
        
        Secretaria secretaria = new Secretaria("Secretária Eletrônica", preco, mensagem, limite, linha.getNumero());
        linha.adicionarServico(secretaria);
        System.out.println("Serviço de Secretária Eletrônica adicionado com sucesso!");
    }

    private static void fazerReclamacao() {
        if (linhas.isEmpty()) {
            System.out.println("Nenhuma linha cadastrada!");
            return;
        }
        
        System.out.print("Digite o número da linha: ");
        String numero = scanner.nextLine();
        
        Linha linha = buscarLinha(numero);
        if (linha == null) {
            System.out.println("Linha não encontrada!");
            return;
        }
        
        System.out.print("Digite o texto da reclamação: ");
        String textoReclamacao = scanner.nextLine();
        
        atendimento.Fazer_Reclamacao(linha, textoReclamacao);
    }

    private static void listarLinhasEServicos() {
        if (linhas.isEmpty()) {
            System.out.println("Nenhuma linha cadastrada!");
            return;
        }
        
        for (Linha linha : linhas) {
            System.out.println("\n--- Linha: " + linha.getNumero() + " ---");
            if (linha.getServicos().isEmpty()) {
                System.out.println("  Nenhum serviço cadastrado");
            } else {
                for (Servico servico : linha.getServicos()) {
                    System.out.println("  - " + servico.toString());
                }
            }
        }
    }

    private static void calcularValorTotal() {
        if (linhas.isEmpty()) {
            System.out.println("Nenhuma linha cadastrada!");
            return;
        }
        
        System.out.print("Digite o número da linha: ");
        String numero = scanner.nextLine();
        
        Linha linha = buscarLinha(numero);
        if (linha == null) {
            System.out.println("Linha não encontrada!");
            return;
        }
        
        double total = linha.CalcularValorTotal();
        System.out.println("Valor total da linha " + numero + ": R$ " + String.format("%.2f", total));
    }

    private static Linha buscarLinha(String numero) {
        for (Linha linha : linhas) {
            if (linha.getNumero().equals(numero)) {
                return linha;
            }
        }
        return null;
    }
}
