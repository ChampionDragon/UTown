package com.bs.utown.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Description:HTTP通过GET请求方式传输的工具类
 * AUTHOR: Champion Dragon
 * created at 2018/6/6
 **/

public class HttpByGet {
    public static String result = "none";
    public static String error = "error";
    private static String tag = "HttpByGet";

    public static String executeHttpGet(String urlStr) {
        Logs.w(tag+" 21   "+urlStr);
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            // connection.setRequestProperty("user-agent",
            // "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");// 用于定义网络文件的类型和网页的编码
            connection.setReadTimeout(20 * 1000);
            connection.setConnectTimeout(20 * 1000);
            // 建立实际的连接
            connection.connect();

            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();
            Log.i("lcb", "httpbyget34   code: " + responseCode + "     msg:  "
                    + responseMessage);

            in = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
            Logs.v(tag+"52   "+result);
        } catch (Exception e) {
            result = error;
            Logs.d(tag+"55   " + e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 返回自定义url
     */
    public static String get(String... b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            if ((i + 1) % 2 == 0) {
                if ((i + 1) == b.length)
                    sb.append("=" + b[i]);
                else {
                    sb.append("=" + b[i] + "&");
                }
            } else {
                sb.append(b[i]);
            }
        }
        return "?" + sb.toString();
    }

    public static String getUrl(String url, String... b) {
        String urlstr, buffer;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            if ((i + 1) % 2 == 0) {
                if ((i + 1) == b.length)
                    sb.append("=" + b[i]);
                else {
                    sb.append("=" + b[i] + "&");
                }
            } else {
                sb.append(b[i]);
            }
        }
        buffer = sb.toString();
        Logs.d(tag + "105     " + buffer);
        try {
            buffer = URLEncoder.encode(buffer, "utf-8");
            Logs.d(tag + "108     " + buffer);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        urlstr = url + "?" + buffer;
        return urlstr;

    }
}
