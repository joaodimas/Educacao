package br.com.cit.curso.colaboradores.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang3.StringUtils;

import br.com.cit.curso.colaboradores.dominio.Colaborador;
import br.com.cit.curso.colaboradores.servico.ManterColaboradores;

@ManagedBean
@RequestScoped
public class ConsultaColaboradoresController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String baseFiltro;
	private int currentRow;
	private List<Colaborador> colaboradores;

	@EJB
	private ManterColaboradores manterColaboradores;

	public ConsultaColaboradoresController() {

	}

	public void buscarColaboradores() {
		if (!StringUtils.isEmpty(baseFiltro)) {
			colaboradores = manterColaboradores.consultarPorBase(baseFiltro);
		}
	}

	public String getBaseFiltro() {
		return baseFiltro;
	}

	public void setBaseFiltro(String baseFiltro) {
		this.baseFiltro = baseFiltro;
	}

	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

	public void currentRow(ActionEvent event) {
		System.out.println("método chamado");
		this.colaboradores = new ArrayList<Colaborador>();
	}

	public int getCurrentRow() {
		return currentRow;
	}

	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}

}
