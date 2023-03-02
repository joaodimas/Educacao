package br.com.simpleworks.educacao.dominio.turma;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import br.com.simpleworks.foundation.entity.BaseEntity;

@Data
@ToString(of = {"id", "nomeSerie"})
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "SERIE")
@NamedQueries({
	@NamedQuery(name = Serie.OBTER_TODOS, query = Serie.OBTER_TODOS_QUERY)
})
public class Serie implements BaseEntity<Integer> {

	public static final String OBTER_TODOS = "Serie.obterTodos";
	static final String OBTER_TODOS_QUERY = "SELECT S FROM Serie S";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ID_SERIE = "idSerie";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_SERIE")
	private Integer id;
	
	@Column(name = "NOME_SERIE")
	private String nomeSerie;
	
	@ManyToOne
	@JoinColumn(name = "ID_ENSINO")
	private Ensino ensino;

}
