package com.example.aidlclient_demo;

import com.example.aidlclient_demo.aidl.IServer;

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
import android.widget.Toast;

public class AIDL_ClientService extends Activity {

	private AIDL_IServerImpl mAIDLService;

	private boolean isConnection;// 是否已连接

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
		Intent intent = new Intent("android.ipc.demo.aidl");
		myConn = new ServiceConnectionImpl();
		bindService(intent, myConn, BIND_AUTO_CREATE);
	}

	// 与服务端通信
	public void communication() {
		try {
			String string = mAIDLService.getString("test");
			Toast.makeText(this, string, Toast.LENGTH_LONG).show();
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
			// 这种是利用AIDL进行IPC通信
			mAIDLService = (AIDL_IServerImpl) IServer.Stub.asInterface(binder);
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			isConnection = false;
		}

	}
}
