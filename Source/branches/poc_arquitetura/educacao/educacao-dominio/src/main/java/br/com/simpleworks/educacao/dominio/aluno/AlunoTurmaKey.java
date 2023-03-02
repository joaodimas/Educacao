package br.com.simpleworks.educacao.dominio.aluno;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@Embeddable
public class AlunoTurmaKey implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_ALUNO")
	private Long idAluno;
	
	@Column(name = "ID_TURMA")
	private Integer idTurma;
	
	@Column(name = "DATA_ENTRADA_ALUNO_TURMA")
	@Temporal(TemporalType.DATE)
	private Date dataEntradaAlunoTurma;
	
	public AlunoTurmaKey() {
		
	}
}
