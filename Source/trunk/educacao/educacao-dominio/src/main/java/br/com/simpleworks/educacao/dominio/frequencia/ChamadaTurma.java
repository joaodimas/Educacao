package br.com.simpleworks.educacao.dominio.frequencia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import br.com.simpleworks.educacao.dominio.aluno.Aluno;
import br.com.simpleworks.educacao.dominio.turma.Turma;
import br.com.simpleworks.foundation.entity.BaseEntity;

@Entity
@EqualsAndHashCode(of = {"id"})
@Table(name = "CHAMADA_TURMA")
public class ChamadaTurma implements BaseEntity<ChamadaTurmaKey> {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	@Getter
	@Setter
	private ChamadaTurmaKey id = new ChamadaTurmaKey();

	@ManyToOne
	@JoinColumn(name = "ID_TURMA", insertable = false, updatable = false)
	@Getter
	@Setter
	private Turma turma;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumns({ @JoinColumn(name = "ID_TURMA", referencedColumnName = "ID_TURMA", insertable = false, updatable = false), @JoinColumn(name = "DATA_CHAMADA", referencedColumnName = "DATA_CHAMADA", insertable = false, updatable = false) })
	private Set<ChamadaAluno> chamadasAluno = new HashSet<ChamadaAluno>();

	@Transient
	@Getter
	@Setter
	private boolean novaChamada;
	
	public ChamadaTurma() {
	}
	
	public Date getDataChamada() {
		return this.id.getDataChamada();
	}

	/**
	 * Obtém uma Chamada existente para um Aluno.
	 * 
	 * @param idAluno
	 * @return
	 * @author João Dimas
	 * @since 12/03/2012
	 */
	private ChamadaAluno getChamadaAlunoPorIdAluno(long idAluno) {
		for (ChamadaAluno chamadaAluno : chamadasAluno) {
			if (chamadaAluno.getId().getIdAluno() == idAluno) {
				return chamadaAluno;
			}
		}

		return null;
	}

	/**
	 * Registra um aluno como Presente.
	 * 
	 * @param idAluno
	 * @author João Dimas
	 * @since 12/03/2012
	 */
	public void presente(Long idAluno) {
		registrarChamadaAluno(idAluno, true);
	}

	/**
	 * Registra um aluno como Ausente.
	 * 
	 * @param idAluno
	 * @author João Dimas
	 * @since 12/03/2012
	 */
	public void ausente(Long idAluno) {
		registrarChamadaAluno(idAluno, false);
	}

	/**
	 * Registra ausência ou presença para um aluno.
	 * 
	 * @param idAluno
	 * @param presente
	 * @author João Dimas
	 * @since 12/03/2012
	 */
	private void registrarChamadaAluno(Long idAluno, boolean presente) {
		ChamadaAluno chamadaAluno = getChamadaAlunoPorIdAluno(idAluno);
		if (chamadaAluno == null) {
			chamadaAluno = new ChamadaAluno(turma.getAlunoPorId(idAluno), getId().getIdTurma(), getId().getDataChamada());
			this.chamadasAluno.add(chamadaAluno);
		}

		chamadaAluno.setPresente(presente);
	}

	/**
	 * Obtém os alunos Presentes.
	 * 
	 * @return
	 * @author João Dimas
	 * @since 12/03/2012
	 */
	public List<Aluno> getAlunosPresentes() {
		List<Aluno> result = new ArrayList<Aluno>();
		for (ChamadaAluno chamadaAluno : this.chamadasAluno) {
			if (chamadaAluno.isPresente()) {
				result.add(chamadaAluno.getAluno());
			}
		}

		Collections.sort(result, Aluno.comparatorByNomeAluno());

		return result;
	}

	/**
	 * Obtém os alunos Ausentes.
	 * 
	 * @return
	 * @author João Dimas
	 * @since 12/03/2012
	 */
	public List<Aluno> getAlunosAusentes() {
		List<Aluno> result = new ArrayList<Aluno>();
		for (ChamadaAluno chamadaAluno : this.chamadasAluno) {
			if (!chamadaAluno.isPresente()) {
				result.add(chamadaAluno.getAluno());
			}
		}

		Collections.sort(result, Aluno.comparatorByNomeAluno());

		return result;
	}
}
