package app.android.javapwpb2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvTime;
    Button btnStart, btnPause, btnReset, btnLap;
    long MlSecond, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Seconds, Minuters, MiliSeconds;
    ListView listView;
    String[] ListElements = new String[]{};
    List<String> ListElementArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTime = (TextView)findViewById(R.id.tv_time);
        btnStart = (Button)findViewById(R.id.btn_mulai);
        btnPause = (Button)findViewById(R.id.btn_berhenti);
        btnReset = (Button)findViewById(R.id.btn_reset);
        btnLap = (Button)findViewById(R.id.btn_save);
        listView = (ListView)findViewById(R.id.listview1);

        handler = new Handler();

        ListElementArrayList = new ArrayList<String>(Arrays.asList(ListElements));

        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                ListElementArrayList
        );

        listView.setAdapter(adapter);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                btnReset.setEnabled(false);
            }
        });


        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeBuff += MiliSeconds;
                handler.removeCallbacks(runnable);
                btnReset.setEnabled(true);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MiliSeconds = (int) 0L;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minuters = 0 ;
                MiliSeconds = 0 ;
                tvTime.setText("00:00:00");
                ListElementArrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });

        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListElementArrayList.add(tvTime.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

    }

    public Runnable runnable = new Runnable() {
        public void run() {
            MiliSeconds = (int) (SystemClock.uptimeMillis() - StartTime);
            UpdateTime = TimeBuff + MiliSeconds;
            Seconds = (int) (UpdateTime / 1000);
            Minuters = Seconds / 60;
            Seconds = Seconds % 60;
            MiliSeconds = (int) (UpdateTime % 1000);
            tvTime.setText("" + Minuters + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MiliSeconds));
            handler.postDelayed(this, 0);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_SpeechToText:
                Intent intent = new Intent(getApplicationContext(), SpeechActivity.class);
                startActivity(intent);
                break;
            case R.id.item_stopwatch:
                Intent inten = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(inten);
        }

        return super.onOptionsItemSelected(item);
    }

}
