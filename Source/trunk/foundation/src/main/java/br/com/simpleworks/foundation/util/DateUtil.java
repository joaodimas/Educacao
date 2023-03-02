package br.com.simpleworks.foundation.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil {

	public static final String FORMAT_SHORT = "dd/MM/yyyy";

	/**
	 * Verifica se a data1 � maior ou igual � data2 ignorando o hor�rio.
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 * @author Jo�o Dimas
	 * @since 16/03/2012
	 */
	public static boolean dataMaiorOuIgual(Date data1, Date data2) {
	
		data1 = DateUtils.truncate(data1, Calendar.DATE);
		data2 = DateUtils.truncate(data2, Calendar.DATE);
		
		return !data1.before(data2);
	}
	
	/**
	 * Verifica se a data1 � igual � data2 ignorando o hor�rio.
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
	 * Verifica se a data1 � maior � data2 ignorando o hor�rio.
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 * @author Jo�o Dimas
	 * @since 16/03/2012
	 */
	public static boolean dataMaior(Date data1, Date data2) {
	
		data1 = DateUtils.truncate(data1, Calendar.DATE);
		data2 = DateUtils.truncate(data2, Calendar.DATE);
		
		return data1.after(data2);
	}
	
	/**
	 * Obt�m a data atual sem hor�rio.
	 * @return
	 */
	public static Date dataAtual() {
		return DateUtils.truncate(new Date(), Calendar.DATE);
	}
	
	/**
	 * Formata uma Data.
	 *
	 * @author Jo�o Dimas
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
	 * @author Jo�o Dimas
	 * @since 02/05/2012
	 * @param date
	 * @return
	 */
	public static String formatShort(Date date) {
		return new SimpleDateFormat(FORMAT_SHORT).format(date);
	}
}
