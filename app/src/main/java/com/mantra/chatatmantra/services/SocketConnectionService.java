package com.mantra.chatatmantra.services;

/**
 * Created by tanma on 5/28/2016.
 */
public class SocketConnectionService
//        extends IntentService
{
//
//
//    private volatile static Socket socket;
//    private static String driverId;
//    private static String cabId;
//
//    public SocketConnectionService() {
//        super("SocketConnectionService");
//        Log.d("SocketService","constructor");
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        super.onBind(intent);
//        try
//        {
//            driverId = SharedPrefs.getInstance(getApplicationContext()).getDriverId();
////            driverId = "573302a668307e94008a665b";
//            cabId = SharedPrefs.getInstance(getApplicationContext()).getCabDetails().getId();
//            IO.Options options = new IO.Options();
//            options.secure = false;
//            options.forceNew = false;
//            options.reconnection = true;
//            socket = IO.socket(RestClient.getServerBaseUrl()+"?__sails_io_sdk_version=0.13.5&driver_id="+driverId,options);
//
//            IO.setDefaultHostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//
//                    return true;
//                }
//            });
//        }
//        catch (Exception e)
//        {
//            Log.e("Socket Error",e.getLocalizedMessage());
//            e.printStackTrace();
//            stopSelf();
//        }
//        return new LocalBinder();
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent)
//    {
//        String action = intent.getAction();
//
//        executeAction(action,getApplicationContext());
//    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.e("SocketService","destroyed");
//    }
//
//    public synchronized void executeAction(String data, Context context) {
//        try
//        {
//
//            JSONObject jsonObject = new JSONObject(data);
//            String action = jsonObject.getString("action");
//            switch (action)
//            {
//                case SyncStateContract.Constants.SOCKET_CONNECT: socketIO(new SocketData(driverId,cabId, SyncStateContract.Constants.SOCKET_CONNECT));
//                    break;
//                case SyncStateContract.Constants.BOOKING_ACTION: sendIncomingBookingResponse(jsonObject.getJSONObject("data"));
//                    break;
//                case SyncStateContract.Constants.SOCKET_DISCONNECT: socket.disconnect();
//                    DashboardActivity.eventBus.post(new DisconnectSocket());
//                    break;
//            }
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            Log.e("SocketService Error",e.getLocalizedMessage());
//        }
//
//    }
//
//    private void sendIncomingBookingResponse(JSONObject data) throws Exception{
//        SocketData socketData = new SocketData(driverId,cabId, SyncStateContract.Constants.BOOKING_ACTION,data);
//        socketIO(socketData);
//    }
//
//
//    public synchronized void socketIO(final SocketData socketData) throws Exception {
//
//
//        final JSONObject jsonObject = new JSONObject();
//        jsonObject.put("url", "/socket?driver_id="+driverId);
//
//        jsonObject.put("action", socketData.getAction());
//        jsonObject.put("data", new Gson().toJson(socketData));
//        Log.d("socketdata", jsonObject.toString());
//        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//
//            @Override
//            public void call(Object... args) {
//                Log.d("Socket", "Connected");
//
//
//            }
//
//        }).on("connection", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                Log.d("Socket", "Connection");
//                socket.emit("post", jsonObject);
//            }
//        }).on("data", new Emitter.Listener() {
//
//            @Override
//            public void call(Object... args) {
//                Log.d("Got data", String.valueOf(args[0]));
//                try {
//                    SocketData socketData;
//                    socketData = new Gson().fromJson(String.valueOf(args[0]), SocketData.class);
//                    if(socketData.getAction().equalsIgnoreCase(Constants.RIDE_COMPLETE))
//                    {
//                        Type billType = new TypeToken<SocketData<RideBill>>(){}.getType();
//                        socketData = new Gson().fromJson(String.valueOf(args[0]), billType);
//                    }
//                    Intent intent = new Intent(SocketConnectionService.this, DashboardActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    startActivity(intent);
//                    DashboardActivity.eventBus.post(socketData);
//
//
//
//                } catch (Exception e) {
//                    Log.e("SocketError",e.getLocalizedMessage());
//                }
//
//
//            }
//
//        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
//
//            @Override
//            public void call(Object... args) {
//                Log.d("Disconnected", String.valueOf(args.length));
//            }
//
//        }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                Log.e("Error", "args" + String.valueOf(args));
//            }
//        }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                Log.e("Error", "Timeout" + args.length);
//            }
//        }).on(Socket.EVENT_CONNECTING, new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                Log.d("Connecting", "args" + args.length);
//            }
//        });
//
//        if (!socket.connected())
//        {
//            socket.connect();
//            socket.connect();
//            socket.emit("post", jsonObject);
//        }
//        else
//            socket.emit("post", jsonObject);
//
//    }
////public static void socketIO(final SocketData socketData) throws Exception {
////    if (socket == null)
////    {
////        IO.Options options = new IO.Options();
////        options.forceNew = false;
////        options.reconnection = true;
////        socket = IO.socket("http://192.168.0.144:1337?__sails_io_sdk_version=0.13.5",options);
////    }
////
////    final JSONObject jsonObject = new JSONObject();
////
////    jsonObject.put("url", "/socket");
////
////    jsonObject.put("action", socketData.getAction());
////    jsonObject.put("data", new Gson().toJson(socketData));
////    Log.d("socketdata", jsonObject.toString());
////if(DashboardActivity.socketListener==null)
////    DashboardActivity.socketListener = SocketListener.getInstance();
////    socket.on(Socket.EVENT_CONNECT,DashboardActivity.socketListener).on("connection", DashboardActivity.socketListener)
////            .on("data", DashboardActivity.socketListener).on(Socket.EVENT_DISCONNECT, DashboardActivity.socketListener)
////            .on(Socket.EVENT_CONNECT_ERROR, DashboardActivity.socketListener)
////            .on(Socket.EVENT_CONNECT_TIMEOUT, DashboardActivity.socketListener).on(Socket.EVENT_CONNECTING, DashboardActivity.socketListener);
////
//////    if (socket.connected())
////        socket.emit("post", jsonObject);
//////    else
////        socket.connect();
////}
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        super.onStartCommand(intent, flags, startId);
//        Log.i("SocketConnectionService","Starting");
//        return START_STICKY;
//    }
//
//    public class LocalBinder extends Binder
//    {
//        public SocketConnectionService getService()
//        {
//            return SocketConnectionService.this;
//        }
//    }
}