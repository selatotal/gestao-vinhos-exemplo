package br.edu.ulbra.gestaovinhos.input;

import org.springframework.web.multipart.MultipartFile;

public class VinhoInput {
	private Long id;
	private String nome;
	private String vinicola;
	private Long idTipo;
	private MultipartFile imagem;
	private String nomeImagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getVinicola() {
		return vinicola;
	}

	public void setVinicola(String vinicola) {
		this.vinicola = vinicola;
	}

	public Long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

	public MultipartFile getImagem() {
		return imagem;
	}

	public void setImagem(MultipartFile imagem) {
		this.imagem = imagem;
	}

	public String getNomeImagem() {
		return nomeImagem;
	}

	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}
}
