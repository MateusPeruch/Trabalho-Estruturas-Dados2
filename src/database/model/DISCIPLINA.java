package database.model;

public class DISCIPLINA {
	private String disciplina;
	private int  idfase;
	private String diaAtual;
	private String nomeProfessor;
	private int idDisciplina;

	public DISCIPLINA()
	{
		
	}

	public DISCIPLINA(String disciplina, String diaAtual, int idfase)
	{
		this.disciplina = disciplina;
		this.diaAtual = diaAtual;
		this.idfase = idfase;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public int getIdfase() {
		return idfase;
	}

	public void setIdfase(int idfase) {
		this.idfase = idfase;
	}

	public String getDiaAtual() {
		return diaAtual;
	}

	public void setDiaAtual(String diaAtual) {
		this.diaAtual = diaAtual;
	}

	public String getNomeProfessor() {
		return nomeProfessor;
	}

	public void setNomeProfessor(String nomeProfessor) {
		this.nomeProfessor = nomeProfessor;
	}

	public int getIdDisciplina() {
		return idDisciplina;
	}

	public void setIdDisciplina(int idDisciplina) {
		this.idDisciplina = idDisciplina;
	}
	

}
