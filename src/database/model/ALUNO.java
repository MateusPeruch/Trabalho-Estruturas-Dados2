package database.model;

import java.util.Date;

public class ALUNO {

    private int idaluno;
    private int cpf;
    private String data_nascimeento;
    private String nome;

    public int getIdaluno() {
        return idaluno;
    }

    public void setIdaluno(int idaluno) {
        this.idaluno = idaluno;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getData_nascimeento() {
        return data_nascimeento;
    }

    public void setData_nascimeento(String data_nascimeento) {
        this.data_nascimeento = data_nascimeento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
