package database.model;

public class PROFESSOR {

	private String nome;
	private int idDisciplina;
	private String formacaoProfessor;

	public PROFESSOR(){}

	public PROFESSOR(String nome, String formacaoProfessor, int idDisciplina){
		this.nome = nome;
		this.formacaoProfessor = formacaoProfessor;
		this.idDisciplina = idDisciplina;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdDisciplina() {
		return idDisciplina;
	}

	public void setIdDisciplina(int idDisciplina) {
		this.idDisciplina = idDisciplina;
	}

	public String getFormacaoProfessor() {return formacaoProfessor;}
}
