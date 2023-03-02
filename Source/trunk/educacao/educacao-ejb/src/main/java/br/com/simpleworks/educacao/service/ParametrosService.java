package br.com.simpleworks.educacao.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.simpleworks.educacao.dominio.controle.Parametro;
import br.com.simpleworks.educacao.service.base.BaseService;
import br.com.simpleworks.foundation.util.EntityUtil;

@Singleton
@Startup
public class ParametrosService extends BaseService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	
	private Map<String, Parametro> mapParametros;
	
	/**
	 * Obtém um parâmetro por Id.
	 *
	 * @author João Dimas
	 * @since 03/05/2012
	 * @param idParametro
	 * @return
	 */
	public Parametro obterPorId(String idParametro) {
		Parametro parametro = mapParametros.get(idParametro);
		if(parametro.isAtualizarOnline()) {
			parametro = em.find(Parametro.class, idParametro);
		}
		
		return parametro;
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		List<Parametro> list = em.createNamedQuery(Parametro.OBTER_TODOS).getResultList();
		mapParametros = EntityUtil.getEntityListAsMap(list);
	}
}
