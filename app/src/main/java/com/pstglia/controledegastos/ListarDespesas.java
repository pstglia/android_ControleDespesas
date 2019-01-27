package com.pstglia.controledegastos;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;

import com.pstglia.controledegastos.database.Database;

import java.util.ArrayList;


/**
 *  Referências / references:
 *  https://medium.com/pen-bold-kiln-press/tablelayout-vs-gridlayout-ae28be87b4b6
 *  https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the
 *
 */
public class ListarDespesas extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.LayoutManager manager;
    private AdaptadorTabResult adapter;
    private ArrayList<String> list=new ArrayList<>();
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_despesas);


        recycler = findViewById(R.id.recyclerView);
        recycler.setHasFixedSize(true);
        manager = new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(manager);
        populaDespesas();    //initialize your list in this method
        adapter = new AdaptadorTabResult(list,this);

        recycler.setAdapter(adapter);

    }


    public void populaDespesas() {

        db = new Database();
        final SQLiteDatabase pHandleDb = db.openDatabase(getDatabasePath(getString(R.string.rscNomeDatabase)).getAbsolutePath());

        list = db.obtemListaDespesas(pHandleDb);

        db.closeDatabase(pHandleDb);

    }
}
