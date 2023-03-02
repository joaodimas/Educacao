package br.com.simpleworks.educacao.web.frequencia;

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
import br.com.simpleworks.educacao.dominio.aluno.Aluno;
import br.com.simpleworks.educacao.dominio.frequencia.ChamadaTurma;
import br.com.simpleworks.educacao.dominio.turma.Serie;
import br.com.simpleworks.educacao.dominio.turma.Turma;
import br.com.simpleworks.educacao.dominio.turma.Turno;
import br.com.simpleworks.educacao.service.RegistroChamadaService;
import br.com.simpleworks.educacao.service.crud.SerieCrudService;
import br.com.simpleworks.educacao.service.crud.TurnoCrudService;
import br.com.simpleworks.educacao.web.base.BaseController;
import br.com.simpleworks.foundation.exception.ApplicationException;
import br.com.simpleworks.foundation.util.DateUtil;

@Named
@ConversationScoped
public class RegistrarChamadaController extends BaseController implements
		Serializable {

	//private static final String BUNDLE_REGISTRADO_COM_SUCESSO = "registrarChamada.mensagem.registradoComSucesso";

	private static final long serialVersionUID = 1L;

	private static final String VIEW_REGISTRARCHAMADA = "registrarChamada.jsf?faces-redirect=true";
	private static final String VIEW_SELECIONARTURMACHAMADA = "selecionarTurmaChamada.jsf?faces-redirect=true&cid=";
	
	/*
	 * Inject
	 */
	// [start]
	@Inject
	@Getter
	private Conversation conversation;
	@Inject
	private RegistroChamadaService registroChamadaService;
	@Inject
	private TurnoCrudService turnoManager;
	@Inject
	private SerieCrudService serieManager;
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
	@Getter	@Setter
	private String idsPresentes;
	@Getter	@Setter
	private String idsAusentes;
	@Getter @Setter
	private List<Aluno> alunosPresentes = new ArrayList<Aluno>();
	@Getter @Setter
	private List<Aluno> alunosAusentes = new ArrayList<Aluno>();
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

			turnos = turnoManager.obterTodos();
			series = serieManager.obterTodos();
			turmas = new ArrayList<Turma>();
			idSerieSelected = null;
			idTurmaSelected = null;
			idTurnoSelected = null;
			dataChamada = DateUtil.dataAtual();
		}
	}

	public String iniciarChamada() {
		Turma turma = getTurmaPorId(idTurmaSelected);
		
		chamadaTurma = turma.novaChamada(dataChamada);
		
		alunosPresentes = chamadaTurma.getAlunosPresentes();
		alunosAusentes = chamadaTurma.getAlunosAusentes();
				
		return VIEW_REGISTRARCHAMADA;
	}
	
	public String corrigirChamada() {
		Turma turma = getTurmaPorId(idTurmaSelected);
		
		chamadaTurma = turma.getChamadaPorData(dataChamada);
		
		alunosPresentes = chamadaTurma.getAlunosPresentes();
		alunosAusentes = chamadaTurma.getAlunosAusentes();
		
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

		List<Turma> result = registroChamadaService.obterTurmasParaChamada(idTurno, idSerie);
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
		
		for(Turma turma : turmas) {
			turma.setDataChamada(dataChamada);
		}
	}

	public String salvarChamada() throws ApplicationException {
		String[] arrayIdsPresentes = idsPresentes.split(",");
		String[] arrayIdsAusentes = idsAusentes.split(",");
		
		for (String idAluno : arrayIdsPresentes) {
			chamadaTurma.presente(Long.parseLong(idAluno));
		}
		for(String idAluno : arrayIdsAusentes) {
			chamadaTurma.ausente(Long.parseLong(idAluno));
		}
		
		registroChamadaService.salvarChamada(this.chamadaTurma);
		
		
	//	showSuccessAndRedirect(BUNDLE_REGISTRADO_COM_SUCESSO, VIEW_SELECIONARTURMACHAMADA);
		
		return VIEW_SELECIONARTURMACHAMADA;
	}

	public String cancelarChamada() {
		conversation.end();
		
		return VIEW_SELECIONARTURMACHAMADA;
	}

	// [end]

	/*
	 * Outros métodos
	 */
	// [start]
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
