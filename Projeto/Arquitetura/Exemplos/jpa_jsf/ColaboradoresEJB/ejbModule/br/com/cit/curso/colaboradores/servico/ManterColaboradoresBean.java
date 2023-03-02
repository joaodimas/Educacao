package br.com.cit.curso.colaboradores.servico;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import br.com.cit.curso.colaboradores.dominio.Colaborador;
import br.com.cit.curso.colaboradores.exceptions.ApplicationException;
import br.com.cit.curso.colaboradores.util.Constantes;

@Stateless
public class ManterColaboradoresBean implements ManterColaboradores {

	@PersistenceContext
	private EntityManager entityManager;

	public void excluirColaborador(String cpf) {
		Colaborador colaboradorExistente = consultarPorCpf(cpf);
		
		if(colaboradorExistente != null) {
			entityManager.remove(colaboradorExistente);
		} else {
			throw new ApplicationException(Constantes.BundleKeys.CadastroColaboradores.COLABORADOR_NAO_ENCONTRADO);
		}
	}
	
	public void salvarColaborador(Colaborador colaborador) {
		try {
			Colaborador colaboradorExistente = consultarPorCpf(colaborador.getCpf());
			if (colaboradorExistente != null) {
				throw new ApplicationException(
						Constantes.BundleKeys.CadastroColaboradores.COLABORADOR_JA_EXISTENTE_CPF);
			} else {
				this.entityManager.persist(colaborador);
				this.entityManager.flush();
			}
		} catch (PersistenceException ex) {
			throw new ApplicationException(ex.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Colaborador> consultarPorBase(String codigoBase) {
		List<Colaborador> result = entityManager
				.createNamedQuery(Colaborador.CONSULTAR_POR_BASE)
				.setParameter("codigoBase", codigoBase).getResultList();

		return result;
	}

	@SuppressWarnings("unchecked")
	public Colaborador consultarPorCpf(String cpf) {
		List<Colaborador> lista = entityManager
				.createNamedQuery(Colaborador.CONSULTAR_POR_CPF)
				.setParameter("cpf", cpf).getResultList();

		if (lista.size() > 0) {
			return lista.get(0);
		} else {
			return null;
		}
	}

}
