package com.tardytron.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tardytron.client.dto.EventDto;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Tardytron implements EntryPoint {

    private final EventServiceAsync eventService = GWT.create(EventService.class);
    private final TestEmailServiceAsync testEmailService = GWT.create(TestEmailService.class);
    final IconImageBundle icons = GWT.create(IconImageBundle.class);
    private final Panel currentEventsPanel = new VerticalPanel();
    private final CurrentEventsTableLoader currentEventsTableLoader = new CurrentEventsTableLoader(
            eventService, icons);
    static DateTimeFormat DATE_FMT = DateTimeFormat.getFormat("MM/dd/yyyy");
    static DateTimeFormat LONG_DATE_FMT = DateTimeFormat.getLongDateFormat();

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        currentEventsPanel.addStyleName("hasBorder");
        final NewEventDatePanel newEventDatePanel = new NewEventDatePanel();
        final NewEventNamePanel newEventNamePanel = new NewEventNamePanel();
        final Button createButton = new Button("Create");
        final CheckBox twoWeekReminder = new CheckBox();
        final CheckBox twoDayReminder = new CheckBox();
        final Label twoWeekReminderText = new Label("2 week reminder?");
        final Label twoDayReminderText = new Label("2 day reminder?");
        final Label datePreview = new Label();
        datePreview.addStyleName("boldText");
        final Image datePreviewImage = new Image();


        final KeyUpHandler kuh = new KeyUpHandler() {

            @Override
            public void onKeyUp(KeyUpEvent e) {
                Date parsed = newEventDatePanel.getDate();
                if (parsed != null) {
                    datePreview.setText(LONG_DATE_FMT.format(parsed));
                    icons.greenFlag().applyTo(datePreviewImage);
                } else if (newEventDatePanel.getText().length() == 0) {
                    datePreview.setText("Type a date below");
                    icons.blueFlag().applyTo(datePreviewImage);
                } else {
                    datePreview.setText("Hmmm...?");
                    icons.redFlag().applyTo(datePreviewImage);
                }
            }
        };
        newEventDatePanel.addKeyUpHandler(kuh);
        kuh.onKeyUp(null);

        RootPanel.get("helpDiv").add(helpPanel());
        VerticalPanel newEventPanel = new VerticalPanel();
        HorizontalPanel row = new HorizontalPanel();
        row.add(datePreviewImage);
        row.add(hspacer(10));
        row.add(datePreview);
        newEventPanel.add(row);
        row = new HorizontalPanel();
        row.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        row.add(newEventDatePanel);
        row.add(hspacer(10));
        VerticalPanel options = new VerticalPanel();
        HorizontalPanel hp = new HorizontalPanel();
        hp.add(twoWeekReminder);
        hp.add(twoWeekReminderText);
        options.add(hp);
        hp = new HorizontalPanel();
        hp.add(twoDayReminder);
        hp.add(twoDayReminderText);
        options.add(hp);
        row.add(options);
        newEventPanel.add(row);
        VerticalPanel vp = new VerticalPanel();
        vp.setHeight("5px");
        vp.add(new Label(""));
        newEventPanel.add(vp);
        row = new HorizontalPanel();
        row.setVerticalAlignment(VerticalPanel.ALIGN_MIDDLE);
        row.add(newEventNamePanel);
        row.add(hspacer(20));
        row.add(createButton);
        newEventPanel.add(row);
        VerticalPanel sp = new VerticalPanel();
        sp.add(newEventPanel);
        sp.addStyleName("hasBorder");
        RootPanel.get("addNewEvent").add(sp);
        RootPanel.get("currentEvents").add(currentEventsPanel);
        // Create a handler for the sendButton and nameField
        class MyHandler implements ClickHandler {

            public void onClick(ClickEvent event) {
                addEvent();
            }

            private void addEvent() {
                Date date = newEventDatePanel.getDate();
                if (date == null) {
                    return;
                } else {
                    addEvent(date);
                }
            }

            private void addEvent(Date date) {
                createButton.setEnabled(false);
                int year = date.getYear() + 1900;
                int month = date.getMonth() + 1;
                int day = date.getDate();
                eventService.newEvent(new Date().getTimezoneOffset(), year, month, day, twoDayReminder.getValue(), twoWeekReminder.getValue(), newEventNamePanel.getName(),
                        new AsyncCallback<EventDto>() {

                            public void onFailure(Throwable caught) {
                                GWT.log("Adding event", caught);
                                createButton.setEnabled(true);
                            }

                            @Override
                            public void onSuccess(EventDto result) {
                                createButton.setEnabled(true);
                                newEventDatePanel.setText("");
                                newEventNamePanel.reset();
                                kuh.onKeyUp(null);
                                refreshCurrentEvents();
                            }
                        });
            }
        }

        // Add a handler to send the name to the server
        MyHandler handler = new MyHandler();
        createButton.addClickHandler(handler);

        refreshCurrentEvents();
    }

    private static HorizontalPanel hspacer(int px) {
        HorizontalPanel spacer = new HorizontalPanel();
        spacer.setWidth("" + px + "px");
        return spacer;
    }

    private DisclosurePanel helpPanel() {
        DisclosurePanel helpPanel = new DisclosurePanel("How does this work?");
        helpPanel.removeStyleName("gwt-DisclosurePanel");
        helpPanel.addStyleName("helpPanel");
        helpPanel.setAnimationEnabled(true);
        VerticalPanel vp = new VerticalPanel();
        HTML help = new HTML(
                "For each event you add, Tardytron will send you <b>reminder emails every year</b>. In addition to a midnight reminder on the anniversary date, you can opt to get reminders two days and/or two weeks in advance.");
        vp.add(help);
        Button emailButton = new Button("Send a test email");
        emailButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                testEmailService.sendTestEmail(new AsyncCallback<Void>() {

                    @Override
                    public void onSuccess(Void result) {
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                    }

                    ;
                });

            }
        });
        vp.add(emailButton);
        vp.addStyleName("hasBorder");
        helpPanel.setContent(vp);
        return helpPanel;
    }

    private void refreshCurrentEvents() {
        currentEventsPanel.clear();
        currentEventsPanel.add(new Label("Loading..."));
        currentEventsTableLoader.load(new AsyncCallback<Grid>() {

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("When loading events", caught);
            }

            @Override
            public void onSuccess(Grid result) {
                currentEventsPanel.clear();
                if (result.getRowCount() == 1) {
                    currentEventsPanel.add(new Label("No events yet."));
                } else {
                    result.addStyleName("currentEventsTable");
                    currentEventsPanel.add(result);
                }
            }
        }, new AsyncCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                refreshCurrentEvents();

            }

            @Override
            public void onFailure(Throwable caught) {
                GWT.log("When deleting event", caught);
            }
        });
    }
}
