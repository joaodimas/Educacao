package br.com.simpleworks.educacao.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.commons.logging.Log;

import br.com.simpleworks.educacao.dominio.aluno.Aluno;
import br.com.simpleworks.educacao.dominio.frequencia.ChamadaTurma;
import br.com.simpleworks.educacao.dominio.turma.Serie;
import br.com.simpleworks.educacao.dominio.turma.Turma;
import br.com.simpleworks.educacao.dominio.turma.Turno;
import br.com.simpleworks.educacao.service.base.BaseService;
import br.com.simpleworks.foundation.exception.ApplicationException;
import br.com.simpleworks.foundation.util.DateUtil;

@Stateful
public class RegistroChamadaService extends BaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Length da string básica da mensagem de aluno ausente.
	 */

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager em;

	@Inject
	private EnvioSMSService envioSMSService;
	
	@Inject
	private Log logger;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void salvarChamada(ChamadaTurma chamadaTurma) throws ApplicationException {
		em.persist(chamadaTurma);

		notificarResponsaveisAlunosAusentes(chamadaTurma.getAlunosAusentes(), chamadaTurma.getDataChamada());
	}

	private void notificarResponsaveisAlunosAusentes(List<Aluno> alunosAusentes, Date dataChamada) throws ApplicationException {
		String dataChamadaStr = DateUtil.formatShort(dataChamada);
		for (Aluno aluno : alunosAusentes) {
			if (aluno.getNomeResponsavel() != null && aluno.getDddCelularResponsavel() != null && aluno.getNumeroCelularResponsavel() != null) {
				String texto = "Prezado Sr(a) " + aluno.getNomeResponsavel() + ", informamos que seu filho(a) " + aluno.getNomeAluno() + " faltou às aulas no dia " + dataChamadaStr + ".";
				// TODO: verificar tamanho máximo do texto.
				envioSMSService.enfileirarMensagem(texto, aluno.getDddCelularResponsavel(), aluno.getNumeroCelularResponsavel());
			} else {
				logger.warn("Aluno não possui Nome e Celular de Responsável. Verificar cadastro: " + aluno.getMatricula() + "-" + aluno.getNomeAluno());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Turma> obterTurmasParaChamada(Integer idTurno, Integer idSerie) {
		em.clear();
		List<Turma> turmas = (List<Turma>) em.createNamedQuery(Turma.OBTER_POR_SERIE_POR_TURNO).setParameter(Turno.ID_TURNO, idTurno).setParameter(Serie.ID_SERIE, idSerie).getResultList();

		return turmas;
	}
}
