package mobile.keren.coronaclient;

import android.os.Bundle;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import plg.corona.ICallback;
import plg.corona.IService;
import plg.corona.Status;

/**
 * Class holds CoronaServer's main activity.
 */
public class MainActivity extends AppCompatActivity {

    // region Constants.

    private static final String TAG = "MainActivity";

    // endregion

    // region Members

    private IService _service;

    /**
     * Registration server.
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            _service = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            _service = IService.Stub.asInterface(service);

            try {
                _service.registerCallback(mCallback);
            }
            catch (RemoteException e) {
                Log.e(TAG, "", e);
            }
        }
    };

    // endregion

    // region LifeCycle Methods

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind service.
        Intent intent = new Intent("CoronaService");
        intent.setPackage("plg.corona");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        ((Button) findViewById(R.id.update_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    _service.sendUpdate(1, 1, 0);
                }
                catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        if (_service != null){
            try {
                _service.unregisterCallback(mCallback);
            }
            catch (RemoteException e) {
                Log.e(TAG, "", e);
            }
        }
        //destroy don't forget unbindService
        unbindService(mConnection);
        super.onDestroy();
    }

    // endregion

    // region ICallback

    /**
     * service callback method
     */
    private ICallback.Stub mCallback = new ICallback.Stub() {
        /**
         * Method handles constant status received.
         * @param status holding info about sick, healed and deceased people in israel.
         * @throws RemoteException for IPC problems.
         */
        @Override
        public void onStatusReceived(Status status) throws RemoteException {
            System.out.println(status.toString());
        }

        /**
         * Method handles updates sent from server.
         * @param update received from server.
         * @throws RemoteException for IPC problems.
         */
        @Override
        public void onMessageReceived(String update) throws RemoteException {
            System.out.println(update);
        }

        /**
         * Method handles clear request from server.
         * @throws RemoteException for IPC problems.
         */
        @Override
        public void onClearReceived() throws RemoteException {
            System.out.println("clear");
            // TODO handle clears.
        }
    };

    // endregion

}
