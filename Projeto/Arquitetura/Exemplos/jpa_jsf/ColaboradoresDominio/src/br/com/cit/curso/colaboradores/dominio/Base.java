package br.com.cit.curso.colaboradores.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = Base.CONSULTAR_TODAS, query = "SELECT B FROM Base B")
public class Base implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String CONSULTAR_TODAS = "Base.consultarTodas";

	@Id
	@Column(name = "CODIGO_BASE")
	private String codigoBase;

	private String nome;

	public String getCodigoBase() {
		return codigoBase;
	}

	public void setCodigoBase(String codigoBase) {
		this.codigoBase = codigoBase;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Base [codigoBase=" + codigoBase + ", nome=" + nome + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codigoBase == null) ? 0 : codigoBase.hashCode());
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
		Base other = (Base) obj;
		if (codigoBase == null) {
			if (other.codigoBase != null)
				return false;
		} else if (!codigoBase.equals(other.codigoBase))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
