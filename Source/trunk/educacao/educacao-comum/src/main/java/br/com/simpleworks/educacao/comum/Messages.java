package br.com.simpleworks.educacao.comum;

import br.com.simpleworks.foundation.util.BundleUtil;


public class Messages {
	public interface Comum {
		public static final String FORMAT_DATA_SHORT = "format.data.short";	
	}
	
	public interface Chamada {
		public static final String CHAMADA_JA_CADASTRADA = "messages.chamada.exception.chamadaJaCadastradaParaEsteAluno"; 
	}
	
	public static String getMessage(String bundleKey) {
		return BundleUtil.getInstance().getMessage(bundleKey);
	}
}
