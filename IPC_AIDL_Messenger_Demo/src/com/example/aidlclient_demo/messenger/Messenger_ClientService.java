package com.example.aidlclient_demo.messenger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class Messenger_ClientService extends Activity {

	private boolean isConnection;// 是否已连接
	private Messenger mMessengerService;
	// 利用Messenger进行IPC通信
	private Messenger messenger = new Messenger(new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 处理服务端返回的消息
		};
	});

	private ServiceConnectionImpl myConn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Intent intent = new Intent("android.ipc.demo.messenger");
		myConn = new ServiceConnectionImpl();
		bindService(intent, myConn, BIND_AUTO_CREATE);
	}

	/**
	 * 这种是利用Messenger进行IPC通信
	 */
	public void sendMsg() {
		try {
			Message msg = Message.obtain();
			msg.obj = "test";
			// 接收服务端返回的消息,同时服务端就是利用该Messenger对象向客户端发送消息
			msg.replyTo = messenger;
			// 利用连接服务端后返回的IBinder对象所构造出来的Messenger向服务端发送消息
			mMessengerService.send(msg);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (isConnection && myConn != null) {
			unbindService(myConn);
			isConnection = false;
		}
	}

	class ServiceConnectionImpl implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder binder) {
			// TODO Auto-generated method stub
			isConnection = true;
			// 这种是利用Messenger进行IPC通信的时候的写法
			mMessengerService = new Messenger(binder);
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			isConnection = false;
		}

	}
}
