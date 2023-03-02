package br.com.simpleworks.educacao.dominio.turma;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import br.com.simpleworks.educacao.dominio.aluno.Aluno;
import br.com.simpleworks.educacao.dominio.aluno.AlunoTurma;
import br.com.simpleworks.educacao.dominio.frequencia.ChamadaTurma;
import br.com.simpleworks.foundation.entity.BaseEntity;
import br.com.simpleworks.foundation.util.DateUtil;

@Data
@ToString(of = {"id", "nomeTurma"})
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "TURMA")
@NamedQueries({
	@NamedQuery(name = Turma.OBTER_PARA_CHAMADA, query = Turma.OBTER_PARA_CHAMADA_QUERY),
	@NamedQuery(name = Turma.OBTER_POR_SERIE_POR_TURNO, query = Turma.OBTER_POR_SERIE_POR_TURNO_QUERY)
})
public class Turma implements BaseEntity<Integer> {

	public static final String ID_TURMA = "idTurma";
	public static final String OBTER_PARA_CHAMADA = "Turma.obterParaChamada";
	static final String OBTER_PARA_CHAMADA_QUERY = "SELECT T FROM Turma T " + 
													" LEFT JOIN FETCH T.alunosTurma T_AT " + 
													" LEFT JOIN FETCH T_AT.aluno T_AT_A" +
													" LEFT JOIN FETCH T_AT_A.alunosTurma T_AT_A_AT" +
													" LEFT JOIN FETCH T_AT_A.chamadasAluno T_AT_A_CA" +
													" LEFT JOIN FETCH T.chamadasTurma T_CT " + 
													" LEFT JOIN FETCH T.serie T_S " + 
													" LEFT JOIN FETCH T_S.ensino T_S_E " + 
													" WHERE T.id = :" + ID_TURMA;
	
	public static final String OBTER_POR_SERIE_POR_TURNO = "Turma.obterPorSeriePorTurno";
	static final String OBTER_POR_SERIE_POR_TURNO_QUERY = "SELECT distinct T FROM Turma T " +
															" LEFT JOIN FETCH T.alunosTurma T_AT " + 
															" LEFT JOIN FETCH T_AT.aluno T_AT_A" +
															" LEFT JOIN FETCH T_AT_A.alunosTurma T_AT_A_AT" +
															" LEFT JOIN FETCH T_AT_A.chamadasAluno T_AT_A_CA" +
															" LEFT JOIN FETCH T.chamadasTurma T_CT " + 
															" LEFT JOIN FETCH T.serie T_S " + 
															" LEFT JOIN FETCH T_S.ensino T_S_E " + 
															" WHERE (:" + Serie.ID_SERIE + " IS null OR T.serie.id = :" + Serie.ID_SERIE + ") " +
															" AND (:" + Turno.ID_TURNO + " IS null OR T.turno.id = :" + Turno.ID_TURNO + ") ";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_TURMA")
	private Integer id;

	@Column(name = "NOME_TURMA")
	private String nomeTurma;

	@ManyToOne
	@JoinColumn(name = "ID_SERIE")
	private Serie serie;

	@ManyToOne
	@JoinColumn(name = "ID_TURNO")
	private Turno turno;

	@Column(name = "ANO", columnDefinition = "char(4)")
	private String ano;

	@Column(name = "TOTAL_VAGAS")
	private Integer totalVagas;

	@OneToMany(mappedBy = "turma")
	private Set<AlunoTurma> alunosTurma;

	@OneToMany(mappedBy = "turma")
	private Set<ChamadaTurma> chamadasTurma;

	@Column(name = "DATA_INICIO")
	private Date dataInicio;

	@Column(name = "DATA_FIM")
	private Date dataFim;
	
	/**
	 * Armazena a data da chamada selecionada na pesquisa.
	 */
	@Transient
	private Date dataChamada;

	/**
	 * Pesquisa um aluno por Id. Retorna null caso não encontre.
	 * 
	 * @param matricula
	 * @return
	 * @author João Dimas
	 * @since 02/03/2012
	 */
	public Aluno getAlunoPorId(Long idAluno) {
		for (Aluno aluno : getAlunosAtivos()) {
			if (aluno.getId().equals(idAluno)) {
				return aluno;
			}
		}

		return null;
	}

	/**
	 * Obtém os alunos ativos na turma.
	 * 
	 * @return
	 * @author João Dimas
	 * @since 06/03/2012
	 */
	public List<Aluno> getAlunosAtivos() {
		List<Aluno> result = new ArrayList<Aluno>();
		for (AlunoTurma alunoTurma : alunosTurma) {
			if (alunoTurma.getDataSaidaAlunoTurma() == null) {
				result.add(alunoTurma.getAluno());
			}
		}

		return result;
	}

	/**
	 * Inicia uma nova chamada para a Turma atual.
	 * 
	 * @param dataChamada
	 * @return
	 * @author João Dimas
	 * @since 12/03/2012
	 */
	public ChamadaTurma novaChamada(Date dataChamada) {
		ChamadaTurma chamadaTurma = new ChamadaTurma();

		chamadaTurma.getId().setIdTurma(this.getId());
		chamadaTurma.getId().setDataChamada(dataChamada);
		chamadaTurma.setTurma(this);

		// Inicializa todos os alunos como presentes.
		for (Aluno aluno : getAlunosAtivos()) {
			chamadaTurma.presente(aluno.getId());
		}

		chamadaTurma.setNovaChamada(true);
		
		return chamadaTurma;
	}
	
	/**
	 * Se a turma está ativa (A data atual é maior que a data inicio mas não tem data fim). 
	 * 
	 * @return
	 * @author João Dimas
	 * @since 20/03/2012
	 */
	public boolean isAtivo() {
		Date hoje = new Date();
		return hoje.after(dataInicio) && (dataFim == null || DateUtil.dataMaiorOuIgual(dataFim, hoje));
	}
	
	/**
	 * Obtém a última chamada da turma.
	 * 
	 * @return
	 * @author João Dimas
	 * @since 20/03/2012
	 */
	public ChamadaTurma getUltimaChamada() {
		ChamadaTurma ultimaChamada = null;
		for(ChamadaTurma chamadaTurma : chamadasTurma) {
			if(ultimaChamada == null || DateUtil.dataMaior(chamadaTurma.getDataChamada(), ultimaChamada.getDataChamada())) {
				ultimaChamada = chamadaTurma;
			}
		}
		
		return ultimaChamada;
	}
	
	/**
	 * Obtém uma chamada dessa turma por data.
	 * 
	 * @param dataChamada
	 * @return
	 */
	public ChamadaTurma getChamadaPorData(Date dataChamada) {
		for(ChamadaTurma chamadaTurma : chamadasTurma) {
			if(DateUtil.dataIgual(dataChamada, chamadaTurma.getDataChamada())){
				return chamadaTurma;
			}
		}
		
		return null;
	}
	
	/**
	 * Verifica se a chamada está pendente para essa turma.
	 * 
	 * @param dataChamada
	 * @return
	 */
	public boolean isChamadaPendente(Date dataChamada) {
		return getChamadaPorData(dataChamada) == null;
	}
	
	/**
	 * Informa se a chamada está pendente considerando o atributo dataChamada.
	 * @return
	 */
	public boolean isChamadaPendente() {
		if(dataChamada == null) {
			throw new IllegalStateException("Turma: dataChamada está null. id="+id);
		}

		return getChamadaPorData(dataChamada) == null;
	}
	
}
