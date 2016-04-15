package com.example.aidlclient_demo;

import android.os.Parcel;
import android.os.RemoteException;

import com.example.aidlclient_demo.aidl.IServer;

/**
 * 服务端实现
 * 
 * @author Magina
 *
 */
public class AIDL_IServerImpl extends IServer.Stub {

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
		return str == null ? string : str;
	}

	// 如果return false则代表的是调用失败。当想要做一些权限控制的时候，例如限制只有某个apk可以访问，则重写此方法。
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
}
