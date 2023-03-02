package br.com.cit.curso.colaboradores.web;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.cit.curso.colaboradores.arquitetura.StatusContext;
import br.com.cit.curso.colaboradores.dominio.Base;
import br.com.cit.curso.colaboradores.dominio.Colaborador;
import br.com.cit.curso.colaboradores.servico.ManterColaboradores;
import br.com.cit.curso.colaboradores.servico.ConsultaBases;
import br.com.cit.curso.colaboradores.util.Constantes;

@ManagedBean
@ViewScoped
public class CadastroColaboradoresController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private List<Base> bases;
	private Colaborador novoColaborador;

	@ManagedProperty(value="#{statusContext}")
	private StatusContext statusContext;
	
	@EJB
	private ManterColaboradores cadastroColaboradores;

	@EJB
	private ConsultaBases consultaBases;

	public void init() {
		if (this.bases == null) {
			this.bases = consultaBases.consultarTodas();
		}
		
		if(this.novoColaborador == null) {
			this.novoColaborador = new Colaborador();
			this.novoColaborador.setBase(new Base());
		}
	}

	public void salvarColaborador() {
		this.cadastroColaboradores.salvarColaborador(novoColaborador);
		this.statusContext.setCurrentMessage(Constantes.BundleKeys.CadastroColaboradores.COLABORADOR_CADASTRADO_COM_SUCESSO);
		this.novoColaborador = null;
	}

	public List<Base> getBases() {
		return bases;
	}

	public void setBases(List<Base> bases) {
		this.bases = bases;
	}

	public Colaborador getNovoColaborador() {
		return novoColaborador;
	}

	public void setNovoColaborador(Colaborador novoColaborador) {
		this.novoColaborador = novoColaborador;
	}
}
