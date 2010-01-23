package com.tardytron.client;

import com.google.gwt.user.client.ui.TextBox;
import java.util.Date;

import com.tardytron.client.datejs.DateJs;

public class NewEventDatePanel extends TextBox {

    public NewEventDatePanel() {
        addStyleDependentName("date");
    }

    public Date getDate() {
        try {
            return DateJs.parse(getValue()).toDate();
        } catch (Exception e) {
            return null;
        }
    }
}
