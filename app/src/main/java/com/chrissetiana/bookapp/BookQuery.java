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
import java.util.List;

public class BookQuery {
    private final static String TAG = BookQuery.class.getSimpleName();

    public static List<BookActivity> fetchData(String src) {
        URL url = createUrl(src);
        String response = null;

        try {
            response = makeHttpReq(url);
        } catch (IOException e) {
            Log.e(TAG, "Connection not available", e);
        }

        List<BookActivity> list = getJSONData(response);
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

        return newUrl;
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
                response = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error code: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem in establishing connection");
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

    private static String readFromStream(InputStream stream) throws IOException {
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

    private static List<BookActivity> getJSONData(String source) {
        if (TextUtils.isEmpty(source)) {
            return null;
        }

        List<BookActivity> list = new ArrayList<>();

        try {
            JSONObject object = new JSONObject(source);
            JSONArray items = object.getJSONArray("items");
            int len = items.length();

            for (int i = 0; i < len; i++) {
                JSONObject property = items.getJSONObject(i);
                JSONObject volumeInfo = property.getJSONObject("volumeInfo");

                String author = "";
                JSONArray authors = volumeInfo.getJSONArray("authors");
                for (int j = 0; j < authors.length(); j++) {
                    author += authors.optString(j);
                }

                JSONArray industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers");
                JSONObject identifiers = industryIdentifiers.getJSONObject(0);
                String isbn = identifiers.optString("identifier");

                String title = volumeInfo.optString("title");
                String published = volumeInfo.optString("publishedDate");
                String publisher = volumeInfo.optString("publisher");
                String pages = volumeInfo.optString("pageCount");
                String description = volumeInfo.optString("description");

                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                int image = imageLinks.optInt("smallThumbnail");

                BookActivity book = new BookActivity(title, author, published, publisher, pages, description, image, isbn);
                list.add(book);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing data", e);
        }
        return list;
    }
}
