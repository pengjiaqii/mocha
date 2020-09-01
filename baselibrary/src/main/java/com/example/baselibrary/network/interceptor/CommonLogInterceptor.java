package com.example.baselibrary.network.interceptor;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class CommonLogInterceptor implements Interceptor {
    @SuppressWarnings("CharsetObjectCanBeUsed")
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String TAG = "NetworkLog";

    //是否打印body，如下载数据容易oom不能打印body
    private boolean logBody;

    public CommonLogInterceptor(boolean logBody) {
        this.logBody = logBody;
    }

    @Override
    public @NonNull
    Response intercept(Chain chain) throws IOException {
        Response proceed = chain.proceed(chain.request());
        if (logBody) {
            Log.i(TAG, "=========================START==========================");
            String url = chain.request().url().url().toString();
            Log.i(TAG, "Request: " + url);
            Log.i(TAG, "Method: " + chain.request().method());
            long requestTime = System.currentTimeMillis();

            Log.i(TAG, "Response: " + url + " code:" + proceed.code() + " requestTime=" + (System.currentTimeMillis() - requestTime));

            ResponseBody body = proceed.body();
            if (body != null) {
                BufferedSource source = body.source();
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.buffer();
                Buffer clone = buffer.clone();
                String unicode = clone.readString(UTF8);
                //                String unicode2String = unicode2String(unicode);
                Log.i(TAG, "Body: " + url + "\n" + unicode2String(unicode));
                clone.close();
            } else {
                Log.i(TAG, "Body " + url + "-->is null");
            }

            Log.i(TAG, "=========================END==========================");
        }
        return proceed;
    }

    /**
     * unicode 转字符串
     *
     * @param unicode 全为 Unicode 的字符串
     * @return
     */
    public String unicode2String(String unicode) {
        //解决中文unicode的问题
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(unicode);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            unicode = unicode.replace(matcher.group(1), ch + "");
        }
        return unicode;
    }

}
