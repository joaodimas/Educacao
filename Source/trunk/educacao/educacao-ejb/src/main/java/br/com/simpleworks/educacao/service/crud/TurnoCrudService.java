package br.com.simpleworks.educacao.service.crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.simpleworks.educacao.dominio.turma.Turno;
import br.com.simpleworks.educacao.service.crud.base.BaseCrudService;

public class TurnoCrudService extends BaseCrudService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Turno> obterTodos() {
		List<Turno> turnos = (List<Turno>) em.createNamedQuery(Turno.OBTER_TODOS).getResultList();
		return turnos;
	}
}
