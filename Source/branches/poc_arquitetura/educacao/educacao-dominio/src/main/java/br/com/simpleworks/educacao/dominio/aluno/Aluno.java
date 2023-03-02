package br.com.simpleworks.educacao.dominio.aluno;

import java.util.Comparator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import br.com.simpleworks.educacao.dominio.frequencia.ChamadaAluno;
import br.com.simpleworks.foundation.entity.BaseEntity;

@Data
@EqualsAndHashCode(of={"id"})
@ToString(of={"id", "matricula", "nomeAluno"})
@Entity
@Table(name = "ALUNO")
@NamedQueries({@NamedQuery(name = Aluno.CONSULTAR_POR_MATRICULA, query = Aluno.CONSULTAR_POR_MATRICULA_QUERY)})
public class Aluno implements BaseEntity<Long> {
	public final static String CONSULTAR_POR_MATRICULA = "Aluno.consultarPorMatricula";
	public final static String CONSULTAR_POR_MATRICULA_QUERY = "SELECT a FROM Aluno a WHERE a.matricula = :matricula";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ID_ALUNO")
	private Long id;
	
	@Column(name = "MATRICULA")
	private String matricula;
	
	@Column(name = "NOME_ALUNO")
	private String nomeAluno;
	
	@OneToMany(mappedBy = "aluno")
	private Set<AlunoTurma> alunosTurma;
	
	@OneToMany(mappedBy = "aluno")
	private Set<ChamadaAluno> chamadasAluno;
	
	public AlunoTurma getAlunoTurmaAtual() {
		for(AlunoTurma alunoTurma : alunosTurma) {
			if(alunoTurma.getDataSaidaAlunoTurma() == null) {
				return alunoTurma;
			}
		}
		
		return null;
	}
	
	public static Comparator<Aluno> comparatorByNomeAluno() {
		return new Comparator<Aluno>() {
			public int compare(Aluno o1, Aluno o2) {
				return o1.getNomeAluno().compareTo(o2.getNomeAluno());
			}
		};
	}
}
