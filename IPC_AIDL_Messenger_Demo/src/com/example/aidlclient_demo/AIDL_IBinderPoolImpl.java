package com.example.aidlclient_demo;

import android.os.IBinder;
import android.os.RemoteException;

import com.example.aidlclient_demo.aidl.IBinderPool;

/**
 * 服务端做的实现类
 * @author Magina
 */
public class AIDL_IBinderPoolImpl extends IBinderPool.Stub {

	public AIDL_IBinderPoolImpl() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public IBinder queryBinder(int binderType) throws RemoteException {
		// TODO Auto-generated method stub
		IBinder iBinder = null;
		// 根据实际业务需要判断返回哪种类型的binder
		if (binderType == 0) {
			iBinder = new AIDL_IServerImpl("Binder连接池中的初始化");
		} else {
			iBinder = new AIDL_IServerImpl("Binder连接池中的初始化");
		}
		return iBinder;
	}

}
