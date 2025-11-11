package model;

import java.io.Serializable;

/**
 * POJO 1 - Representa um cliente do serviço de telefonia
 */
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Cliente() {}

    public Cliente(String nome, String cpf, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Cliente{nome='" + nome + "', cpf='" + cpf + "', telefone='" + telefone + "', email='" + email + "'}";
    }
}
