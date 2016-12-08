package com.deucecoded.uponthetimes.search;


import android.util.Log;

import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class SearchBuilder {

    private String apiKey;
    private int page;
    private String query;
    private Date earliestDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
    private boolean isSortedByOldest;
    private List<String> newsDesks;

    SearchBuilder() {
        reset();
    }

    RequestParams build() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("api_key", apiKey);
        requestParams.put("page", page);
        requestParams.put("q", query);
        if (earliestDate != null)
            requestParams.put("begin_date", dateFormat.format(earliestDate));
        if (isSortedByOldest) {
            requestParams.put("sort", "oldest");
        } else {
            requestParams.put("sort", "newest");
        }
        if (!newsDesks.isEmpty()) requestParams.put("fq", formatDesks());

        Log.d("BUILT SEARCH", requestParams.toString());
        return requestParams;
    }

    void reset() {
        this.apiKey = null;
        this.page = 0;
        this.query = null;
        this.earliestDate = null;
        this.isSortedByOldest = false;
        this.newsDesks = new ArrayList<>();
    }

    SearchBuilder withApiKey(String key) {
        this.apiKey = key;
        return this;
    }

    SearchBuilder withQuery(String query) {
        this.query = query;
        return this;
    }

    SearchBuilder withPage(int page) {
        this.page = page;
        return this;
    }

    SearchBuilder withEarliestDate(Date earliestDate) {
        this.earliestDate = earliestDate;
        return this;
    }

    SearchBuilder shouldSortByOldest(boolean sortByOldest) {
        this.isSortedByOldest = sortByOldest;
        return this;
    }

    SearchBuilder withNewsDesks(List<String> desks) {
        this.newsDesks = desks;
        return this;
    }

    private String formatDesks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String desk : this.newsDesks) {
            stringBuilder.append('\"').append(desk).append('\"').append(" ");
        }
        String desks = stringBuilder.toString().trim();
        return "news_desk:(" + desks + ")";
    }

    void incrementPage() {
        page++;
    }
}
