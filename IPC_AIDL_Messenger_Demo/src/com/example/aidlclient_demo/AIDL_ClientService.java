package com.example.aidlclient_demo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;
import android.widget.Toast;

import com.example.aidlclient_demo.aidl.IBinderPool;
import com.example.aidlclient_demo.aidl.IListener;
import com.example.aidlclient_demo.aidl.IServer;

public class AIDL_ClientService extends Activity {

	private static final int BINDER_ISERVER = 0;

	private AIDL_IBinderPoolImpl mAIDLBindPoolService;
	private IServer mAIDLService;

	private boolean isConnection;// 是否已连接

	private ServiceConnectionImpl myConn;
	// 设置binder的死亡代理
	private DeathRecipient deathRecipient = new DeathRecipient() {

		@Override
		public void binderDied() {
			// TODO Auto-generated method stub
			if (mAIDLBindPoolService == null)
				return;
			mAIDLBindPoolService.asBinder().unlinkToDeath(deathRecipient, 0);
			mAIDLBindPoolService = null;
			bindService();
		}
	};

	// 监听器，监听服务端的某个事件
	private IListener listener = new IListener.Stub() {

		@Override
		public void onResultListener(String str) throws RemoteException {
			// TODO 这里处理服务器发送过来的通知，此处是运行在binder线程池中，看情况可以转到主线程中执行后续操作

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		bindService();
	}

	// 绑定服务
	private void bindService() {
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
			try {
				// 销毁时需要进行解注册
				mAIDLService.unregistListener(listener);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			// 首先得到binder连接池
			mAIDLBindPoolService = (AIDL_IBinderPoolImpl) IBinderPool.Stub
					.asInterface(binder);
			// 设置死亡代理，binder在死亡的时候客户端可以收到通知，并进行重新绑定
			try {
				mAIDLBindPoolService.asBinder().linkToDeath(deathRecipient, 0);
				// 得到所需要的aidl接口实现，并注册监听
				mAIDLService = IServer.Stub.asInterface(mAIDLBindPoolService
						.queryBinder(BINDER_ISERVER));
				mAIDLService.registListener(listener);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			isConnection = false;
		}
	}

}
