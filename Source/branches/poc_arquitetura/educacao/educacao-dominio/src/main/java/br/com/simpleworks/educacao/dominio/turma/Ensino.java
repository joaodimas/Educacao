package br.com.simpleworks.educacao.dominio.turma;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import br.com.simpleworks.foundation.entity.BaseEntity;

@Entity
@Table(name = "ENSINO")
@Data
@EqualsAndHashCode(of="id")
public class Ensino implements BaseEntity<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_ENSINO")
	private Integer id;
	
	@Column(name="NOME_ENSINO", length=200)
	private String nomeEnsino;
}
