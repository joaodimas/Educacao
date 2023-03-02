package br.com.simpleworks.educacao.web.registrarChamada;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;
import br.com.simpleworks.educacao.comum.Messages;
import br.com.simpleworks.educacao.dominio.frequencia.ChamadaTurma;
import br.com.simpleworks.educacao.dominio.turma.Serie;
import br.com.simpleworks.educacao.dominio.turma.Turma;
import br.com.simpleworks.educacao.dominio.turma.Turno;
import br.com.simpleworks.educacao.service.RegistroChamadaService;
import br.com.simpleworks.foundation.controller.Controller;
import br.com.simpleworks.foundation.exception.ApplicationException;
import br.com.simpleworks.foundation.util.DateUtil;

@Named
@ConversationScoped
public class RegistrarChamadaController extends Controller implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private static final String VIEW_REGISTRARCHAMADA = "registrarChamada.jsf?faces-redirect=true";
	private static final String VIEW_SELECIONARTURMACHAMADA = "selecionarTurmaChamada.jsf?faces-redirect=true";
	
	/*
	 * Inject
	 */
	// [start]
	@Inject
	@Getter
	private Conversation conversation;
	@Inject
	private RegistroChamadaService registroChamadaService;

	// [end]

	/*
	 * Seleção de turma
	 */
	// [start]
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
	@Getter
	@Setter
	private Date dataChamada;
	@Getter
	@Setter
	private boolean apenasPendentes;
	// [end]

	/*
	 * Registro da chamada
	 */
	// [start]
	@Getter
	@Setter
	private List<Integer> presentesSelection = new ArrayList<Integer>();
	@Getter
	@Setter
	private List<Integer> ausentesSelection = new ArrayList<Integer>();
	@Getter
	private ChamadaTurma chamadaTurma;

	// [end]

	/*
	 * Actions
	 */
	// [start]

	public void selecionarTurmaChamadaPreRender() {
		if (!isViewPostback()) {
			if(!conversation.isTransient()) {
				conversation.end();
			}
			
			conversation.begin();

			turnos = registroChamadaService.obterTurnos();
			series = registroChamadaService.obterSeries();
			turmas = new ArrayList<Turma>();
			idSerieSelected = null;
			idTurmaSelected = null;
			idTurnoSelected = null;
			dataChamada = DateUtil.dataAtual();
		}
	}

	public String iniciarChamada() {
		Turma turma = getTurmaPorId(idTurmaSelected);
		
		if(turma.isChamadaPendente(dataChamada)) {
			chamadaTurma = turma.novaChamada(dataChamada);
		} else {
			chamadaTurma = turma.getChamadaPorData(dataChamada);
		}
				
		return VIEW_REGISTRARCHAMADA;
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

		List<Turma> result = registroChamadaService.obterTurmas(idTurno, idSerie);
		if(apenasPendentes) {
			turmas = new ArrayList<Turma>();
			for(Turma turma : result) {
				if(turma.isChamadaPendente(dataChamada)) {
					turmas.add(turma);
				}
			}
		} else {
			turmas = result;
		}
	}

	public String salvarChamada() throws ApplicationException {
		registroChamadaService.salvarChamada(this.chamadaTurma);
		
		return VIEW_SELECIONARTURMACHAMADA;
	}

	public String cancelarChamada() {
		conversation.end();
		
		return VIEW_SELECIONARTURMACHAMADA;
	}

	public void moverSelecionadosParaAusentes() {
		for (Integer rowId : presentesSelection) {
			Long idAluno = this.chamadaTurma.getAlunosPresentes().get(rowId)
					.getId();
			this.chamadaTurma.ausente(idAluno);
		}
		presentesSelection.clear();
	}

	public void moverSelecionadosParaPresentes() {
		for (Integer rowId : ausentesSelection) {
			Long idAluno = this.chamadaTurma.getAlunosAusentes().get(rowId)
					.getId();
			this.chamadaTurma.presente(idAluno);
		}
		ausentesSelection.clear();
	}

	// [end]

	/*
	 * Outros métodos
	 */
	// [start]
	public Long getIdAluno(int rowId) {
		return this.chamadaTurma.getAlunosAusentes().get(rowId).getId();
	}

	public String getLabelDetalhesTurma() {
		return chamadaTurma.getTurma().getSerie().getEnsino().getNomeEnsino()
				+ ", " + chamadaTurma.getTurma().getTurno().getNomeTurno()
				+ " | " + chamadaTurma.getTurma().getSerie().getNomeSerie()
				+ " | " + chamadaTurma.getTurma().getNomeTurma();
	}

	public String getDataChamadaFormatado() {
		return new SimpleDateFormat(getFormatDataChamada()).format(chamadaTurma
				.getDataChamada());
	}

	public String getFormatDataChamada() {
		return getMessage(Messages.Comum.FORMAT_DATA_SHORT);
	}
	
	private Turma getTurmaPorId(int idTurma) {
		if(turmas != null) {
			for(Turma turma : turmas) {
				if(turma.getId().intValue() == idTurma) {
					return turma;
				}
			}
		}
		
		return null;
	}
	
	// [end]
}
