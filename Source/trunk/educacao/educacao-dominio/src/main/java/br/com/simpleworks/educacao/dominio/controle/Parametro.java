package br.com.simpleworks.educacao.dominio.controle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import br.com.simpleworks.foundation.entity.BaseEntity;

@Data
@Entity
@Table(name = "PARAMETROS")
@NamedQueries({ @NamedQuery(name = Parametro.OBTER_TODOS, query = Parametro.OBTER_TODOS_QUERY) })
public class Parametro implements BaseEntity<String> {

	private static final long serialVersionUID = 1L;

	public static final String OBTER_TODOS = "Parametro.obterTodos";
	static final String OBTER_TODOS_QUERY = "SELECT P FROM Parametro P";
	
	public static final String ENVIAR_SMS_HABILITADO = "ENVIAR_SMS_HABILITADO";

	@Id
	@Column(name = "ID_PARAMETRO")
	private String id;

	@Column(name = "DESCRICAO")
	private String descricao;

	@Column(name = "VALOR")
	private String valor;

	@Column(name = "ATUALIZAR_ONLINE")
	private Integer atualizarOnline;
	
	/**
	 * Retorna o valor na forma booleana.
	 *
	 * @author João Dimas
	 * @since 03/05/2012
	 * @return
	 */
	public boolean getValorBoolean() {
		return valor.equals("1");
	}
	
	public void setAtualizarOnline(boolean atualizarOnline) {
		this.atualizarOnline = (atualizarOnline ? 1 : 0);
	}

	public boolean isAtualizarOnline() {
		return this.atualizarOnline.intValue() == 1;
	}
}
