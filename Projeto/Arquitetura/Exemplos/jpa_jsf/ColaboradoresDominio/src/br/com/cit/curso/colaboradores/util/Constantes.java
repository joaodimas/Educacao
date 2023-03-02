package br.com.cit.curso.colaboradores.util;

/**
 * Armazena as constantes da aplicação.
 * 
 * @author jdimas
 *
 */
public interface Constantes {
	
	/**
	 * Armazena as keys do Bundle da aplicação.
	 * @author jdimas
	 *
	 */
	public interface BundleKeys {
		
		/**
		 * Keys comuns a toda a aplicação.
		 * @author jdimas
		 *
		 */
		public interface Comum {
			public static final String CPF_INVALIDO = "comum.cpfinvalido";
			public static final String FORMATO_DATA = "comum.formato_data";
		}
		
		/**
		 * Keys do Cadastro de Colaboradores.
		 * @author jdimas
		 *
		 */
		public interface CadastroColaboradores {
			public static final String COLABORADOR_CADASTRADO_COM_SUCESSO = "cadastrocolaboradores.colaboradorcadastradocomsucesso";
			public static final String COLABORADOR_JA_EXISTENTE_CPF = "cadastrocolaboradores.colaboradorjaexistentecpf";
			public static final String CAMPO_BASE_OBRIGATORIO = "cadastrocolaboradores.baseobrigatorio";
			public static final String CAMPO_NOME_OBRIGATORIO = "cadastrocolaboradores.nomeobrigatorio";
			public static final String CAMPO_DATANASCIMENTO_OBRIGATORIO = "cadastrocolaboradores.datanascimentoobrigatorio";
			public static final String CAMPO_CPF_OBRIGATORIO = "cadastrocolaboradores.cpfobrigatorio";
			public static final String DATANASCIMENTO_INVALIDA = "cadastrocolaboradores.datanascimentoinvalida";
			public static final String COLABORADOR_NAO_ENCONTRADO = "cadastrocolaboradores.colaboradornaoencontrado";
		}
	}
}
