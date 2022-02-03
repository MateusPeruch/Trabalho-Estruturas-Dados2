package database.dao;

import database.model.MATRICULA;
import database.model.MATRICULA_AUX;
import database.model.USUARIO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO extends AbstractDAO
{

	private String select = "select * from matricula";
	private String selectWhere = "select * from matricula where idAluno = ?";
	private final String insert = "insert into matricula(id_aluno, dataMatricula, diaVencimento) VALUES(?, ?, ?)";
	private final String delete = "delete from matricula where idMatricula = ?";
	private final String update = "update matricula set diaVencimento = ?,  where idDatricula = ? ";
	private final String SelectMatriculaCurso =
					"	select m.idMatricula, "
					+"	(select a.nome from sistema.aluno a where a.idAluno = m.idAluno) as nome_aluno, "
					+"	m.dataMatricula, "
					+"	m.diaVencimento, "
					+"	(select c.nomeCurso from sistema.curso c where c.idCurso = mc.idCurso) as nome_curso, "
					+"	(select f.nomeFase from sistema.fase f where f.idFase = mc.idFase) as nome_fase, "
					+"	(select d.codigo from sistema.disciplina d where d.idDisciplina = mc.idDisciplina) as codigo_disciplina, "
					+"	mc.dataInicio, "
					+"	mc.dataFim, "
					+"	mc.valor "
					+"	from sistema.matricula m "
					+"	join sistema.matricula_curso mc on (mc.idMatricula = m.idMatricula) "
					+"	where m.idAluno = ?";

	private PreparedStatement pstSelect;
	private PreparedStatement pstSelectWhere;
	private final PreparedStatement psInsert;
	private final PreparedStatement psDelete;
	private final PreparedStatement psUpdate;
	private final PreparedStatement psSelectMatriculaCurso;

	public MatriculaDAO(final Connection conexao) throws SQLException
	{
		pstSelect = conexao.prepareStatement(select);
		pstSelectWhere = conexao.prepareStatement(selectWhere);
		psInsert = conexao.prepareStatement(insert);
		psDelete = conexao.prepareStatement(delete);
		psUpdate = conexao.prepareStatement(update);
		psSelectMatriculaCurso = conexao.prepareStatement(SelectMatriculaCurso);
	}
	
	@Override
	public List<Object> Select() throws SQLException 
	{
		
		List<Object> listamatricula = new ArrayList<Object>();
		ResultSet resultado = pstSelect.executeQuery();
		if (resultado != null) 
		{
			while (resultado.next()) 
			{
				MATRICULA matricula = new MATRICULA();
				matricula.setId_matricula(resultado.getInt("idMatricula"));
				matricula.setId_aluno(resultado.getInt("idAluno"));
				matricula.setDataMateicula(resultado.getDate("dataMatricula"));
				matricula.setDia_vencimento(resultado.getInt("diaVencimento"));
				listamatricula.add(matricula);
			}
			resultado.close();
		}		
		return listamatricula;
	}

	public List<Object> SelectMatriculaCurso(final int idAluno) throws SQLException
	{

		List<Object> listamatricula_aux = new ArrayList<Object>();

		psSelectMatriculaCurso.setInt(1, idAluno);

		ResultSet resultado = psSelectMatriculaCurso.executeQuery();
		if (resultado != null)
		{
			while (resultado.next())
			{
				MATRICULA_AUX matricula_aux = new MATRICULA_AUX();
				matricula_aux.setIdMatricula(resultado.getInt("idMatricula"));
				matricula_aux.setNome_aluno(resultado.getString("nome_aluno"));
				matricula_aux.setDataMatricula(resultado.getString("dataMatricula"));
				matricula_aux.setDiaVencimento(resultado.getString("diaVencimento"));
				matricula_aux.setNome_curso(resultado.getString("nome_curso"));
				matricula_aux.setNome_fase(resultado.getString("nome_fase"));
				matricula_aux.setCodigo_disciplina(resultado.getString("codigo_disciplina"));
				matricula_aux.setDataInicio(resultado.getDate("dataInicio"));
				matricula_aux.setDataFim(resultado.getString("dataFim"));
				matricula_aux.setValor(resultado.getBigDecimal("valor"));
				listamatricula_aux.add(matricula_aux);
			}
			resultado.close();
		}
		return listamatricula_aux;
	}
	
	public MATRICULA SelectWhere(final int id_aluno) throws SQLException
	{
		
		MATRICULA matricula = null;
		
		pstSelectWhere.setInt(1, id_aluno);
		
		ResultSet resultado = pstSelectWhere.executeQuery();
		if (resultado != null && resultado.next()) 
		{
			matricula = new MATRICULA();
			matricula.setId_matricula(resultado.getInt("idMatricula"));
			matricula.setId_aluno(resultado.getInt("idAluno"));
			matricula.setDataMateicula(resultado.getDate("dataMatricula"));
			matricula.setDia_vencimento(resultado.getInt("diaVencimento"));
		}
		
		return matricula;
	}

	@Override
	public int Insert(Object parametros) throws SQLException
	{
		psInsert.clearParameters();

		MATRICULA matricula = (MATRICULA) parametros;


		psInsert.setInt(1, matricula.getId_aluno());
		SimpleDateFormat spd = new SimpleDateFormat("yyyy-mm-dd");
		psInsert.setString(2, spd.format(matricula.getDataMatricula()));
		psInsert.setInt(3, matricula.getDia_vencimento());
		psInsert.execute();

		return  0;
	}

	@Override
	public void Update(Object parametros) throws SQLException 
	{
		psUpdate.clearParameters();
		MATRICULA matricula = (MATRICULA) parametros;
		psUpdate.setInt(1, matricula.getDia_vencimento());
		psUpdate.execute();
	}

	@Override
	public void Delete(Object parametros) throws SQLException 
	{
		psDelete.clearParameters();
		psDelete.setInt(1,  (Integer)parametros);
		psDelete.execute();
		
	}

}
