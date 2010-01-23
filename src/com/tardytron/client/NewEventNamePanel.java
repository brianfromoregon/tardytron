package com.tardytron.client;

public class NewEventNamePanel extends HintingTextBox {
	public NewEventNamePanel() {
		addStyleDependentName("name");
	}

	public String getName() {
		return getText();
	}

	@Override
	protected String initialText() {
		return "Grandma Slappy's birthday";
	}

	@Override
	protected String tooltip() {
		return "The name you enter here will be included in the reminder emails.";
	}
}
