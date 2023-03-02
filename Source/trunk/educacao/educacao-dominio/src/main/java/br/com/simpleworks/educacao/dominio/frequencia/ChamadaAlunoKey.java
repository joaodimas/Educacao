package br.com.simpleworks.educacao.dominio.frequencia;

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
public class ChamadaAlunoKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8177335074689990479L;

	@Column(name = "ID_ALUNO")
	private Long idAluno;
	
	@Column(name = "ID_TURMA")
	private Integer idTurma;
	
	@Column(name = "DATA_CHAMADA")
	@Temporal(TemporalType.DATE)
	private Date dataChamada;
	
	public ChamadaAlunoKey() {
		
	}
	
}
