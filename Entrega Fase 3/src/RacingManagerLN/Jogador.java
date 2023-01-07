package RacingManagerLN;

public class Jogador {
	private String nome;
	private String password;
	private int pontuacao;
	private boolean autenticado;
	private String versao;

	public Jogador(String nome, String password, int pontuacao, boolean autenticado, String versao)
	{
		this.nome = nome;
		this.password = password;
		this.pontuacao = pontuacao;
		this.autenticado = autenticado;
		this.versao = versao;
	}

	public String getNome()
	{
		return this.nome;
	}

	public String getPassword()
	{
		return this.password;
	}

	public int getPontuacao()
	{
		return this.pontuacao;
	}

	public String getVersao()
	{
		return versao;
	}

	public void addPontuacao(int pontos) {
		int pontosatuais = this.pontuacao;
		this.pontuacao = pontosatuais+pontos;
	}

	public boolean getAutenticado() {
		return autenticado;
	}

	public boolean isAdministrador()
	{
		boolean admin = false;
		if (this instanceof Administrador) {
			admin = true;
		}
		return admin;
	}
}