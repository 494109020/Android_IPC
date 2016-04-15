package com.example.aidlclient_demo;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class AIDL_ServerService extends Service {
	// 使用AIDL进行IPC通信的注意事项：
	// 1. 调用者是不能保证在主线程执行的，所以从一调用的开始就需要考虑多线程处理，以及确保线程安全；
	// 2.IPC调用是同步的。如果你知道一个IPC服务需要超过几毫秒的时间才能完成地话，你应该避免在Activity的主线程中调用。也就是IPC调用会挂起应用程序导致界面失去响应，这种情况应该考虑单独开启一个线程来处理。
	// 3.抛出的异常是不能返回给调用者（跨进程抛异常处理是不可取的）
	// 4.aidl文件中定义接口的参数
	// 视情况需要有方向指示，包括in、out和inout，in表示由客户端设置，out表示由服务端设置，inout是两者均可设置。
	private AIDL_IServerImpl impl;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return impl.asBinder();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		impl = new AIDL_IServerImpl("默认");
	}

}
