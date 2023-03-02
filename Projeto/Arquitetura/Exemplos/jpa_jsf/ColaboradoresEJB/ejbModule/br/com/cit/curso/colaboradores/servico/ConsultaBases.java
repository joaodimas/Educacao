package br.com.cit.curso.colaboradores.servico;

import java.util.List;

import javax.ejb.Local;

import br.com.cit.curso.colaboradores.dominio.Base;

@Local
public interface ConsultaBases {

	List<Base> consultarTodas();
}
