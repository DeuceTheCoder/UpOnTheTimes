package com.deucecoded.uponthetimes.search;


import com.loopj.android.http.RequestParams;

class SearchBuilder {

    private String apiKey;
    private int page;
    private String query;
    private String earliestDate;
    private boolean isSortedByOldest;
    private String fq;

    SearchBuilder() {
        reset();
    }

    RequestParams build() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("api_key", apiKey);
        requestParams.put("page", page);
        requestParams.put("q", query);
        if (earliestDate != null && !earliestDate.isEmpty())
            requestParams.put("begin_date", earliestDate);
        if (isSortedByOldest) {
            requestParams.put("sort", "oldest");
        } else {
            requestParams.put("sort", "newest");
        }
        if (fq != null && !fq.isEmpty()) requestParams.put("fq", fq);

        return requestParams;
    }

    void reset() {
        this.apiKey = null;
        this.page = 0;
        this.query = null;
        this.earliestDate = null;
        this.isSortedByOldest = false;
        this.fq = null;
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

    SearchBuilder withEarliestDate(String earliestDate) {
        this.earliestDate = earliestDate;
        return this;
    }

    SearchBuilder shouldSortByOldest(boolean sortByOldest) {
        this.isSortedByOldest = sortByOldest;
        return this;
    }

    SearchBuilder withNewsDesks(String desks) {
        this.fq = "news_desk:(" + desks + ")";
        return this;
    }
}
