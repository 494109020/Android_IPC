/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/Magina/Documents/workspace/IPC_AIDL_Messenger_Demo/src/com/example/aidlclient_demo/aidl/IServer.aidl
 */
package com.example.aidlclient_demo.aidl;
public interface IServer extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.example.aidlclient_demo.aidl.IServer
{
private static final java.lang.String DESCRIPTOR = "com.example.aidlclient_demo.aidl.IServer";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.example.aidlclient_demo.aidl.IServer interface,
 * generating a proxy if needed.
 */
public static com.example.aidlclient_demo.aidl.IServer asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.example.aidlclient_demo.aidl.IServer))) {
return ((com.example.aidlclient_demo.aidl.IServer)iin);
}
return new com.example.aidlclient_demo.aidl.IServer.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getString:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.getString(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.example.aidlclient_demo.aidl.IServer
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public java.lang.String getString(java.lang.String str) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(str);
mRemote.transact(Stub.TRANSACTION_getString, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getString = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public java.lang.String getString(java.lang.String str) throws android.os.RemoteException;
}
