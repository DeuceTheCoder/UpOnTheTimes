package com.deucecoded.uponthetimes.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.deucecoded.uponthetimes.R;
import com.deucecoded.uponthetimes.view.ArticleActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import butterknife.OnItemClick;
import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.edit_query)
    EditText queryEditText;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.gv_results)
    GridView resultsGridView;
    List<Article> articles;
    private ArticleArrayAdapter articleArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        setupViews();
    }

    private void setupViews() {
        articles = new ArrayList<>();
        articleArrayAdapter = new ArticleArrayAdapter(this, articles);
        resultsGridView.setAdapter(articleArrayAdapter);
    }

    @OnItemClick(R.id.gv_results)
    public void launchArticleView(int position) {
        Article article = articleArrayAdapter.getItem(position);
        if (article == null) {
            return;
        }
        Intent intent = new Intent(getApplicationContext(), ArticleActivity.class);
        intent.putExtra(ArticleActivity.ARTICLE_KEY, article);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnEditorAction(R.id.edit_query)
    public boolean onEditorAction(TextView v, int actionId) {
        boolean handled = false;
        if (actionId == EditorInfo.IME_ACTION_GO) {
            handled = true;
            onArticleSearch(v);
        }
        return handled;
    }

    public void onArticleSearch(View view) {
        String query = queryEditText.getText().toString();
        articleArrayAdapter.clear();

        AsyncHttpClient httpClient = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        String apiKey = "c019a04278564fe08fe16424d57c91b8";

        RequestParams requestParams = new RequestParams();
        requestParams.put("api_key", apiKey);
        requestParams.put("page", 0);
        requestParams.put("q", query);

        httpClient.get(url, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());

                JSONArray articleResultsArray;

                try {
                    JSONObject responseJson = response.getJSONObject("response");
                    articleResultsArray = responseJson.getJSONArray("docs");
                    articleArrayAdapter.addAll(Article.fromJsonArray(articleResultsArray));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
