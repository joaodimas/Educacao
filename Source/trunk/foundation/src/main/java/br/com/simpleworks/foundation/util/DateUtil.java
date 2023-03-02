package br.com.simpleworks.foundation.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil {

	public static final String FORMAT_SHORT = "dd/MM/yyyy";

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
	
	/**
	 * Formata uma Data.
	 *
	 * @author João Dimas
	 * @since 02/05/2012
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 * Formata uma data no formato dd/MM/yyyy.
	 *
	 * @author João Dimas
	 * @since 02/05/2012
	 * @param date
	 * @return
	 */
	public static String formatShort(Date date) {
		return new SimpleDateFormat(FORMAT_SHORT).format(date);
	}
}
