package test.rmbk.com.testtask;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    public static final String CALCULATE_SUM = "test.rmbk.com.testtask.CALCULATE_SUM";
    private static boolean isRunning = false;
    private final String TAG = "MyService";

    /**
     * CHANGE
     */

    /**
     * IM MASTER-KOL-REDO
     */

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Player OnCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        if (intent == null) {
            return START_STICKY;
        }

        String action = intent.getAction();
        if (action != null) {
            if (action.equals(CALCULATE_SUM)) {
                if (!isRunning) {
                    int i,j,delay;
                    i = intent.getExtras().getInt("first",0);
                    j = intent.getExtras().getInt("second",0);
                    delay = intent.getExtras().getInt("delay",0);
                    new calculateTask().execute(i, j, delay);
                }
            }
        }

        return START_STICKY;
    }

    class calculateTask extends AsyncTask <Integer, Void, Void> {

        int sum_result;

        @Override
        protected Void doInBackground(Integer... params) {
            try {
                TimeUnit.SECONDS.sleep(params[2]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sum_result = params[0] + params[1];
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            Intent intent = new Intent(MainActivity.RECEIVE_RESULT);
            intent.putExtra("result", sum_result);
            LocalBroadcastManager.getInstance(MyService.this).sendBroadcast(intent);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
