package br.com.simpleworks.educacao.dominio.turma;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import br.com.simpleworks.foundation.entity.BaseEntity;

@Data
@ToString(of = {"id", "nomeTurno"})
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "TURNO")
@NamedQueries({
	@NamedQuery(name = Turno.OBTER_PARA_SELECAO_TURMA, query = Turno.OBTER_PARA_SELECAO_TURMA_QUERY) 
})
public class Turno implements BaseEntity<Integer> {

	public static final String OBTER_PARA_SELECAO_TURMA = "Turno.obterParaSelecaoTurma";
	static final String OBTER_PARA_SELECAO_TURMA_QUERY = "SELECT T FROM Turno T";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ID_TURNO = "idTurno";
	
	@Id
	@Column(name = "ID_TURNO")
	private Integer id;

	@Column(name = "NOME_TURNO")
	private String nomeTurno;

}
