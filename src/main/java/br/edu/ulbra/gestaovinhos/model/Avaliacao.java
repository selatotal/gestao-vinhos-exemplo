package br.edu.ulbra.gestaovinhos.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Avaliacao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false)
	private Vinho vinho;

	@ManyToOne(optional = false)
	private User user;

	@Column(nullable = false)
	private Boolean positivo;

	@Column(nullable = false)
	private String descricao;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Vinho getVinho() {
		return vinho;
	}

	public void setVinho(Vinho vinho) {
		this.vinho = vinho;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getPositivo() {
		return positivo;
	}

	public void setPositivo(Boolean positivo) {
		this.positivo = positivo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
}
