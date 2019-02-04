package com.pstglia.controledegastos;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.pstglia.controledegastos.database.Database;
import com.pstglia.controledegastos.util.ConfirmDialogExclusao;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Categorias extends AppCompatActivity implements ConfirmDialogExclusao.ConfirmDialogExclusaoListener {

    private EditText edtCatPrinc;
    private EditText edtCatSec;
    private Button btnInserir;
    private ListView lstCategorias;
    private ArrayList<String> arrCategorias;
    private ArrayAdapter<String> adaptador;
    private long vIdSelecionadoLista;
    Database db;
    SQLiteDatabase pHandleDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        edtCatPrinc = findViewById(R.id.edtCategoria);
        edtCatSec = findViewById(R.id.edtCatSec);
        btnInserir = (Button) findViewById(R.id.btnAdicionar);
        lstCategorias = findViewById(R.id.lstCategorias);

        db = new Database();
        pHandleDb = db.openDatabase(getDatabasePath(getString(R.string.rscNomeDatabase)).getAbsolutePath());
        arrCategorias = new ArrayList<>();

        arrCategorias = db.listaCategorias(pHandleDb);
        adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,arrCategorias);

        lstCategorias.setAdapter(adaptador);


        // Clique no botao salvar
        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vStrRet;
                String vIdCatPai;
                if (edtCatPrinc.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(),R.string.rscMsgInformarCat,Toast.LENGTH_LONG).show();
                    return;
                }
                if (edtCatSec.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(),R.string.rscMsgInformarCatSec,Toast.LENGTH_LONG).show();
                    return;
                }

                vStrRet = db.consultaCategoria(pHandleDb,edtCatPrinc.getText().toString(),edtCatSec.getText().toString());

                // Se o retorno forem 2 valores (separados por @) entao o valor ja esta cadastrado
                if (vStrRet.contains("@")) {
                    Toast.makeText(getApplicationContext(),R.string.rscMsgCatJaExiste,Toast.LENGTH_LONG).show();
                    return;
                } else {
                    vIdCatPai = vStrRet;
                }

                if (!db.insereCategoria(pHandleDb,edtCatPrinc.getText().toString(),edtCatSec.getText().toString(),vIdCatPai)) {
                    Toast.makeText(getApplicationContext(),R.string.rscMsgProblemaInserir,Toast.LENGTH_LONG).show();
                    return;
                } else {
                    arrCategorias.add(edtCatPrinc.getText().toString() + "-" + edtCatSec.getText().toString());
                    adaptador.notifyDataSetChanged();
                }

            }
        });

        lstCategorias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                ConfirmDialogExclusao dialogExclusao = new ConfirmDialogExclusao();

                dialogExclusao.show(getSupportFragmentManager(),"ConfirmaExcl");
                vIdSelecionadoLista = id;

                return true;
            }
        });

    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.i("CTRLGASTOSDBG","Valor selecionado: " + String.valueOf(vIdSelecionadoLista));
        String sValor = adaptador.getItem(Integer.valueOf(String.valueOf(vIdSelecionadoLista)));
        Log.i("CTRLGASTOSDBG","Valor selecionado: " + sValor);

        // Remove do banco
        String[] arr = sValor.split("-");
        db.removeCategoria(pHandleDb,arr[0],arr[1]);

        adaptador.remove(sValor);
        adaptador.notifyDataSetChanged();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    protected void onDestroy() {
        db.closeDatabase(pHandleDb);
        super.onDestroy();
    }
}
