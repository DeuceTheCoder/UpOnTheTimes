package com.deucecoded.uponthetimes.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.deucecoded.uponthetimes.R;
import com.squareup.picasso.Picasso;

import java.util.List;

class ArticleArrayAdapter extends ArrayAdapter<Article> {
    ArticleArrayAdapter(Context context, List<Article> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Article article = getItem(position);
        if (article == null) {
            return convertView;
        }

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
        }

        TextView headlineTextView = (TextView) convertView.findViewById(R.id.text_headline);
        ImageView articleImageView = (ImageView) convertView.findViewById(R.id.iv_article_image);
        articleImageView.setImageResource(0);

        headlineTextView.setText(article.getHeadline());
        String thumbnailUrl = article.getThumbnailUrl();
        if (!thumbnailUrl.isEmpty()) {
            Picasso.with(getContext()).load(thumbnailUrl).into(articleImageView);
        }

        return convertView;
    }
}
