package com.wuxianedu.jminews.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 网络请求框架
 * 常用的请求方式get post
 * Created by Hu131 on 2016/6/28.
 */
public class HttpUtils {

    /**
     * get放式请求
     * @param uri
     * @return
     */
    public static String getHttp(String uri) throws MalformedURLException {
        URL url = new URL(uri);
        //请求方式两种： HttpUrlConnection  HttpClient(不推荐使用)
        try {
            //打开一个链接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            //得到返回码
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK){ //是否请求成功
                //得到输入流
                InputStream is = connection.getInputStream();
                String s = IOUtils.toString(is, "utf-8"); //将inputStream转换成String
                return s;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}
