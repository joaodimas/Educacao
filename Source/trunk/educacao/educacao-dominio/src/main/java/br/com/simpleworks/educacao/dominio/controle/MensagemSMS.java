package br.com.simpleworks.educacao.dominio.controle;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import br.com.simpleworks.foundation.entity.BaseEntity;
import br.com.simpleworks.foundation.exception.ApplicationException;

@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "MENSAGEM_SMS")
@NamedQueries({
	@NamedQuery(name = MensagemSMS.OBTER_PENDENTES, query = MensagemSMS.OBTER_PENDENTES_QUERY)
})
public class MensagemSMS implements BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
	
	public static final String OBTER_PENDENTES = "MensagemSMS.obterPendentes";
	static final String OBTER_PENDENTES_QUERY = "SELECT M FROM MensagemSMS M " +
													" WHERE M.status = 0";
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_MENSAGEM")
	private Long id;
	
	@Column(name = "TEXTO")
	private String texto;
	
	@Column(name = "DDD_CELULAR_DESTINATARIO")
	private String dddCelularDestinatario;
	
	@Column(name = "NUMERO_CELULAR_DESTINATARIO")
	private String numeroCelularDestinatario;
	
	@Column(name = "DATA_ORIGINAL")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataOriginal;
	
	@Column(name = "DATA_ENVIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEnvio;
	
	@Column(name = "STATUS")
	@Enumerated(EnumType.ORDINAL)
	private StatusMensagem status;
	
	/**
	 * Cria uma nova Mensagem SMS pronta para envio.
	 *
	 * @author João Dimas
	 * @since 02/05/2012
	 * @param texto
	 * @param ddd
	 * @param numero
	 * @return
	 * @throws ApplicationException caso o DDD ou o Número sejam inválidos. 
	 */
	public static MensagemSMS criarNovaMensagem(String texto, String ddd, String numero) throws ApplicationException {
		MensagemSMS mensagem = new MensagemSMS();
		mensagem.setTexto(texto);
		mensagem.setDddCelularDestinatario(ddd);
		mensagem.setNumeroCelularDestinatario(numero);
		mensagem.setDataOriginal(new Date());
		mensagem.validarCelular();
		mensagem.setStatus(StatusMensagem.PENDENTE);
		
		return mensagem;
	}
	
	/**
	 * Valida o número de celular. 
	 *
	 * @author João Dimas
	 * @since 02/05/2012
	 * @throws ApplicationException caso o DDD ou o Número sejam inválidos.
	 */
	public void validarCelular() throws ApplicationException {
		if(!isCelularValido()) {
			throw new ApplicationException("Celular inválido. DDD=" + dddCelularDestinatario + ". Número=" + numeroCelularDestinatario);
		}
	}

	/**
	 * Verifica se o número de celular é válido.
	 *
	 * @author João Dimas
	 * @since 02/05/2012
	 * @param ddd
	 * @param numero
	 * @return
	 */
	public boolean isCelularValido() {
		return dddCelularDestinatario != null && dddCelularDestinatario.trim().length() == 2 && numeroCelularDestinatario != null && numeroCelularDestinatario.trim().length() == 8;
	}
	
	public enum StatusMensagem {
		PENDENTE, PROCESSANDO, ENVIADO;
	}
}
