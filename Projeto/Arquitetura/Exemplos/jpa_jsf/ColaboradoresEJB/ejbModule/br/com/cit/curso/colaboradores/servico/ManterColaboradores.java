package br.com.cit.curso.colaboradores.servico;

import java.util.List;

import javax.ejb.Local;

import br.com.cit.curso.colaboradores.dominio.Colaborador;

@Local
public interface ManterColaboradores {

	void salvarColaborador(Colaborador colaborador);

	List<Colaborador> consultarPorBase(String codigoBase);

	Colaborador consultarPorCpf(String cpf);

}
