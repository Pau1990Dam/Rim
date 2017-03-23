package com.lafamilia.rim.app;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.lafamilia.rim.R;
import com.lafamilia.rim.adapters.BookAdapter;
import com.lafamilia.rim.adapters.PartnerAdapter;
import com.lafamilia.rim.models.Book;
import com.lafamilia.rim.models.Partner;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class BookActivityFragment extends Fragment {

    private Realm realm;
    private BookAdapter bookAdapter;
    private ListView listView;
    private Button add;
    private EditText title;
    private EditText author;
    private View view;

    public BookActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRealm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_book, container, false);

        listView = (ListView) view.findViewById(R.id.list_view_book);

        add = (Button) (view.findViewById(R.id.bt_add));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBook();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                deleteBook(position);
            }
        });

        return view;
    }

    public void deleteBook(final int position){

        realm.beginTransaction();
        Book book = loadBooks().get(position);
        book.deleteFromRealm();
        realm.commitTransaction();

    }

    public void addBook(){

        author = (EditText) view.findViewById(R.id.et_autor);
        title = (EditText) view.findViewById(R.id.et_titulo);

        final String aut = author.getText().toString();
        final String tit = title.getText().toString();

        author.setText("");
        title.setText("");

        if (!aut.isEmpty() && !tit.isEmpty()){

            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    // Save objects to realm database
                    Book book1 = bgRealm.createObject(Book.class);

                    book1.setTitle(aut);
                    book1.setAuthor(tit);
                    book1.setAvailable(true);

                }
            }, new Realm.Transaction.OnSuccess() {

                @Override
                public void onSuccess() {
                    // Transaction was a success
                    Log.d("IS","Success");
                   // bookAdapter.notifyDataSetChanged();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    // Transaction failed and was automatically canceled
                    Log.d("IS","Error");

                }
            });

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        // Set up adapter
        setAdapter();

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
        //Realm.deleteRealm(realmConfiguration);

        // Create a new empty instance of Realm
        realm = Realm.getInstance(realmConfiguration);
    }


    public RealmResults<Book> loadBooks() {
        // Get a list of partners from the realm database
        return realm.where(Book.class).findAll();
    }

    public void setAdapter(){
        bookAdapter = new BookAdapter(loadBooks());
        listView.setAdapter(bookAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}