package br.com.simpleworks.educacao.web;

import java.util.Iterator;

import javax.ejb.EJBException;
import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import br.com.simpleworks.educacao.comum.Messages;

public class DefaultExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	public DefaultExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		MessagesContext messagesContext = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{messagesContext}", MessagesContext.class);

		Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();

		if (iterator.hasNext()) {
			ExceptionQueuedEvent event = iterator.next();
			ExceptionQueuedEventContext eventContext = (ExceptionQueuedEventContext) event.getSource();
			Throwable exception = eventContext.getException();

			String message = null;
			if (isEJBException(exception)) {
				message = castEJBException(exception).getCause().getMessage();
				messagesContext.alert(Messages.getMessage(message));

				iterator.remove();
			} else {
				wrapped.handle();
			}
		} else {
			messagesContext.clean();
		}
	}

	private boolean isEJBException(Throwable exception) {
		return exception.getCause() != null && exception.getCause().getCause() != null && exception.getCause().getCause().getCause() instanceof EJBException;
	}

	private EJBException castEJBException(Throwable exception) {
		return (EJBException) exception.getCause().getCause().getCause();
	}
}
