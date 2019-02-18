package com.pstglia.controledegastos;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.pstglia.controledegastos.database.Database;
import com.pstglia.controledegastos.interfaces.CliqueBotaoRecycler;
import com.pstglia.controledegastos.util.ConfirmDialogExclusao;
import com.pstglia.controledegastos.util.DataDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 *  Referências / references:
 *  https://medium.com/pen-bold-kiln-press/tablelayout-vs-gridlayout-ae28be87b4b6
 *  https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the
 *
 */
public class ListarDespesas extends AppCompatActivity implements CliqueBotaoRecycler , ConfirmDialogExclusao.ConfirmDialogExclusaoListener, DatePickerDialog.OnDateSetListener {

    private RecyclerView recycler;
    private TextView txtSoma;
    private Spinner cmbCatPrinc;
    private EditText edtDataDe;
    private EditText edtDataAte;

    private DataDialog newFragmentDe;
    private DataDialog newFragmentAte;
    private Calendar dataSelecionadaDe;
    private Calendar dataSelecionadaAte;

    private Cursor vCursor;



    private RecyclerView.LayoutManager manager;
    private AdaptadorTabResult adapter;
    private ArrayList<String[]> list=new ArrayList<String[]>();
    private Database db;
    private String vIdSelecionadoLista;
    private int posicaoSelecionadaLista = -1;

    // Indica o campo de data que foi clicado
    // (1 campo De ; 2 campo Ate)
    int nCampoSelecionado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_despesas);


        recycler = findViewById(R.id.recyclerView);
        recycler.setHasFixedSize(true);
        cmbCatPrinc = findViewById(R.id.cmbCatPrincFiltro);
        edtDataDe = findViewById(R.id.edtDataDe);
        edtDataAte = findViewById(R.id.edtDataAte);
        txtSoma = findViewById(R.id.txtSoma);


        // Instancia a caixa de dialogo para o campo De
        newFragmentDe = new DataDialog();
        //newFragmentDe.setEditTextParam(edtDataDe);

        dataSelecionadaDe = Calendar.getInstance();
        dataSelecionadaDe.add(Calendar.DAY_OF_MONTH,-7);
        //newFragmentDe.setDataSelecionada(dataSelecionadaDe);

        //final Calendar c = Calendar.getInstance();
        edtDataDe.setText(DateUtils.formatDateTime(this, dataSelecionadaDe.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));

        // Instancia a caixa de dialogo para o campo Ate
        newFragmentAte = new DataDialog();
        //newFragmentAte.setEditTextParam(edtDataAte);

        dataSelecionadaAte = Calendar.getInstance();
        //newFragmentAte.setDataSelecionada(dataSelecionadaAte);

        //final Calendar c2 = Calendar.getInstance();
        edtDataAte.setText(DateUtils.formatDateTime(this, dataSelecionadaAte.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));




        // Popula o spinner de categorias principais
        // Populate the main category spinner
        db = new Database();
        String[] vCabecalhosArr = new String[5];

        final SQLiteDatabase pHandleDb = db.openDatabase(getDatabasePath(getString(R.string.rscNomeDatabase)).getAbsolutePath());
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

        db.closeDatabase(pHandleDb);


        //manager = new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
        manager = new LinearLayoutManager(this);

        recycler.setLayoutManager(manager);
        populaDespesas();    //initialize your list in this method
        adapter = new AdaptadorTabResult(list,this,this);

        recycler.setAdapter(adapter);

        somaDespesasListadas();


    }

    // TODO: Filtrar tambem por categoria
    public void populaDespesas() {

        db = new Database();
        String[] vCabecalhosArr = new String[5];

        final SQLiteDatabase pHandleDb = db.openDatabase(getDatabasePath(getString(R.string.rscNomeDatabase)).getAbsolutePath());

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String sDataDe = fmt.format(dataSelecionadaDe.getTime());
        String sDataAte = fmt.format(dataSelecionadaAte.getTime());

        db.obtemListaDespesas(pHandleDb, sDataDe, sDataAte,list);

        db.closeDatabase(pHandleDb);

    }

    public void somaDespesasListadas() {

       double vSoma = 0;

       for ( String[] x : list) {

           try {
               vSoma += Double.parseDouble( x[4] );
           } catch (Exception e) {
               Log.i("CTRLGASTOSDBG","Valor invalido: " + x[4]);
           }

       }

       txtSoma.setText( String.valueOf(vSoma) );
    }

    @Override
    public void botaoClicado(View v, String pValor, int posicao) {
        Log.i("CTRLGASTOSDBG","MAE, CLIQUEI NO VALOR "+ pValor);
        ConfirmDialogExclusao dialogExclusao = new ConfirmDialogExclusao();

        dialogExclusao.show(getSupportFragmentManager(),"ConfirmaExcl");
        vIdSelecionadoLista = pValor;
        posicaoSelecionadaLista = posicao;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
      if (vIdSelecionadoLista != null && posicaoSelecionadaLista != -1) {
          final SQLiteDatabase pHandleDb = db.openDatabase(getDatabasePath(getString(R.string.rscNomeDatabase)).getAbsolutePath());
          if (db.removeDespesa(pHandleDb,vIdSelecionadoLista)) {
              adapter.remove(posicaoSelecionadaLista);
              adapter.notifyDataSetChanged();
              somaDespesasListadas();
          }
      }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }


    public void showDatePickerDialog(View v) {

        if ( v.getId() == R.id.edtDataDe ) {
            newFragmentDe.show(getSupportFragmentManager(), "datePicker");
            nCampoSelecionado = 1;
        } else {
            newFragmentAte.show(getSupportFragmentManager(), "datePicker");
            nCampoSelecionado = 2;
        }



    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        Calendar dataSelecionada = Calendar.getInstance();
        dataSelecionada.set(year,month,dayOfMonth);



        if (nCampoSelecionado == 1) {

            edtDataDe.setText(DateUtils.formatDateTime(getApplicationContext(), dataSelecionada.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
            dataSelecionadaDe = dataSelecionada;
        } else {
            edtDataAte.setText(DateUtils.formatDateTime(getApplicationContext(), dataSelecionada.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
            dataSelecionadaAte = dataSelecionada;
        }

        populaDespesas();
        adapter.notifyDataSetChanged();
        somaDespesasListadas();
    }
}
