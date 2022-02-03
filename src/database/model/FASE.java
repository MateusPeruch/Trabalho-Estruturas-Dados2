package database.model;

public class FASE {
	private String fase;

	private int idCurso;
	private String Nomefase;
	private int idFase;
	private String disciplina;
	private String diaSemana;
	private String nomeProfessor;
	private String tituloProfessor;
	private int idDisciplina;

	public String getNomefase() {
		return Nomefase;
	}

	public void setNomefase(String nomefase) {
		Nomefase = nomefase;
	}

	public FASE()
	{
		
	}


	public FASE(final String fase, int idCurso)
	{
		this.fase = fase;
		this.idCurso = idCurso;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public int getIdFase() {
		return idFase;
	}

	public void setIdFase(int idFase) {
		this.idFase = idFase;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public String getNomeProfessor() {
		return nomeProfessor;
	}

	public void setNomeProfessor(String nomeProfessor) {
		this.nomeProfessor = nomeProfessor;
	}

	public String getTituloProfessor() {
		return tituloProfessor;
	}

	public void setTituloProfessor(String tituloProfessor) {
		this.tituloProfessor = tituloProfessor;
	}

	public int getIdDisciplina() {
		return idDisciplina;
	}

	public void setIdDisciplina(int idDisciplina) {
		this.idDisciplina = idDisciplina;
	}
	
}
