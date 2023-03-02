package br.com.simpleworks.foundation.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil {

	/**
	 * Verifica se a data1 é maior ou igual à data2 ignorando o horário.
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 * @author João Dimas
	 * @since 16/03/2012
	 */
	public static boolean dataMaiorOuIgual(Date data1, Date data2) {
	
		data1 = DateUtils.truncate(data1, Calendar.DATE);
		data2 = DateUtils.truncate(data2, Calendar.DATE);
		
		return !data1.before(data2);
	}
	
	/**
	 * Verifica se a data1 é igual à data2 ignorando o horário.
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	public static boolean dataIgual(Date data1, Date data2) {
		
		data1 = DateUtils.truncate(data1, Calendar.DATE);
		data2 = DateUtils.truncate(data2, Calendar.DATE);
		
		return data1.equals(data2);
	}
	

	/**
	 * Verifica se a data1 é maior à data2 ignorando o horário.
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 * @author João Dimas
	 * @since 16/03/2012
	 */
	public static boolean dataMaior(Date data1, Date data2) {
	
		data1 = DateUtils.truncate(data1, Calendar.DATE);
		data2 = DateUtils.truncate(data2, Calendar.DATE);
		
		return data1.after(data2);
	}
	
	/**
	 * Obtém a data atual sem horário.
	 * @return
	 */
	public static Date dataAtual() {
		return DateUtils.truncate(new Date(), Calendar.DATE);
	}
}
