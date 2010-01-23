package com.tardytron.client;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.TextBox;

public abstract class HintingTextBox extends TextBox {
	public HintingTextBox() {
		addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				if (getText().equals(""))
					setText(initialText());
			}
		});
		addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				if (getText().equals(initialText()))
					setText("");
			}
		});
		setTitle(tooltip());
		reset();
	}
	
	public void reset()
	{
		setText(initialText());
	}

	protected abstract String initialText();

	protected abstract String tooltip();
}
