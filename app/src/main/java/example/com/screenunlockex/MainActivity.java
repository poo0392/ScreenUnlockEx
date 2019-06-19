package example.com.screenunlockex;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private LockScreenStateReceiver mLockScreenStateReceiver;
    int count = 0;
    private TextView textView;
    LockScreenStateReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView=(TextView)findViewById(R.id.textView);
       /* Intent intent = new Intent(this, LockScreenStateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), 60000, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                +60000, pendingIntent);
        //sendBroadcast(intent);*/
        receiver=new LockScreenStateReceiver();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiver.enableReceiver(MainActivity.this);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public class LockScreenStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                // screen is turn off
                Log.v("11: ","Screen locked");
                Toast.makeText(MainActivity.this,"Screen locked",Toast.LENGTH_SHORT).show();
            } else {
                //Handle resuming events if user is present/screen is unlocked
                count++;
                textView.setText(""+count);
                Log.v("22: ","Screen unlocked");
                Toast.makeText(MainActivity.this,"Screen unlocked",Toast.LENGTH_SHORT).show();
            }
        }
        /**
         * Enables ConnectivityReceiver
         *
         * @param context
         */
        public void enableReceiver(Context context)
        {

            //Log.v(TAG, "In enableReceiver");
            ComponentName component = new ComponentName(context, LockScreenStateReceiver.class);

            context.getPackageManager().setComponentEnabledSetting(component,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        }
    }
}
