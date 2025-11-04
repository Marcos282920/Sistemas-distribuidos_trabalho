import java.util.ArrayList;
import java.util.List;

public class Linha {
    private String numero;
    private ArrayList<Servico> Servico; //lista que guarda varios serviços


     public Linha(String numero){
     this.numero = numero;
     this.Servico = new ArrayList<>();
    }

    public void adicionarServico(Servico servico) {
        Servico.add(servico);
    }

    public String getNumero() {
        return numero;
    }

    public List<Servico> getServicos() {
        return Servico;
    }

    public double CalcularValorTotal() {
        double total = 0;
        for (Servico s : Servico) {
            total += s.getPreco();
        }
        return total;
    }
    
}
