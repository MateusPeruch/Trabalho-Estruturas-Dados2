package database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Statement;

import database.model.CURSO;

import java.sql.Connection;

public class CursoDAO extends AbstractDAO
{

	private final String insert = "insert into curso(curso) VALUES(?)";
	private final PreparedStatement psInsert;
	
	public CursoDAO(final Connection conexao) throws SQLException
	{
		psInsert = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
	}

	@Override
	public List<Object> Select() throws SQLException 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int Insert(Object parametros) throws SQLException 
	{
		psInsert.clearParameters();
		CURSO cur = (CURSO)parametros;
		psInsert.setString(1, cur.getNomeCurso());
		
		psInsert.executeUpdate();
		
		ResultSet rs = psInsert.getGeneratedKeys();
		rs.next();
		int idGerado = rs.getInt(1);
		cur.setId(idGerado);
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
