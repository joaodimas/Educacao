package br.com.simpleworks.educacao.service;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import br.com.simpleworks.educacao.dominio.frequencia.ChamadaTurma;
import br.com.simpleworks.educacao.dominio.turma.Serie;
import br.com.simpleworks.educacao.dominio.turma.Turma;
import br.com.simpleworks.educacao.dominio.turma.Turno;
import br.com.simpleworks.foundation.exception.ApplicationException;

@Stateful
public class RegistroChamadaService {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void salvarChamada(ChamadaTurma chamadaTurma) throws ApplicationException {
		em.persist(chamadaTurma);
	}

	@SuppressWarnings("unchecked")
	public List<Turma> obterTurmas(Integer idTurno, Integer idSerie) {
		List<Turma> turmas = (List<Turma>) em
				.createNamedQuery(Turma.OBTER_PARA_SELECAO_TURMA)
				.setParameter(Turno.ID_TURNO, idTurno)
				.setParameter(Serie.ID_SERIE, idSerie).getResultList();

		return turmas;
	}

	@SuppressWarnings("unchecked")
	public List<Turma> obterTurmas() {
		List<Turma> turmas = (List<Turma>) em
				.createNamedQuery(Turma.OBTER_PARA_SELECAO_TURMA)
				.setParameter(Turno.ID_TURNO, null)
				.setParameter(Serie.ID_SERIE, null).getResultList();

		return turmas;
	}

	@SuppressWarnings("unchecked")
	public List<Turno> obterTurnos() {
		List<Turno> turnos = (List<Turno>) em.createNamedQuery(
				Turno.OBTER_PARA_SELECAO_TURMA).getResultList();
		return turnos;
	}

	@SuppressWarnings("unchecked")
	public List<Serie> obterSeries() {
		List<Serie> series = (List<Serie>) em.createNamedQuery(
				Serie.OBTER_PARA_SELECAO_TURMA).getResultList();
		return series;
	}

}
