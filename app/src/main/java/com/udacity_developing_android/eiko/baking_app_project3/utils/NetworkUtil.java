package com.udacity_developing_android.eiko.baking_app_project3.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtil {

    private static final String LG = NetworkUtil.class.getSimpleName();

    private static Bitmap thumbnailBitmap;
    private static String contentType;

    public static String getResponseFrimUrl(URL requestUrl)
            throws IOException {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String resultJson = null;
        try {
            if (requestUrl != null) {
                urlConnection = (HttpURLConnection)
                        requestUrl.openConnection();
            }
            if (urlConnection != null) {
                inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    Scanner scanner = new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner.hasNext()) {
                        resultJson = scanner.next();
                    }
                }
                if (urlConnection.getResponseCode() ==
                        HttpURLConnection.HTTP_OK) {
                    return resultJson;
                } else {
                }
            }
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    public static Bitmap getBitmapFromUrl(String bit) {
        String[] paramas = new String[]{bit};
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection connection = (HttpURLConnection)
                            url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);
                    return myBitmap;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                thumbnailBitmap = bitmap;
            }
        }.execute(paramas);
        return thumbnailBitmap;
    }

    public static String getUrlContentType(String url) {
        final String[] params = new String[]{url};
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection connection =
                            (HttpURLConnection) url.openConnection();
                    String urlconnectiontype = connection.getHeaderField(
                            "Content-Type");
                    return urlconnectiontype;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
//                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                contentType = s;
            }
        }.execute(params);
        return contentType;
    }

    public static boolean isNetworkConected(Context context) {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}

