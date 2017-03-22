package com.lafamilia.rim.app;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.lafamilia.rim.R;
import com.lafamilia.rim.adapters.PartnerAdapter;
import com.lafamilia.rim.models.Partner;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class PartnerActivityFragment extends Fragment implements View.OnClickListener {

    private Realm realm;
    private PartnerAdapter partnerAdapter;
    private ListView listView;
    private Button addPartnet;
    private EditText name, age;
    private String pName;
    private String pAge;

    public PartnerActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRealm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_partner, container, false);

        listView = (ListView) view.findViewById(R.id.list_view_partner);

        Button addPartner= (Button) view.findViewById(R.id.addPartner);

        name= (EditText) view.findViewById(R.id.etName);
        age = (EditText) view.findViewById(R.id.etAge);

        addPartner.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Set up adapter
        setAdapter();
    }

    public RealmResults<Partner> loadPartners() {
        // Get a list of partners from the realm database
        return realm.where(Partner.class).findAll();
    }


    @Override
    public void onClick(View view) {



        pName = name.getText().toString();
        pAge = age.getText().toString();
        name.setText("");
        age.setText("");

        if(pName.isEmpty() || pAge.isEmpty())return;

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                // Save objects to realm database
                Partner partner = bgRealm.createObject(Partner.class);


                partner.setName(pName);
                partner.setAge(pAge);

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

    private void initRealm() {
        // Initialize Realm
        Realm.init(getContext());

        // Configure Realm
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .name("library.realm")
                .build();

        // Clear the realm from last time
//        Realm.deleteRealm(realmConfiguration);

        // Create a new empty instance of Realm
        realm = Realm.getInstance(realmConfiguration);
    }



    public RealmResults<Partner> lloadPartners() {
        // Get a list of partners from the realm database
        return realm.where(Partner.class).findAll();
    }

    public void setAdapter(){
        partnerAdapter = new PartnerAdapter(loadPartners());
        listView.setAdapter(partnerAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
