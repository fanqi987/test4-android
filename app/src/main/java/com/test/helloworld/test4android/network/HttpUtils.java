package com.test.helloworld.test4android.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    public static void requestHttp(final String urlString, final RequestLinstener linstener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader br = null;
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(urlString);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(5000);
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    InputStream is = connection.getInputStream();
                    br = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    String s;
                    while ((s = br.readLine()) != null) {
                        sb.append(s);
                    }
                    linstener.onSuccess(sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    linstener.onFail(e);
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

            }
        }).start();
    }
}
