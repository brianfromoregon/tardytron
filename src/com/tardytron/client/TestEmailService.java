package com.tardytron.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("testemail")
public interface TestEmailService extends RemoteService{
	void sendTestEmail();
}
