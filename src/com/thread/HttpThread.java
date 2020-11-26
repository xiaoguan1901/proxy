package com.thread;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;

import com.entiry.HttpEntiryOption;

public class HttpThread extends Thread  {

    private final CloseableHttpResponse response;
    private final HttpEntiryOption option;
    public HttpThread(CloseableHttpResponse response,HttpEntiryOption option) {
        this.response = response;
        this.option = option;
        this.start();
    }

    @Override
    public void run() {
         try {
			option.hadler(response);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
