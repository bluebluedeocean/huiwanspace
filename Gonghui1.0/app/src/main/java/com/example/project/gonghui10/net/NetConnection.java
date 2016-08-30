package com.example.project.gonghui10.net;

import android.os.AsyncTask;
import android.util.Log;

import com.example.project.gonghui10.Config;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Meng on 2016/7/31.
 */
public class NetConnection {
    public NetConnection(final String url, final HttpMethod method, final SuccessCallBack successCallBack, final FailCallBace failCallBace, final String... kvs) {
        Log.i("info","NetConnection传入的参数URL + method + kvs[0] :" + url + method.toString() + kvs[0]);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                StringBuffer paramsStr = new StringBuffer();
                for (int i = 0; i < kvs.length; i += 2) {
                    paramsStr.append(kvs[i]).append("=").append(kvs[i + 1]).append("&");
                }
                paramsStr.append(Config.KEY_AGENT).append("=").append(Config.KEY_APP);
                Log.i("info","NetConnection请求的URL：" + url + paramsStr.toString());
                try {
                    URLConnection urlConnection;
                    switch (method) {
                        case POST:
                            urlConnection = new URL(url).openConnection();
                            urlConnection.setDoInput(true);
                            urlConnection.setDoOutput(true);
                            BufferedWriter bW = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(),Config.CHARSET));
                            bW.write(paramsStr.toString());
                            bW.flush();
                            break;
                        default:
                            urlConnection = new URL(url + "?" + paramsStr.toString()).openConnection();
                            break;
                    }

                    System.out.println("Request url:" + urlConnection.getURL());
                    System.out.println("Request data:" + paramsStr);

                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    InputStream inputStream = urlConnection.getInputStream();
                    byte[] data = new byte[1024];

                    int len = 0;
                    while ((len = inputStream.read(data)) != -1) {
                        outStream.write(data, 0, len);
                    }
                    inputStream.close();
                    String result = new String(outStream.toByteArray());
                    System.out.println("result:" + result);
                    Log.i("info","result:" + result);
                    return result;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result != null&&!result.equals("")) {
                    if (successCallBack != null) {
                        successCallBack.onSuccess(result);
                    }
                }else {
                    if(failCallBace!=null) {
                        failCallBace.onFail();
                    }
                }
            }
        }.execute();
    }

    public static interface SuccessCallBack {
        void onSuccess(String result);
    }

    public static interface FailCallBace {
        void onFail();
    }
}
