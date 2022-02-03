package graphic;

import database.dao.CursoDAO;
import database.dao.DisciplinaDAO;
import database.dao.FaseDAO;
import database.dao.ProfessorDAO;
import database.model.CURSO;
import database.model.DISCIPLINA;
import database.model.FASE;
import database.model.PROFESSOR;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Metodos {

    private Connection conexao;
    private CursoDAO cursoDAO;
    private FaseDAO faseDAO;
    private DisciplinaDAO disciplinaDAO;
    private ProfessorDAO professorDAO;
    private int idCurso;//1
    private int idfase;
    private int idDisciplina;


    public Metodos(final Connection conexao) throws SQLException
    {
        this.conexao = conexao;
        cursoDAO = new CursoDAO(this.conexao);
        faseDAO = new FaseDAO(this.conexao, 0);
        disciplinaDAO = new DisciplinaDAO(this.conexao);
        professorDAO = new ProfessorDAO(this.conexao);
    }
    
    public Metodos()
    {
    	
    }

    public void inserirCurso(String nomeCurso)
    {
        try
        {
            CURSO curso = new CURSO(nomeCurso);
            idCurso = cursoDAO.Insert(curso);

        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Falha ao inserir o curso: " + e.getMessage());
        }
    }
    
    public void inserirFase(String faseAtual, int idcurso)
    {
        try
        {
            FASE fase = new FASE(faseAtual, idCurso);
            idfase = faseDAO.Insert(fase);
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Falha ao inserir a fase: " + e.getMessage());
        }
    }
    
    public void inserirDisciplina(String disciplinaAtual, String diaAtual, int idfase)
    {
        try
        {
            DISCIPLINA disciplina = new DISCIPLINA(disciplinaAtual, diaAtual, idfase);
            idDisciplina = disciplinaDAO.Insert(disciplina);
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Falha ao inserir a disciplina: " + e.getMessage());
        }
    }

    public void inserirProfessor(String professoratual, String formacaoProfessor, int idDisciplina)
    {
        try
        {
            PROFESSOR professor = new PROFESSOR(professoratual, formacaoProfessor, idDisciplina);
            professorDAO.Insert(professor);
        }
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Falha ao inserir ao professor: " + e.getMessage());
        }
    }


    public int getIdcurso()
    {
        return idCurso;
    }
    
    public int getIdfase()
    {
        return idfase;
    }
    
    public  int getIdDisciplina()
    {
    	return idDisciplina;
    }
}
