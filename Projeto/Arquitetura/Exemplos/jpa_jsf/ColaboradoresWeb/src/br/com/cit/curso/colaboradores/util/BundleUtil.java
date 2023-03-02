package br.com.cit.curso.colaboradores.util;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class BundleUtil {

	public static String getMessage(String bundleKey) {
		FacesContext context = FacesContext.getCurrentInstance();

		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, "msgs");

		if (bundle.containsKey(bundleKey)) {
			return bundle.getString(bundleKey);
		} else {
			return bundleKey;
		}
	}
}
