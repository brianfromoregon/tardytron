package com.tardytron.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TestEmailServiceAsync {

	void sendTestEmail(AsyncCallback<Void> callback);

}
