package database.dao;

import database.model.PROFESSOR;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProfessorDAO extends AbstractDAO
{

    private final String insert = "insert into professor(nome, formacao, disciplina_iddisciplina) VALUES(?,?,?)";
    private final PreparedStatement psInsert;

    public ProfessorDAO(final Connection conexao) throws SQLException
    {
        psInsert = conexao.prepareStatement(insert);
    }

    @Override
    public List<Object> Select() throws SQLException 
    {
        return null;
    }

    @Override
    public int Insert(Object parametros) throws SQLException 
    {
        psInsert.clearParameters();

        PROFESSOR professor = (PROFESSOR)parametros;

        psInsert.setString(1, professor.getNome());
        psInsert.setString(2, professor.getFormacaoProfessor());
        psInsert.setInt(3, professor.getIdDisciplina());
        
        psInsert.execute();
        return 0;
    }

    @Override
    public void Update(Object parametros) throws SQLException 
    {

    }

    @Override
    public void Delete(Object parametros) throws SQLException 
    {

    }
}

