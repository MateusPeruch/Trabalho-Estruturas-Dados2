package database.model;

import lib.DataTextField;

import java.math.BigDecimal;
import java.util.Date;

public class MATRICULA_CURSO {

    private int idmatricula_curso;
    private int idmatricula;
    private int idcurso;
    private int idfase;
    private int iddisciplina;
    private BigDecimal valor;
    private Date Datainicio;
    private Date DataFinal;

    public MATRICULA_CURSO(){}

    public int getIdmatricula_curso() {
        return idmatricula_curso;
    }

    public void setIdmatricula_curso(int idmatricula_curso) {
        this.idmatricula_curso = idmatricula_curso;
    }

    public int getIdmatricula() {
        return idmatricula;
    }

    public void setIdmatricula(int idmatricula) {
        this.idmatricula = idmatricula;
    }

    public int getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(int idcurso) {
        this.idcurso = idcurso;
    }

    public int getIdfase() {
        return idfase;
    }

    public void setIdfase(int idfase) {
        this.idfase = idfase;
    }

    public int getIddisciplina() {
        return iddisciplina;
    }

    public void setIddisciplina(int iddisciplina) {
        this.iddisciplina = iddisciplina;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getDatainicio() {
        return Datainicio;
    }

    public void setDatainicio(Date datainicio) {
        Datainicio = datainicio;
    }

    public Date getDataFinal() {
        return DataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        DataFinal = dataFinal;
    }
}
