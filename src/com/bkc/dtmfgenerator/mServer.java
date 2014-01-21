package com.bkc.dtmfgenerator;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class mServer extends NanoHTTPD {
	public mServer() {
		super(8080);
	}

	@Override
    public Response serve(String uri, Method method, 
                          Map<String, String> header,
                          Map<String, String> parameters,
                          Map<String, String> files) {
        String answer = "";
        answer += "Request: "+uri+"<br>";
        answer += "Method: "+method.toString()+"<br>";
        answer += "<div id='key'></div><script>var key = document.getElementById('key');window.onkeypress=function(e){key.innerHTML=e.keyCode}</script>";

        return new NanoHTTPD.Response(answer);
    }
}
