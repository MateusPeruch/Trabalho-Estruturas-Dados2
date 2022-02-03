package database.model;

import java.util.Date;

public class MATRICULA {

    private int id_matricula;
    private int id_aluno;
    private Date dataMatricula;
    private int dia_vencimento;

    public MATRICULA(){}

    public int getId_matricula() {
        return id_matricula;
    }

    public void setId_matricula(int id_matricula) {
        this.id_matricula = id_matricula;
    }

    public int getId_aluno() {
        return id_aluno;
    }

    public void setId_aluno(int id_aluno) {
        this.id_aluno = id_aluno;
    }

    public Date getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMateicula(Date dataMateicula) {
        this.dataMatricula = dataMateicula;
    }

    public int getDia_vencimento() {
        return dia_vencimento;
    }

    public void setDia_vencimento(int dia_vencimento) {
        this.dia_vencimento = dia_vencimento;
    }
}
