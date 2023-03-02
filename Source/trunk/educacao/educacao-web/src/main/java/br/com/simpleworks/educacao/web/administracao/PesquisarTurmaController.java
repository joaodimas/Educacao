package br.com.simpleworks.educacao.web.administracao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;

import lombok.Getter;
import lombok.Setter;
import br.com.simpleworks.educacao.dominio.turma.Serie;
import br.com.simpleworks.educacao.dominio.turma.Turma;
import br.com.simpleworks.educacao.dominio.turma.Turno;
import br.com.simpleworks.educacao.service.crud.SerieCrudService;
import br.com.simpleworks.educacao.service.crud.TurmaCrudService;
import br.com.simpleworks.educacao.service.crud.TurnoCrudService;
import br.com.simpleworks.educacao.web.base.BaseController;

@Model
public class PesquisarTurmaController extends BaseController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Inject
	 */
	// [start]
	private SerieCrudService serieManager;
	private TurnoCrudService turnoManager;
	private TurmaCrudService turmaManager;
	// [end]
	
	/*
	 * Seleção de Turma
	 */
	@Getter
	private List<Turno> turnos;
	@Getter
	private List<Serie> series;
	@Getter
	private List<Turma> turmas;
	@Getter
	@Setter
	private Integer idTurnoSelected;
	@Getter
	@Setter
	private Integer idSerieSelected;
	@Getter
	@Setter
	private Integer idTurmaSelected;
	
	/*
	 * Actions
	 */
	// [start]
	public void selecionarTurmaChamadaPreRender() {
		if (!isViewPostback()) {
			turnos = turnoManager.obterTodos();
			series = serieManager.obterTodos();
			turmas = new ArrayList<Turma>();
			idSerieSelected = null;
			idTurmaSelected = null;
			idTurnoSelected = null;
		}
	}
	
	public void pesquisarTurmas() {	
		Integer idTurno = null;
		Integer idSerie = null;

		if (idTurnoSelected != null && idTurnoSelected != 0) {
			idTurno = idTurnoSelected;
		}
		if (idSerieSelected != null && idSerieSelected != 0) {
			idSerie = idSerieSelected;
		}

		turmas = turmaManager.obterPorTurnoPorSerie(idTurno, idSerie);
	}
	// [end]
}
