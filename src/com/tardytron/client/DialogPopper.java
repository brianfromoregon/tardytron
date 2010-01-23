package com.tardytron.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DialogPopper {
	DialogBox dialogBox;
	Label messageLabel;

	public DialogPopper() {
		dialogBox = new DialogBox();
		dialogBox.setAnimationEnabled(true);
		Button closeButton = new Button("Close");
		messageLabel = new Label();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.add(messageLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
	}

	public void popHelp(String message) {
		dialogBox.setText("Tardytron helps");
		messageLabel.setText(message);
		dialogBox.center();
	}
	
	public void popError(Throwable error)
	{
		popError(error.getMessage());
	}
	
	public void popError(String message)
	{
		dialogBox.setText("Tardytron complains");
		messageLabel.setText(message);
		dialogBox.center();
	}
}
