package com.lianbei.taobu.listener;


public class WXShareResuleInterface {
	public ShareRequestCompletion mOverEventResult;
	public static WXShareResuleInterface wxResuleInterface= null;
	
	
	public static WXShareResuleInterface getInstance() {
		if (wxResuleInterface == null) {
			synchronized (WXShareResuleInterface.class) {
				if (wxResuleInterface == null) {
					wxResuleInterface = new WXShareResuleInterface ();
				}
			}
		}
		return wxResuleInterface;
	}

	public void wxExecuteMessage(ShareRequestCompletion mOverEventResult) {
		this.mOverEventResult = mOverEventResult;
	}

	public void setShareSuc(Object object) {
		mOverEventResult.WXShareSuccess (object);
	}
}
