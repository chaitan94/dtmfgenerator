package com.bkc.dtmfgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import android.app.Activity;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import fi.iki.elonen.NanoHTTPD;

public class MainActivity extends Activity implements OnTouchListener{

    private Button b1,b2,b3,b4,b5,b6,b7,b8,b9,bserve;
    private TextView tvip;
    private ToneGenerator tg;
    private mServer server;
    private int port = 4000;
    
    private class mServer extends NanoHTTPD {
    	public mServer() {
    		super(port);
    	}

    	@Override
        public Response serve(String uri, Method method, 
                              Map<String, String> header,
                              Map<String, String> parameters,
                              Map<String, String> files) {
    		String answer = "";
    		if(uri.contentEquals("/")){
    	        InputStream is;
				try {
					is = getAssets().open("index.html");
	    	        int size = is.available();
	    	        byte[] buffer = new byte[size];
	    	        is.read(buffer); is.close();
	    	        String str = new String(buffer);
	    	        answer += str;
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}else if(uri.contentEquals("/1")){
    			tg.startTone(ToneGenerator.TONE_DTMF_1);
    		}else if(uri.contentEquals("/2")){
    			tg.startTone(ToneGenerator.TONE_DTMF_2);
    		}else if(uri.contentEquals("/3")){
    			tg.startTone(ToneGenerator.TONE_DTMF_3);
    		}else if(uri.contentEquals("/4")){
    			tg.startTone(ToneGenerator.TONE_DTMF_4);
    		}else if(uri.contentEquals("/5")){
    			tg.startTone(ToneGenerator.TONE_DTMF_5);
    		}else if(uri.contentEquals("/6")){
    			tg.startTone(ToneGenerator.TONE_DTMF_6);
    		}else if(uri.contentEquals("/7")){
    			tg.startTone(ToneGenerator.TONE_DTMF_7);
    		}else if(uri.contentEquals("/8")){
    			tg.startTone(ToneGenerator.TONE_DTMF_8);
    		}else if(uri.contentEquals("/9")){
    			tg.startTone(ToneGenerator.TONE_DTMF_9);
    		}else if(uri.contentEquals("/stop")){
    			tg.stopTone();
    		}else{answer="404";}
            return new NanoHTTPD.Response(answer);
        }
    }
    
    @SuppressWarnings("deprecation")
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
        tvip = (TextView) findViewById(R.id.tvip);
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
        
        server = new mServer();
        try {
			server.start();
			String IP;
			WifiManager wim= (WifiManager) getSystemService(WIFI_SERVICE);
			if(!wim.isWifiEnabled()){
				tvip.setText("Enable wi-fi and restart the app");
			}else{
				IP=Formatter.formatIpAddress(wim.getConnectionInfo().getIpAddress());
				tvip.setText(IP+":"+port);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
	protected void onDestroy() {
		super.onDestroy();
        if (server != null)
            server.stop();
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