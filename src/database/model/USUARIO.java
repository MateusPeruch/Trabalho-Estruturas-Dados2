package database.model;

public class USUARIO 
{
	
	private int id;
	private String usuario;
	private String senha;
	private String perfil;
	
	public USUARIO() {}
	public USUARIO(final String usuario, final String senha, final String perfil) 
	{
		this.usuario = usuario;
		this.senha = senha;
		this.perfil = perfil;
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public String getUsuario() 
	{
		return usuario;
	}

	public void setUsuario(String usuario) 
	{
		this.usuario = usuario;
	}

	public String getSenha() 
	{
		return senha;
	}

	public void setSenha(String senha) 
	{
		this.senha = senha;
	}

	public String getPerfil() 
	{
		return perfil;
	}

	public void setPerfil(String perfil) 
	{
		this.perfil = perfil;
	}

}
