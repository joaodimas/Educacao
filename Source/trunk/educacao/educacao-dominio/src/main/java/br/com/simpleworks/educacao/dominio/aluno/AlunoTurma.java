package br.com.simpleworks.educacao.dominio.aluno;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import br.com.simpleworks.educacao.dominio.turma.Turma;
import br.com.simpleworks.foundation.entity.BaseEntity;

@Data
@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "ALUNO_TURMA")
public class AlunoTurma implements BaseEntity<AlunoTurmaKey> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private AlunoTurmaKey id;
	
	@ManyToOne
	@JoinColumn(name = "ID_ALUNO", updatable = false, insertable = false)
	private Aluno aluno;
	
	@ManyToOne
	@JoinColumn(name = "ID_TURMA", updatable = false, insertable = false)
	private Turma turma;

	@Column(name = "DATA_SAIDA_ALUNO_TURMA")
	@Temporal(TemporalType.DATE)
	private Date dataSaidaAlunoTurma;
}
