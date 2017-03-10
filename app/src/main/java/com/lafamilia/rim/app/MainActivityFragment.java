package com.lafamilia.rim.app;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lafamilia.rim.R;
import com.lafamilia.rim.adapters.PartnerAdapter;
import com.lafamilia.rim.models.Partner;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 * help - https://realm.io/docs/java/latest/
 */
public class MainActivityFragment extends Fragment {

    private Realm realm;
    private PartnerAdapter partnerAdapter;
    private ListView listView;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initRealm() {
        // Initialize Realm
        Realm.init(getContext());

        // Configure Realm
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .name("library.realm")
                .build();

        // Clear the realm from last time
        Realm.deleteRealm(realmConfiguration);

        // Create a new empty instance of Realm
        realm = Realm.getInstance(realmConfiguration);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }
}
