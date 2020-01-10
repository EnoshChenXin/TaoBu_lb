package com.lianbei.taobu.listener;


public class WXLoginResuleInterface {
	public LoginRequestCompletions mOverEventResult;
	public static WXLoginResuleInterface wxResuleInterface= null;
	
	
	public static WXLoginResuleInterface getInstance() {
		if (wxResuleInterface == null) {
			synchronized (WXLoginResuleInterface.class) {
				if (wxResuleInterface == null) {
					wxResuleInterface = new WXLoginResuleInterface ();
				}
			}
		}
		return wxResuleInterface;
	}

	public void wxExecuteMessage(LoginRequestCompletions mOverEventResult) {
		this.mOverEventResult = mOverEventResult;
	}

	public void setLoginSuc(Object object) {
		mOverEventResult.WXLoginSuccess (object);
	}
	public void setLoginFail(Object object) {
		mOverEventResult.WXLoginFail(object);
	}
}
