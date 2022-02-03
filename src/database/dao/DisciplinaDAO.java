package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.Statement;
import database.model.DISCIPLINA;

public class DisciplinaDAO extends AbstractDAO
{
	private final String insert = "insert into disciplina(nome, dia_semana, fase_idfase) VALUES(?,?,?)";
	private String select = "SELECT disciplina.codigo, disciplina.dia_semana, professor.nome FROM disciplina JOIN professor ON disciplina.iddisciplina = professor.disciplina_iddisciplina";
	private final PreparedStatement psInsert;
	private final PreparedStatement pstSelect;
	
	public DisciplinaDAO(final Connection conexao) throws SQLException
	{
		psInsert = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
		pstSelect = conexao.prepareStatement(select);
	}

	@Override
	public List<Object> Select() throws SQLException 
	{
		List<Object> listaDisciplinasFase = new ArrayList<Object>();
		ResultSet resultado = pstSelect.executeQuery();
		
		if (resultado != null) 
		{
			while (resultado.next()) 
			{
				DISCIPLINA disciplina = new DISCIPLINA();
				disciplina.setDisciplina(resultado.getString("disciplina.codigo"));
				disciplina.setDiaAtual(resultado.getString("dia_semana"));
				disciplina.setNomeProfessor(resultado.getString("professor.nome"));
				listaDisciplinasFase.add(disciplina);
			}
			resultado.close();
		}		
		return listaDisciplinasFase;
	}

	@Override
	public int Insert(Object parametros) throws SQLException 
	{

		psInsert.clearParameters();

		DISCIPLINA disciplina = (DISCIPLINA)parametros;
		psInsert.setString(1, disciplina.getDisciplina());
		psInsert.setString(2, disciplina.getDiaAtual());
		psInsert.setInt(3, disciplina.getIdfase());

		psInsert.executeUpdate();

		ResultSet rs = psInsert.getGeneratedKeys();
		rs.next();
		int idGerado = rs.getInt(1);
		disciplina.setIdfase(idGerado);
		return idGerado;
	}

	@Override
	public void Update(Object parametros) throws SQLException 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void Delete(Object parametros) throws SQLException 
	{
		// TODO Auto-generated method stub
	}

}
