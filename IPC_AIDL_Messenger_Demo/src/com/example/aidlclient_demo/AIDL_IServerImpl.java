package com.example.aidlclient_demo;

import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.example.aidlclient_demo.aidl.IListener;
import com.example.aidlclient_demo.aidl.IServer;

/**
 * 服务端做的实现类
 * 
 * @author Magina
 *
 */
public class AIDL_IServerImpl extends IServer.Stub {

	private RemoteCallbackList<IListener> remoteCallbackList = new RemoteCallbackList<IListener>();
	private static final String PACKAGE_SAYHI = "你所指定的apk的包名";
	private String string;

	public AIDL_IServerImpl(String str) {
		// TODO Auto-generated constructor stub
		super();
		this.string = str;
	}

	@Override
	public String getString(String str) throws RemoteException {
		// TODO Auto-generated method stub
		// 通知所有注册了监听的客户端，服务端收到消息
		broadcastListener(str);
		return str == null ? string : str;
	}

	// 如果return false则代表的是调用失败。当想要做一些权限控制的时候，例如限制只有某个apk可以访问，可以重写此方法。
	@Override
	public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
			throws RemoteException {
		// TODO Auto-generated method stub
		// String packageName = null;
		// String[] packages = MyService.this.getPackageManager()
		// .getPackagesForUid(getCallingUid());
		// if (packages != null && packages.length > 0) {
		// packageName = packages[0];
		// }
		// if (!PACKAGE_SAYHI.equals(packageName)) {
		// return false;
		// }
		return super.onTransact(code, data, reply, flags);
	}

	@Override
	public void registListener(IListener listener) throws RemoteException {
		// TODO Auto-generated method stub
		remoteCallbackList.register(listener);
	}

	@Override
	public void unregistListener(IListener listener) throws RemoteException {
		// TODO Auto-generated method stub
		remoteCallbackList.unregister(listener);
	}

	// 通知所有的监听
	public void broadcastListener(String str) {
		// 注意：此处的beginBroadcast()和finishBroadcast()必须配合使用，少一不可
		final int n = remoteCallbackList.beginBroadcast();
		for (int i = 0; i < n; i++) {
			IListener listener = remoteCallbackList.getBroadcastItem(i);
			if (listener != null)
				try {
					listener.onResultListener("监听器通知：" + str);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		remoteCallbackList.finishBroadcast();
	}
}
