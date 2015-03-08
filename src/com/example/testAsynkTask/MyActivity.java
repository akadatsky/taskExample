package com.example.testAsynkTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MyActivity extends Activity {

    private TextView textView;
    private MyTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new MyTask();
                task.execute();
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (task != null && !task.isCancelled()) {
                    task.cancel(true);
                }
            }
        });

        textView = (TextView) findViewById(R.id.textView);
    }

    private class MyTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            Toast.makeText(MyActivity.this, "task started", Toast.LENGTH_LONG).show();
            textView.setText("");
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(5);
                publishProgress(50);
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                Log.i("MyTag", "Task interrupted", e);
            }
            return "Task done";
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            textView.setText("Done " + values[0] + "%");
        }
    }

}
