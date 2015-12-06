package test.rmbk.com.testtask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public static final String RECEIVE_RESULT = "test.rmbk.com.testtask.RECEIVE_RESULT";

    private BroadcastReceiver bReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(RECEIVE_RESULT)) {
                int result = intent.getExtras().getInt("result");
                resultTextView.setText("= " + result);
            }
        }
    };

    EditText number1,number2, delay;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assigning required ui elements
        Button calculateButton = (Button) findViewById(R.id.calculateButton);
        number1 = (EditText) findViewById(R.id.number1);
        number2 = (EditText) findViewById(R.id.number2);
        delay = (EditText) findViewById(R.id.delay);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

        // assigning onClickListener
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVE_RESULT);
        bManager.registerReceiver(bReceiver, intentFilter);
    }

    @Override
    protected void onDestroy(){
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bReceiver);
        super.onDestroy();
    }

    public void calculate(){
        try {
            // parsing texts from EditTexts
            int i = Integer.parseInt(number1.getText() + "", 10);
            int j = Integer.parseInt(number2.getText() + "", 10);
            int delay_value = Integer.parseInt(delay.getText() + "", 10);

            // creating intent
            Intent intent = new Intent(MyService.CALCULATE_SUM, null, this, MyService.class);
            intent.putExtra("first", i);
            intent.putExtra("second", j);
            intent.putExtra("delay", delay_value);

            startService(intent);
        }catch(NumberFormatException e){
            Toast.makeText(this,"Invalid input",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

}
