package com.example.aidlclient_demo.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class Messenger_ServerService extends Service {

	// 利用Messenger进行IPC通信，该方式是线程安全的。
	private Messenger messenger = new Messenger(new Handler() {
		public void handleMessage(android.os.Message msg) {
			try {
				msg.replyTo.send(Message.obtain());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	});

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return messenger.getBinder();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

}
