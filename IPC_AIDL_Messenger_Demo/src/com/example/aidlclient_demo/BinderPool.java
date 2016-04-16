package com.example.aidlclient_demo;

import java.util.concurrent.CountDownLatch;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;

import com.example.aidlclient_demo.aidl.IBinderPool;

/**
 * 封装binder连接池
 * 
 * @author Magina
 *
 */
public class BinderPool {

	private Context mContext;
	private IBinderPool mBinderPoolImpl;
	private static volatile BinderPool binderPool;

	// CountDownLatch，一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
	// 主要方法
	// public CountDownLatch(int count);
	// public void countDown();
	// public void await() throws InterruptedException
	// 构造方法参数指定了计数的次数
	// countDown方法，当前线程调用此方法，则计数减一
	// awaint方法，调用此方法会一直阻塞当前线程，直到计时器的值为0
	private CountDownLatch mCountDownLatch;

	private BinderPool(Context context) {
		mContext = context.getApplicationContext();
		bindService();
	}

	public static BinderPool getInstance(Context context) {
		if (binderPool == null) {
			synchronized (BinderPool.class) {
				if (binderPool == null) {
					binderPool = new BinderPool(context);
				}
			}
		}
		return binderPool;
	}

	private DeathRecipient deathRecipient = new DeathRecipient() {

		@Override
		public void binderDied() {
			// TODO Auto-generated method stub
			if (mBinderPoolImpl == null)
				return;
			mBinderPoolImpl.asBinder().unlinkToDeath(deathRecipient, 0);
			mBinderPoolImpl = null;
			bindService();
		}
	};

	private synchronized void bindService() {
		mCountDownLatch = new CountDownLatch(1);
		Intent intent = new Intent(mContext, AIDL_ServerService.class);
		mContext.bindService(intent, new ClientServiceConnection(),
				Context.BIND_AUTO_CREATE);
		try {
			mCountDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IBinder queryBinder(int binderType) {
		IBinder binder = null;
		if (mBinderPoolImpl != null) {
			try {
				binder = mBinderPoolImpl.queryBinder(binderType);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return binder;
	}

	class ClientServiceConnection implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder binder) {
			// TODO Auto-generated method stub
			mBinderPoolImpl = IBinderPool.Stub.asInterface(binder);
			try {
				mBinderPoolImpl.asBinder().linkToDeath(deathRecipient, 0);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mCountDownLatch.countDown();
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub

		}

	}
}
