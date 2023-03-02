package br.com.simpleworks.educacao.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;

import br.com.simpleworks.educacao.dominio.controle.MensagemSMS;
import br.com.simpleworks.educacao.dominio.controle.MensagemSMS.StatusMensagem;
import br.com.simpleworks.educacao.dominio.controle.Parametro;
import br.com.simpleworks.educacao.integration.mobilepronto.MPGateway;
import br.com.simpleworks.educacao.service.base.BaseService;
import br.com.simpleworks.foundation.exception.ApplicationException;

@Stateless
public class EnvioSMSService extends BaseService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String CODIGO_SUCESSO = "000";
	private static final String CREDENCIAL = "F8294AD8B9A3F775EBB3BDB78D75D5A896F4D047";
	/**
	 * Length máximo da mensagem enviada por SMS.
	 */
	public static final int LENGTH_MAXIMO_SMS = 0;

	@Inject
	private EntityManager em;
	@Inject
	private Log logger;
	@Inject
	private ParametrosService parametrosService;

	/**
	 * Enfileira uma mensagem para ser enviada posteriormente como SMS.
	 * 
	 * @author João Dimas
	 * @since 02/05/2012
	 * @param mensagem
	 * @param ddd
	 * @param numero
	 * @throws ApplicationException
	 */
	public void enfileirarMensagem(String texto, String ddd, String numero) throws ApplicationException {
		MensagemSMS novaMensagem = MensagemSMS.criarNovaMensagem(texto, ddd, numero);
		em.persist(novaMensagem);
	}

	/**
	 * Formata o número de celular.
	 * 
	 * @author João Dimas
	 * @since 02/05/2012
	 * @param ddd
	 * @param numero
	 * @return
	 */
	private String formatarCelularSMS(String ddd, String numero) {
		return "+55(" + ddd + ")" + numero;
	}

	/**
	 * Envia automaticamente as mensagens pendentes a cada 30 minutos.
	 * 
	 * @author João Dimas
	 * @since 02/05/2012
	 */
	@Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
	public void enviarMensagensPendentes() {
		try {
			boolean envioHabilitado = verificarEnvioHabilitado();

			if (envioHabilitado) {
				List<MensagemSMS> pendentes = obterPendentes();

				atualizarProcessando(pendentes);
				for (MensagemSMS mensagem : pendentes) {
					try {
						logger.info("Enviando mensagem. Id=" + mensagem.getId());
						String destino = formatarCelularSMS(mensagem.getDddCelularDestinatario(), mensagem.getNumeroCelularDestinatario());
						String texto = mensagem.getTexto();
						String result = new MPGateway().getMPGatewaySoap().mpgSendSimpleSMS(CREDENCIAL, destino, texto);

						if (result.equals(CODIGO_SUCESSO)) {
							atualizarSucessoEnvio(mensagem);
							logger.info("Mensagem enviada. Id=" + mensagem.getId());
						} else {
							atualizarErroEnvio(mensagem);
						}
					} catch (Exception ex) {
						atualizarErroEnvio(mensagem);
						logger.error("Falha ao enviar SMS. Id=" + mensagem.getId() + "\nErro=" + ex.getMessage(), ex);
					}
				}
			}
		} catch (Exception ex) {
			logger.error("Falha ao enviar mensagens pendentes.", ex);
		}
	}

	/**
	 * Verifica se o envio de SMS está habilitado.
	 *
	 * @author João Dimas
	 * @since 03/05/2012
	 * @return
	 */
	private boolean verificarEnvioHabilitado() {
		Parametro parametro = parametrosService.obterPorId(Parametro.ENVIAR_SMS_HABILITADO);
		boolean envioHabilitado = parametro.getValorBoolean();
		
		logger.info("envio de SMS habilitado=" + envioHabilitado);
		
		return envioHabilitado;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void atualizarErroEnvio(MensagemSMS mensagem) {
		mensagem.setStatus(StatusMensagem.PENDENTE);
		em.merge(mensagem);
		em.flush();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void atualizarProcessando(List<MensagemSMS> pendentes) {
		for (MensagemSMS mensagem : pendentes) {
			mensagem.setStatus(StatusMensagem.PROCESSANDO);
			em.merge(mensagem);
		}

		em.flush();
	}

	/**
	 * Atualiza a data de envio com sucesso em uma nova transação.
	 * 
	 * @author João Dimas
	 * @since 02/05/2012
	 * @param mensagem
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void atualizarSucessoEnvio(MensagemSMS mensagem) {
		mensagem.setDataEnvio(new Date());
		mensagem.setStatus(StatusMensagem.ENVIADO);
		em.merge(mensagem);
		em.flush();
	}

	/**
	 * Obtém as mensagens pendentes de envio.
	 * 
	 * @author João Dimas
	 * @since 02/05/2012
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<MensagemSMS> obterPendentes() {
		return em.createNamedQuery(MensagemSMS.OBTER_PENDENTES).getResultList();
	}
}
