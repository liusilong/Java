package com.lsl.flowlayout;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    String[] arr = new String[]{"adsfaf","afdsfasdf","dafdaf","dfd","afdfaf","dafdadfa",
            "adsfaf","adasf","dafdaf","dfd","afdfaf","dafdadfa"};
    private FlowLayout flowLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flowLayout = (FlowLayout) findViewById(R.id.fl);
        WindowManager wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Log.e(TAG, "onCreate: "+width+"=="+height);
        Log.e(TAG, "onCreate: flowLayout:"+flowLayout.getWidth());
        flowLayout.post(new Runnable() {
            @Override
            public void run() {
                initViews(flowLayout.getWidth());
            }
        });

    }

    private void initViews(int width){
        for (int i = 0; i < arr.length; i++) {
            final TextView textView = new TextView(this);
            ViewGroup.MarginLayoutParams lp  =
                    new ViewGroup.MarginLayoutParams((width - 60)/3,150);
            lp.setMargins(10,10,10,10);
            textView.setGravity(Gravity.CENTER);
            textView.setText(arr[i]);
            textView.setLayoutParams(lp);
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundResource(R.drawable.tv_bg);
            flowLayout.addView(textView);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "txt:"+textView.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
