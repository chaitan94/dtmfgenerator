package com.bkc.dtmfgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import fi.iki.elonen.NanoHTTPD;

public class DTMFgen extends IntentService{

	private mServer server;
    private int port = 4000;
    private ToneGenerator tg;

    public DTMFgen() {
		super("DTMFgen");
    }

    public DTMFgen(String name) {
		super(name);
	}
    
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
    		}else if(parameters.get("p").contentEquals(getSharedPreferences("server", 0).getString("key", ""))){
	    		if(uri.contentEquals("/1")){
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
	    		}
    		}
            return new NanoHTTPD.Response(answer);
        }
    }

	@Override
	protected void onHandleIntent(Intent intent) {
        tg = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

        server = new mServer();
        try {
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
