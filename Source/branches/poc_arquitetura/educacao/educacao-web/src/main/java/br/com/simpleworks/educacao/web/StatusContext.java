package br.com.simpleworks.educacao.web;

import javax.enterprise.inject.Model;

@Model
public class StatusContext {

	private String currentMessage;

	public String getCurrentMessage() {
		return currentMessage;
	}

	public void setCurrentMessage(String currentMessage) {
		this.currentMessage = currentMessage;
	}

}
