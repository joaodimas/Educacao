package br.com.simpleworks.educacao.web.base;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;

import lombok.Getter;
import br.com.simpleworks.educacao.web.MessagesContext;
import br.com.simpleworks.foundation.util.BundleUtil;

public class BaseController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Getter
	private MessagesContext messagesContext;
	
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
	
	protected void showSuccessAndRedirect(String bundleKey, String onCloseRedirect) {
		String message = getMessage(bundleKey);
		messagesContext.info(message, onCloseRedirect);
		flashScope().put("messagesContext", messagesContext);
	}
}
