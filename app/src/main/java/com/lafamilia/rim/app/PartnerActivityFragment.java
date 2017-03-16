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

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class PartnerActivityFragment extends Fragment {

    private Realm realm;
    private PartnerAdapter partnerAdapter;
    private ListView listView;

    public PartnerActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRealm();
    }

    private void initRealm() {
        // Initialize Realm
        Realm.init(getContext());

        // Configure Realm
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .name("library.realm")
                .build();


        // Create a new empty instance of Realm
        realm = Realm.getInstance(realmConfiguration);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partner, container, false);

        listView = (ListView) view.findViewById(R.id.list_view_partner);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Set up adapter
        setAdapter();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                // Save objects to realm database
                Partner partner1 = bgRealm.createObject(Partner.class);
                Partner partner2 = bgRealm.createObject(Partner.class);

                partner1.setName("Marc");
                partner1.setAge(29);

                partner2.setName("David");
                partner2.setAge(19);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success
                Log.d("IS","Success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled
                Log.d("IS","Error");

            }
        });
    }

    public RealmResults<Partner> loadPartners() {
        // Get a list of partners from the realm database
        return realm.where(Partner.class).findAll();
    }

    public void setAdapter(){
        partnerAdapter = new PartnerAdapter(loadPartners());
        listView.setAdapter(partnerAdapter);
    }
}
