package com.lafamilia.rim.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.lafamilia.rim.R;
import com.lafamilia.rim.models.Book;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 *
 * Created by laFamilia on 09/03/2017.
 *
 */


public class BookAdapter extends RealmBaseAdapter<Book> implements ListAdapter {

    public BookAdapter(@Nullable OrderedRealmCollection<Book> realmResults) {
        super(realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_list_book, parent, false
            );
        }

        // Magic is this.Magic
        if (this.adapterData != null) {

            // Get item
            Book item  = adapterData.get(position);

            TextView name = (TextView) convertView.findViewById(R.id.mTitle);
            TextView age = (TextView) convertView.findViewById(R.id.author);
            ImageView available = (ImageView) convertView.findViewById(R.id.available);

            // Populate view
            name.setText(item.getTitle());
            age.setText(item.getAuthor());

            if (item.isAvailable()) {
                available.setImageDrawable(ContextCompat.getDrawable(available.getContext(),
                        R.drawable.ic_available));
            } else {
                available.setImageDrawable(ContextCompat.getDrawable(available.getContext(),
                        R.drawable.ic_no_available));
            }

        }
        return convertView;
    }
}
