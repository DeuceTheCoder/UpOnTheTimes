package com.deucecoded.uponthetimes.search;


import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Article implements Serializable {
    private String webUrl;
    private String headline;

    private String thumbnailUrl;

    public Article(JSONObject jsonObject) {
        try {
            webUrl = jsonObject.getString("web_url");
            headline = jsonObject.getJSONObject("headline").getString("main");
            JSONArray multimediaArray = jsonObject.getJSONArray("multimedia");
            if (multimediaArray.length() > 0) {
                JSONObject multimediaObject = multimediaArray.getJSONObject(0);
                thumbnailUrl = String.format("http://www.nytimes.com/%s", multimediaObject.getString("url"));
            } else {
                thumbnailUrl = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static List<Article> fromJsonArray(JSONArray jsonArray) {
        List<Article> articles = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject articleJson = jsonArray.getJSONObject(i);
                articles.add(new Article(articleJson));
            } catch (JSONException e) {
                e.printStackTrace();
                return articles;
            }
        }

        return articles;
    }

    @NonNull
    public String getWebUrl() {
        return webUrl == null ? "" : webUrl;
    }

    @NonNull
    public String getHeadline() {
        return headline == null ? "" : headline;
    }

    @NonNull
    public String getThumbnailUrl() {
        return thumbnailUrl == null ? "" : thumbnailUrl;
    }
}
