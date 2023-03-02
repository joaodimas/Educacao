package br.com.cit.curso.colaboradores.dominio;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
		@NamedQuery(name = Colaborador.CONSULTAR_POR_BASE, query = "SELECT C FROM Colaborador C INNER JOIN FETCH C.base WHERE C.base.codigoBase = :codigoBase"),
		@NamedQuery(name = Colaborador.CONSULTAR_POR_CPF, query = "SELECT C FROM Colaborador C WHERE C.cpf = :cpf") })
public class Colaborador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String CONSULTAR_POR_BASE = "Colaborador.consultarPorBase";
	public static final String CONSULTAR_POR_CPF = "Colaborador.consultarPorCpf";

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SQC_GEN")
	@SequenceGenerator(name = "ID_SQC_GEN", sequenceName = "COLABORADOR_ID_SEQUENCE", allocationSize = 1)
	private Integer id;

	@Column
	private String cpf;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CODIGO_BASE")
	private Base base;

	@Column
	private String nome;

	@Column(name = "DATA_NASCIMENTO")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String toString() {
		return "Colaborador [id=" + id + ", cpf=" + cpf + ", base=" + base
				+ ", nome=" + nome + ", dataNascimento=" + dataNascimento + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((base == null) ? 0 : base.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + id;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Colaborador other = (Colaborador) obj;
		if (base == null) {
			if (other.base != null)
				return false;
		} else if (!base.equals(other.base))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
