package com.bkc.dtmfgenerator;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DTMFgen extends IntentService{

	private mServer server;
	public static final int PORT = 4000;
	private ToneGenerator tg;

	public DTMFgen() {
		super("DTMFgen");
	}

	public DTMFgen(String name) {
		super(name);
	}

	private class mServer extends NanoHTTPD {
		public mServer() {
			super(PORT);
		}

		@Override
		public Response serve(IHTTPSession session) {
			String uri = session.getUri();
			Method method = session.getMethod();
			Map<String, String> files = new HashMap<String, String>();
			if (Method.PUT.equals(method) || Method.POST.equals(method)) {
				try {
					session.parseBody(files);
				} catch (IOException ioe) {
					return new Response(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
				} catch (ResponseException re) {
					return new Response(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
				}
			}
			Map<String, String> parameters = session.getParms();
			if(uri.contentEquals("/")){
				InputStream is;
				try {
					is = getAssets().open("index.html");
					int size = is.available();
					byte[] buffer = new byte[size];
					is.read(buffer);
					is.close();
					String response = new String(buffer);
					return new NanoHTTPD.Response(response);
				} catch (IOException e) {
					e.printStackTrace();
					return new Response(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "Internal Server Error. Try again or mail a report to bkchaitan94@gmail.com.");
				}
			} else if (method == Method.POST){
				if (parameters.containsKey("p")) {
					if (parameters.get("p").contentEquals(getSharedPreferences("server", 0).getString("key", ""))) {
						if (uri.contentEquals("/1")) {
							tg.startTone(ToneGenerator.TONE_DTMF_1);
						} else if (uri.contentEquals("/2")) {
							tg.startTone(ToneGenerator.TONE_DTMF_2);
						} else if (uri.contentEquals("/3")) {
							tg.startTone(ToneGenerator.TONE_DTMF_3);
						} else if (uri.contentEquals("/4")) {
							tg.startTone(ToneGenerator.TONE_DTMF_4);
						} else if (uri.contentEquals("/5")) {
							tg.startTone(ToneGenerator.TONE_DTMF_5);
						} else if (uri.contentEquals("/6")) {
							tg.startTone(ToneGenerator.TONE_DTMF_6);
						} else if (uri.contentEquals("/7")) {
							tg.startTone(ToneGenerator.TONE_DTMF_7);
						} else if (uri.contentEquals("/8")) {
							tg.startTone(ToneGenerator.TONE_DTMF_8);
						} else if (uri.contentEquals("/9")) {
							tg.startTone(ToneGenerator.TONE_DTMF_9);
						} else if (uri.contentEquals("/0")) {
							tg.startTone(ToneGenerator.TONE_DTMF_0);
						} else if (uri.contentEquals("/a")) {
							tg.startTone(ToneGenerator.TONE_DTMF_A);
						} else if (uri.contentEquals("/b")) {
							tg.startTone(ToneGenerator.TONE_DTMF_B);
						} else if (uri.contentEquals("/c")) {
							tg.startTone(ToneGenerator.TONE_DTMF_C);
						} else if (uri.contentEquals("/d")) {
							tg.startTone(ToneGenerator.TONE_DTMF_D);
						} else if (uri.contentEquals("/e")) {
							tg.startTone(ToneGenerator.TONE_DTMF_P);
						} else if (uri.contentEquals("/f")) {
							tg.startTone(ToneGenerator.TONE_DTMF_S);
						} else if (uri.contentEquals("/A")) {
							tg.startTone(ToneGenerator.TONE_DTMF_A);
						} else if (uri.contentEquals("/B")) {
							tg.startTone(ToneGenerator.TONE_DTMF_B);
						} else if (uri.contentEquals("/C")) {
							tg.startTone(ToneGenerator.TONE_DTMF_C);
						} else if (uri.contentEquals("/D")) {
							tg.startTone(ToneGenerator.TONE_DTMF_D);
						} else if (uri.contentEquals("/E")) {
							tg.startTone(ToneGenerator.TONE_DTMF_P);
						} else if (uri.contentEquals("/F")) {
							tg.startTone(ToneGenerator.TONE_DTMF_S);
						} else if (uri.contentEquals("/stop")) {
							tg.stopTone();
						}
						return new NanoHTTPD.Response(Response.Status.ACCEPTED, MIME_PLAINTEXT, "");
					} else {
						return new Response(Response.Status.UNAUTHORIZED, MIME_PLAINTEXT, "User Unauthorized: Password wrong.");
					}
				} else {
					return new Response(Response.Status.UNAUTHORIZED, MIME_PLAINTEXT, "User Unauthorized: Password required.");
				}
			}
			return new NanoHTTPD.Response(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "404: Not Found");
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
