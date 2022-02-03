package database.dao;

import database.model.ALUNO;
import database.model.MATRICULA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class alunoDAO extends AbstractDAO{

    private String select = "select * from matricula";
    private String selectWhere = "select * from aluno where idAluno = ?";
    private PreparedStatement pstSelect;
    private PreparedStatement psSelectWhere;

    public alunoDAO(final Connection conexao) throws SQLException {
        pstSelect = conexao.prepareStatement(select);
        psSelectWhere = conexao.prepareStatement(selectWhere);
    }


    public List<Object> Select() throws SQLException{

        List<Object> listacodigo = new ArrayList<Object>();
        ResultSet resultado = pstSelect.executeQuery();
        if (resultado != null)
        {
            while (resultado.next())
            {
                ALUNO aluno = new ALUNO();
                aluno.setIdaluno(resultado.getInt("idAluno"));
                listacodigo.add(aluno);
            }
            resultado.close();
        }
        return listacodigo;
    }

    public List<Object> Selectwhere(final int idAluno) throws SQLException{

        ALUNO aluno = null;

        psSelectWhere.setInt(1, idAluno);
        List<Object> listaluno = new ArrayList<Object>();
        ResultSet resultado = psSelectWhere.executeQuery();
        if (resultado != null && resultado.next())
        {
            ALUNO alu = new ALUNO();
            alu.setNome(resultado.getString("nome"));
            alu.setData_nascimeento(resultado.getString("data_nascimento"));
            listaluno.add(alu);
        }
        return listaluno;
    }

    @Override
    public int Insert(Object parametros) throws SQLException {
        return 0;
    }

    @Override
    public void Update(Object parametros) throws SQLException {

    }

    @Override
    public void Delete(Object parametros) throws SQLException {

    }
}
