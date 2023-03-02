package br.com.simpleworks.educacao.dominio.frequencia;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import br.com.simpleworks.educacao.dominio.SimNao;
import br.com.simpleworks.educacao.dominio.aluno.Aluno;
import br.com.simpleworks.foundation.entity.BaseEntity;

@Entity
@EqualsAndHashCode(of = { "id" })
@Table(name = "CHAMADA_ALUNO")
public class ChamadaAluno implements BaseEntity<ChamadaAlunoKey> {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@Getter
	@Setter
	private ChamadaAlunoKey id = new ChamadaAlunoKey();

	@ManyToOne
	@JoinColumn(name = "ID_ALUNO", insertable = false, updatable = false)
	@Getter
	private Aluno aluno;

	@Column(name = "PRESENTE", columnDefinition = "char(1)")
	@Enumerated(EnumType.STRING)
	private SimNao presente;

	public ChamadaAluno(Aluno aluno, int idTurma, Date dataChamada) {
		this.id.setIdAluno(aluno.getId());
		this.id.setIdTurma(idTurma);
		this.id.setDataChamada(dataChamada);
		this.aluno = aluno;
	}

	public ChamadaAluno() {

	}

	public void setPresente(boolean presente) {
		this.presente = (presente ? SimNao.S : SimNao.N);
	}

	public Boolean isPresente() {
		if (this.presente != null)
			return this.presente == SimNao.S;
		else
			return null;
	}
}
