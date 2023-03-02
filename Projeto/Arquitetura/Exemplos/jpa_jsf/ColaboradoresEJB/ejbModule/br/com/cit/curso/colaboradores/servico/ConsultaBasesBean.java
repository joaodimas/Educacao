package br.com.cit.curso.colaboradores.servico;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.cit.curso.colaboradores.dominio.Base;

@Stateless
public class ConsultaBasesBean implements ConsultaBases {

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Base> consultarTodas() {
		return entityManager.createNamedQuery(Base.CONSULTAR_TODAS).getResultList();
	}

}
