package br.com.simpleworks.educacao.web;

import java.io.Serializable;

import lombok.Data;

@Data
public class MessagesContext implements
Serializable {
	private static final long serialVersionUID = 1L;
	
	private String currentMessage;
	private String onCloseRedirect;
	private MessageType messageType;
	
	
	public boolean hasMessage() {
		return currentMessage != null;
	}
	
	public enum MessageType {
		INFO, ALERT, CONFIRMATION;
	}

	public void info(String message, String onCloseRedirect) {
		this.currentMessage = message;
		this.onCloseRedirect = onCloseRedirect;
		this.messageType = MessageType.INFO;
	}
	
	public void alert(String message, String onCloseRedirect) {
		this.currentMessage = message;
		this.onCloseRedirect = onCloseRedirect;
		this.messageType = MessageType.ALERT;
	}
	
	public void alert(String message) {
		this.currentMessage = message;
		this.onCloseRedirect = null;
		this.messageType = MessageType.ALERT;
	}
	
	public void clean() {
		this.currentMessage = null;
		this.onCloseRedirect = null;
		this.messageType = null;
	}
}
