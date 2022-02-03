package database.model;

import java.math.BigDecimal;
import java.util.Date;

public class MATRICULA_AUX
{
    private int idMatricula;
    private String nome_aluno;
    private String dataMatricula;
    private String diaVencimento;
    private String nome_curso;
    private String nome_fase;
    private String codigo_disciplina;
    private Date dataInicio;
    private String dataFim;
    private BigDecimal valor;

    public MATRICULA_AUX(){

    }

    public int getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(int idMatricula) {
        this.idMatricula = idMatricula;
    }

    public String getNome_aluno() {
        return nome_aluno;
    }

    public void setNome_aluno(String nome_aluno) {
        this.nome_aluno = nome_aluno;
    }

    public String getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(String dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public String getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(String diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public String getNome_curso() {
        return nome_curso;
    }

    public void setNome_curso(String nome_curso) {
        this.nome_curso = nome_curso;
    }

    public String getNome_fase() {
        return nome_fase;
    }

    public void setNome_fase(String nome_fase) {
        this.nome_fase = nome_fase;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getCodigo_disciplina() {
        return codigo_disciplina;
    }

    public void setCodigo_disciplina(String codigo_disciplina) {
        this.codigo_disciplina = codigo_disciplina;
    }
}
