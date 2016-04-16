package com.example.aidlclient_demo.aidl;

interface IBinderPool{
	 IBinder queryBinder(int binderType);
}