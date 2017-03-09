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

        // Initialize realm
        Realm.init(getContext());

        // Configure realm
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();

        // Clear the realm from last time
        Realm.deleteRealm(realmConfiguration);

        // Create a new empty instance of Realm
        realm = Realm.getInstance(realmConfiguration);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partner, container, false);

        listView = (ListView) view.findViewById(R.id.list_view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Partner partner1 = bgRealm.createObject(Partner.class);
                Partner partner2 = bgRealm.createObject(Partner.class);

                // Update
                partner1.setName("Marc");
                partner1.setAge(29);

                partner2.setName("David");
                partner2.setAge(19);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
                Log.d("IS","Success");
                setAdapter();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
                Log.d("IS","Error");

            }
        });
    }

    public RealmResults<Partner> loadPartners() {
        RealmQuery<Partner> query = realm.where(Partner.class);
        return query.findAll();
    }

    public void setAdapter(){
        partnerAdapter = new PartnerAdapter(loadPartners());
        listView.setAdapter(partnerAdapter);
        partnerAdapter.notifyDataSetChanged();
    }
}
