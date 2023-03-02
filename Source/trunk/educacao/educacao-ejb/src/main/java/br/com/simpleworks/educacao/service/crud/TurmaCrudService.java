package br.com.simpleworks.educacao.service.crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.simpleworks.educacao.dominio.turma.Serie;
import br.com.simpleworks.educacao.dominio.turma.Turma;
import br.com.simpleworks.educacao.dominio.turma.Turno;
import br.com.simpleworks.educacao.service.crud.base.BaseCrudService;

public class TurmaCrudService extends BaseCrudService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Turma> obterPorTurnoPorSerie(Integer idTurno, Integer idSerie) {
		List<Turma> turmas = (List<Turma>) em.createNamedQuery(Turma.OBTER_POR_SERIE_POR_TURNO).setParameter(Turno.ID_TURNO, idTurno).setParameter(Serie.ID_SERIE, idSerie).getResultList();

		return turmas;
	}
}
