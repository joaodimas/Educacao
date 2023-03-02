package br.com.simpleworks.foundation.controller;

import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import br.com.simpleworks.foundation.util.BundleUtil;

public class Controller {
	public Locale getLocale() {
		FacesContext context = FacesContext.getCurrentInstance();
		Locale locale = context.getViewRoot().getLocale();

		return locale;
	}

	protected Flash flashScope() {
		return FacesContext.getCurrentInstance().getExternalContext().getFlash();
	}
	
	protected String getMessage(String bundleKey) {
		return BundleUtil.getInstance().getMessage(bundleKey);
	}
	
	protected boolean isViewPostback() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.isPostback() && (context.getExternalContext().getRequestServletPath().equals(context.getViewRoot().getViewId().replace("xhtml", "jsf")));
	}
	
	protected boolean isJsfPostback() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.isPostback();
	}
	
	protected String redirect(String view) {
		return view + "faces-redirect=true";
	}
}
