package com.chrissetiana.bookapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class BookQuery {
    private final static String TAG = BookQuery.class.getSimpleName();

    public static ArrayList<BookActivity> fetchData(String src) {
        URL url = createUrl(src);
        String response = null;

        try {
            response = makeHttpReq(url);
        } catch (IOException e) {
            Log.e(TAG, "Connection not available", e);
        }

        ArrayList<BookActivity> list = getJSONData(response);
        return list;
    }

    private static URL createUrl(String strUrl) {
        URL newUrl = null;

        try {
            newUrl = new URL(strUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error in making connection", e);
            return null;
        }
        return null;
    }

    private static String makeHttpReq(URL varUrl) throws IOException {
        String response = "";
        if (varUrl == null) {
            return response;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) varUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.connect();

            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                response = readStream(inputStream);
            } else {
                Log.e(TAG, "Error code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Cannot establish connection");
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return response;
    }

    private static String readStream(InputStream stream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        if (stream != null) {
            InputStreamReader streamReader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private static ArrayList<BookActivity> getJSONData(String source) {
        if (TextUtils.isEmpty(source)) {
            return null;
        }

        ArrayList<BookActivity> list = new ArrayList<>();

        try {
            JSONObject object = new JSONObject(source);
            JSONArray items = object.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject info = items.getJSONObject(i);

                JSONObject searchInfo = info.getJSONObject("searchInfo");
                String description = searchInfo.getString("textSnippet");

                JSONObject volumeInfo = info.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("bookTitle");
                String author = volumeInfo.getString("authors");
                String publisher = volumeInfo.getString("publishedDate");
                String published = volumeInfo.getString("publisher");
                String pages = volumeInfo.getString("pageCount");

                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                String image = imageLinks.getString("smallThumbnail");

                BookActivity book = new BookActivity(title, author, published, publisher, pages, description, image);
                list.add(book);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Cannot read data", e);
        }
        return list;
    }
}
