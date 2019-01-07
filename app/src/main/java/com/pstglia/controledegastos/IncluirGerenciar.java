package com.pstglia.controledegastos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pstglia.controledegastos.database.Database;

import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class IncluirGerenciar extends AppCompatActivity {

    private TextView txtDataId;
    private EditText edtDataId;
    private Spinner cmbCatPrinc;
    private Spinner cmbCatSec;
    private EditText edtValorGasto;
    private ImageView imgSalvar;
    private ImageView imgCancelar;
    private Database db;
    private TextView txtCustom1;
    private TextView txtCustom2;
    private TextView txtCustom3;
    private EditText edtCustom1;
    private EditText edtCustom2;
    private EditText edtCustom3;

    private Cursor vCursor2;

    private DataDialog newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_gerenciar);
        Cursor vCursor;

        db = new Database();
        final SQLiteDatabase pHandleDb = db.openDatabase(getDatabasePath(getString(R.string.rscNomeDatabase)).getAbsolutePath());


        // Mapeia elementos do activity (nesse ponto parece que voltei a usar o PHP-GTK :D )
        // Maps Activity elements (Seems at this point I returned using PHP-GTK :D )
        txtDataId = (TextView) findViewById(R.id.txtDataId);
        edtDataId = (EditText) findViewById(R.id.edtDataId);
        cmbCatPrinc = (Spinner) findViewById(R.id.cmbCatPrinc);
        cmbCatSec = (Spinner) findViewById(R.id.cmbCatSec);
        edtValorGasto = (EditText) findViewById(R.id.edtValorGasto);
        imgSalvar = (ImageView) findViewById(R.id.imgSalvar);
        imgCancelar = (ImageView) findViewById(R.id.imgCancelar);
        txtCustom1 = (TextView) findViewById(R.id.txtCustom1);
        txtCustom2 = (TextView) findViewById(R.id.txtCustom2);
        txtCustom3 = (TextView) findViewById(R.id.txtCustom3);
        edtCustom1 = (EditText) findViewById(R.id.edtCustom1);
        edtCustom2 = (EditText) findViewById(R.id.edtCustom2);
        edtCustom3 = (EditText) findViewById(R.id.edtCustom3);


        // Instancia a caixa de dialogo
        newFragment = new DataDialog();
        newFragment.setEditTextParam(edtDataId);

        final Calendar c = Calendar.getInstance();
        edtDataId.setText(c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH + 1) + "/" + c.get(Calendar.YEAR));

        // Popula o spinner de categorias principais
        // Populate the main category spinner
        vCursor = db.obtemCategorias(pHandleDb, 0);
        SimpleCursorAdapter adapter1 = new SimpleCursorAdapter(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                vCursor,
                new String[] {"ds_categoria","_id"},
                new int[]{android.R.id.text1},
                0);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbCatPrinc.setAdapter(adapter1);

        // LISTENERS

        // Define um listener para a seleção de uma categoria secundária
        // Defines a listener to populate a secondary category
        cmbCatPrinc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("CTRLGASTOSDBG",String.valueOf(id));
                vCursor2 = db.obtemCategorias(pHandleDb, Integer.valueOf(String.valueOf(id)));

                SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(
                        getApplicationContext(),
                        android.R.layout.simple_spinner_item,
                        vCursor2,
                        new String[] {"ds_categoria","_id"},
                        new int[]{android.R.id.text1},
                        0);

                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                cmbCatSec.setAdapter(adapter2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cmbCatSec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Verifica se os campos customizados devem ser exibidos
                //Check if custom fields shall be shown
                String vRet = db.listaCamposAdicionaisCat(pHandleDb, Integer.valueOf(String.valueOf(id)));
                txtCustom1.setVisibility(View.INVISIBLE);
                txtCustom2.setVisibility(View.INVISIBLE);
                txtCustom3.setVisibility(View.INVISIBLE);
                edtCustom1.setVisibility(View.INVISIBLE);
                edtCustom2.setVisibility(View.INVISIBLE);
                edtCustom3.setVisibility(View.INVISIBLE);


                String[] vRetArr = vRet.split("@");
                int vQtdCampos = Integer.valueOf(vRetArr[0]);

                if (vQtdCampos> 0 ) {
                    txtCustom1.setVisibility(View.VISIBLE);
                    edtCustom1.setVisibility(View.VISIBLE);
                    txtCustom1.setText(vRetArr[1] );

                    if (vQtdCampos > 1) {
                        txtCustom2.setVisibility(View.VISIBLE);
                        edtCustom2.setVisibility(View.VISIBLE);
                        txtCustom2.setText(vRetArr[2] );
                    }
                    if (vQtdCampos > 2) {
                        txtCustom3.setVisibility(View.VISIBLE);
                        edtCustom3.setVisibility(View.VISIBLE);
                        txtCustom3.setText(vRetArr[3] );
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgSalvar.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {

                                             // Valida os campos
                                             if (edtDataId.getText().toString().isEmpty()) {
                                                 Toast.makeText(getApplicationContext() ,R.string.rscMsgValidaData,Toast.LENGTH_SHORT).show();
                                                 return;
                                             }

                                             if (edtValorGasto.getText().toString().isEmpty()) {
                                                 Toast.makeText(getApplicationContext() ,R.string.rscMsgValidaValorGasto,Toast.LENGTH_SHORT).show();
                                                 return;
                                             }

                                             db.setDt_lancamento(edtDataId.getText().toString());
                                             db.setId_categoria(Integer.valueOf(String.valueOf(cmbCatSec.getSelectedItemId())));
                                             db.setVl_despesa(Float.valueOf(edtValorGasto.getText().toString()));

                                             if (!edtCustom1.getText().toString().isEmpty()) {
                                                 db.setVl_custom_1(Float.valueOf(edtCustom1.getText().toString()));
                                             }
                                             if (!edtCustom2.getText().toString().isEmpty()) {
                                                 db.setVl_custom_2(Float.valueOf(edtCustom2.getText().toString()));
                                             }
                                             if (!edtCustom3.getText().toString().isEmpty()) {
                                                 db.setVl_custom_3(Float.valueOf(edtCustom3.getText().toString()));
                                             }

                                             if ( !db.insereDespesa(pHandleDb)) {

                                                 String msgStr = getText(R.string.rscMsgProblemaInserir).toString();
                                                 Toast.makeText(getApplicationContext(),msgStr,Toast.LENGTH_LONG).show();
                                                 return;
                                             }
                                             db.closeDatabase(pHandleDb);
                                             finish();

                                         }
                                     }
        );

        imgCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    public void showDatePickerDialog(View v) {
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

}

