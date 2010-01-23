package com.tardytron.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NewEventResult implements IsSerializable {
	private String failureMessage;

	public NewEventResult(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public NewEventResult() {
		this(null);
	}

	public String getFailureMessage() {
		return failureMessage;
	}
}
