public class Servico {
    private String tipo;
    private double preco;

    public Servico(String tipo, double preço){
        this.tipo = tipo;
        this.preco = preço;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public void setPreco(double preco){
        this.preco = preco;
    }

    public String getTipo(){
        return tipo;
    }

    public double getPreco(){
        return preco;
    }
    
    @Override
    public String toString() {
        return tipo + " (R$ " + preco + ")";
    }





}

