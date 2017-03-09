package com.lafamilia.rim.adapters;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.lafamilia.rim.R;
import com.lafamilia.rim.models.Partner;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 *
 * Created by laFamilia on 09/03/2017.
 *
 */

public class PartnerAdapter extends RealmBaseAdapter<Partner> implements ListAdapter {

    public PartnerAdapter(@Nullable OrderedRealmCollection<Partner> realmResults) {
        super(realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_list_partner, parent, false
            );
        }

        // Magic is this.Magic
        if (this.adapterData != null) {

            // Get item
            Partner item  = adapterData.get(position);

            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView age = (TextView) convertView.findViewById(R.id.age);

            // Populate view
            name.setText(item.getName());
            age.setText(String.valueOf(item.getAge()));

        }
        return convertView;
    }
}

