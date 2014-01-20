package com.bkc.dtmfgenerator;

import android.app.Activity;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnTouchListener{

    Button b1,b2,b3,b4,b5,b6,b7,b8,b9,bserve;
    ToneGenerator tg;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        b5 = (Button) findViewById(R.id.b5);
        b6 = (Button) findViewById(R.id.b6);
        b7 = (Button) findViewById(R.id.b7);
        b8 = (Button) findViewById(R.id.b8);
        b9 = (Button) findViewById(R.id.b9);
        bserve = (Button) findViewById(R.id.bserve);
        b1.setOnTouchListener(this);
        b2.setOnTouchListener(this);
        b3.setOnTouchListener(this);
        b4.setOnTouchListener(this);
        b5.setOnTouchListener(this);
        b6.setOnTouchListener(this);
        b7.setOnTouchListener(this);
        b8.setOnTouchListener(this);
        b9.setOnTouchListener(this);
        bserve.setOnTouchListener(this);
        tg = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            switch(v.getId()){
                case R.id.b1:
                tg.startTone(ToneGenerator.TONE_DTMF_1);
                break;
                case R.id.b2:
                tg.startTone(ToneGenerator.TONE_DTMF_2);
                break;
                case R.id.b3:
                tg.startTone(ToneGenerator.TONE_DTMF_3);
                break;
                case R.id.b4:
                tg.startTone(ToneGenerator.TONE_DTMF_4);
                break;
                case R.id.b5:
                tg.startTone(ToneGenerator.TONE_DTMF_5);
                break;
                case R.id.b6:
                tg.startTone(ToneGenerator.TONE_DTMF_6);
                break;
                case R.id.b7:
                tg.startTone(ToneGenerator.TONE_DTMF_7);
                break;
                case R.id.b8:
                tg.startTone(ToneGenerator.TONE_DTMF_8);
                break;
                case R.id.b9:
                tg.startTone(ToneGenerator.TONE_DTMF_9);
                break;
            }
            break;
            case MotionEvent.ACTION_UP:
            tg.stopTone();
            break;
        }
        return false;
    }
    
}