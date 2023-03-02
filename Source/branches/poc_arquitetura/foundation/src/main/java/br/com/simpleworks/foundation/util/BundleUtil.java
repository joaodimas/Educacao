package br.com.simpleworks.foundation.util;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class BundleUtil {
	private String bundleName;
	
	private BundleUtil(String bundleName) {
		this.bundleName = bundleName;
	}
	
	public String getMessage(String bundleKey) {
		FacesContext context = FacesContext.getCurrentInstance();

		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, bundleName);

		if (bundle.containsKey(bundleKey)) {
			return bundle.getString(bundleKey);
		} else {
			return bundleKey;
		}
	}
	
	public static BundleUtil getInstance() {
		return new BundleUtil("i18n");
	}
	
	public static BundleUtil getInstance(String bundleName) {
		return new BundleUtil(bundleName);
	}
}
