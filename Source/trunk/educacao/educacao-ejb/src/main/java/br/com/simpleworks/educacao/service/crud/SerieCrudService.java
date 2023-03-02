package br.com.simpleworks.educacao.service.crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.simpleworks.educacao.dominio.turma.Serie;
import br.com.simpleworks.educacao.service.crud.base.BaseCrudService;

public class SerieCrudService extends BaseCrudService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Serie> obterTodos() {
		List<Serie> series = (List<Serie>) em.createNamedQuery(Serie.OBTER_TODOS).getResultList();
		return series;
	}
}
