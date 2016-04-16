package com.example.aidlclient_demo.aidl;

import com.example.aidlclient_demo.aidl.IListener;

interface IServer{
	 String getString(String str);
	 void registListener(IListener listener);
	 void unregistListener(IListener listener);
}