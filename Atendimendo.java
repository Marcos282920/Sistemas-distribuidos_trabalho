import java.util.ArrayList;
import java.util.List;

public class Atendimendo implements Reclamacao {

    private List<String> reclamacoes = new ArrayList<>();


    @Override
    public void Fazer_Reclamacao(Linha linha, String texto_reclamacao) {
        String texto = "Descrição da Reclamação da Linha " + linha.getNumero();
        String s = ": " + texto_reclamacao;
        reclamacoes.add(texto);
        System.out.println(texto);
    }

    @Override
    public void listarReclamacao() {
        for (String rec : reclamacoes) {
            System.out.println(rec);
        }
    }
}

