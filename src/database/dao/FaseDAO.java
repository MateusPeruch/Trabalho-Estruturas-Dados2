package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mysql.jdbc.Statement;
import database.model.FASE;

public class FaseDAO extends AbstractDAO
{
	private int fase;
	private final String insert = "insert into sistema.fase(nome, curso_idcurso) VALUES(?, ?)";
	private String selectWhere = "SELECT disciplina.codigo, disciplina.dia_semana, professor.nome, professor.formacao FROM disciplina JOIN professor ON disciplina.iddisciplina = professor.disciplina_iddisciplina WHERE fase_idfase = ?";
	private final PreparedStatement psInsert;
	private final PreparedStatement pstSelectWhere;
	
	public FaseDAO(final Connection conexao, int fase) throws SQLException
	{
		psInsert = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
		pstSelectWhere = conexao.prepareStatement(selectWhere);
		this.fase = fase;
	}


	@Override
	public List<Object> Select() throws SQLException 
	{
		List<Object> listaDisciplinasFase = new ArrayList<Object>();
		pstSelectWhere.setInt(1, fase);
		ResultSet resultado = pstSelectWhere.executeQuery();

		if (resultado != null) 
		{
			while (resultado.next()) 
			{
				FASE fase = new FASE();
				fase.setDisciplina(resultado.getString("codigo"));
				fase.setDiaSemana(resultado.getString("dia_semana"));
				fase.setNomeProfessor(resultado.getString("professor.nome"));
				fase.setTituloProfessor(resultado.getString("professor.formacao"));
				listaDisciplinasFase.add(fase);
			}
			resultado.close();
		}		
		return listaDisciplinasFase;
	}

	public List<Object> SelectNomefase() throws SQLException
	{
		List<Object> listanomeFase = new ArrayList<Object>();

		ResultSet resultado = pstSelectWhere.executeQuery();

		if (resultado != null)
		{
			while (resultado.next())
			{
				FASE fase = new FASE();
				fase.setNomefase(resultado.getString("nomeFase"));
				listanomeFase.add(fase);
			}
			resultado.close();
		}

		for(int i = 0; i < listanomeFase.size(); i++){
			FASE fase = (FASE)listanomeFase.get(i);
			System.out.println(fase.getNomefase());
		}
		return listanomeFase;

	}

	@Override
	public int Insert(Object parametros) throws SQLException 
	{
		psInsert.clearParameters();
		
		FASE fase = (FASE)parametros;
		psInsert.setString(1, fase.getFase());
		psInsert.setInt(2, fase.getIdCurso());

		psInsert.executeUpdate();
		
		ResultSet rs = psInsert.getGeneratedKeys();
		rs.next();
		int idGerado = rs.getInt(1);
		fase.setIdFase(idGerado);
		return idGerado;
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
