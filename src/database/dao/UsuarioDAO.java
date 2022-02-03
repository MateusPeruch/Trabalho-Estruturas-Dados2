package database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import database.model.USUARIO;

public class UsuarioDAO extends AbstractDAO 
{
	
	private String select = "select * from tb_usuario";
	private String selectWhere = "select * from tb_usuario where usuario = ? and senha = ?";
	private String selectUsuario = "select * from tb_usuario where usuario = ?";
	private final String insert = "insert into tb_usuario(usuario, senha, perfil) VALUES(?, ?, ?)";
	private final String delete = "delete from tb_usuario where id = ?";
	private final String update = "update tb_usuario set perfil = ?, senha = ? where usuario = ? ";
	
	private PreparedStatement pstSelect;
	private PreparedStatement pstSelectWhere;
	private PreparedStatement pstSelectUsuario;
	private final PreparedStatement psInsert;
	private final PreparedStatement psDelete;
	private final PreparedStatement psUpdate;
	
	public UsuarioDAO(final Connection conexao) throws SQLException 
	{
		pstSelect = conexao.prepareStatement(select);
		pstSelectWhere = conexao.prepareStatement(selectWhere);
		pstSelectUsuario = conexao.prepareStatement(selectUsuario);
		psInsert = conexao.prepareStatement(insert);
		psDelete = conexao.prepareStatement(delete);
		psUpdate = conexao.prepareStatement(update);
	}
	
	@Override
	public List<Object> Select() throws SQLException 
	{
		
		List<Object> listaUsuarios = new ArrayList<Object>();
		ResultSet resultado = pstSelect.executeQuery();
		if (resultado != null) 
		{
			while (resultado.next()) 
			{
				USUARIO usuario = new USUARIO();
				usuario.setId(resultado.getInt("id"));
				usuario.setUsuario(resultado.getString("usuario"));
				usuario.setSenha(resultado.getString("senha"));
				usuario.setPerfil(resultado.getString("perfil"));
				listaUsuarios.add(usuario);
			}
			resultado.close();
		}		
		return listaUsuarios;
	}
	
	public USUARIO SelectWhere(final String usuario, final String senha) throws SQLException 
	{
		
		USUARIO u = null;
		
		pstSelectWhere.setString(1, usuario);
		pstSelectWhere.setString(2, senha);
		
		ResultSet resultado = pstSelectWhere.executeQuery();
		if (resultado != null && resultado.next()) 
		{		
			u = new USUARIO();
			u.setId(resultado.getInt("id"));
			u.setUsuario(resultado.getString("usuario"));
			u.setSenha(resultado.getString("senha"));
			u.setPerfil(resultado.getString("perfil"));
		}
		
		return u;
	}
	
	public USUARIO SelectUsuario(final String usuario) throws SQLException 
	{
		
		USUARIO u = null;
		
		pstSelectUsuario.setString(1, usuario);
		
		ResultSet resultado = pstSelectUsuario.executeQuery();
		if (resultado != null && resultado.next()) 
		{		
			u = new USUARIO();
			u.setId(resultado.getInt("id"));
			u.setUsuario(resultado.getString("usuario"));
			u.setSenha(resultado.getString("senha"));
			u.setPerfil(resultado.getString("perfil"));
		}
		
		return u;
	}

	@Override
	public int Insert(Object parametros) throws SQLException
	{
		psInsert.clearParameters();
		
		USUARIO cad = (USUARIO)parametros;
			
		psInsert.setString(1, cad.getUsuario());
		psInsert.setString(2, cad.getSenha());
		psInsert.setString(3, cad.getPerfil());
		
		psInsert.execute();
		return  0;
	}

	@Override
	public void Update(Object parametros) throws SQLException 
	{
		psUpdate.clearParameters();
		
		USUARIO cad = (USUARIO)parametros;
			
		psUpdate.setString(1, cad.getPerfil());
		psUpdate.setString(2, cad.getSenha());
		psUpdate.setString(3, cad.getUsuario());
		
		psUpdate.execute();
	}

	@Override
	public void Delete(Object parametros) throws SQLException 
	{
		psDelete.clearParameters();
		
		psDelete.setInt(1, (Integer)parametros);
		
		psDelete.execute();
		
	}

}
