package database.model;

public class CURSO 
{

	private String nomeCurso;
	private int id;
	
	public CURSO() 
	{
		
	}
	
	public CURSO(final String nomeCurso)
	{
		this.nomeCurso = nomeCurso;
	}
	
	public void setNomeCurso(final String nomeCurso)
	{
		this.nomeCurso = nomeCurso;
	}
	
	public String getNomeCurso()
	{
		return nomeCurso;
	}
	
	public int getId()
	{
		return id;
	}
	
    public void setId(int id) 
    {
        this.id = id;
    }
	
}
