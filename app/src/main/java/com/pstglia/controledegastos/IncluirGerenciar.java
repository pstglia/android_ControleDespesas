package com.pstglia.controledegastos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class IncluirGerenciar extends AppCompatActivity {

    private TextView txtDataId;
    private EditText edtDataId;

    private DataDialog newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incluir_gerenciar);

        // Mapeia elementos do activity
        txtDataId = (TextView) findViewById(R.id.txtDataId);
        edtDataId = (EditText) findViewById(R.id.edtDataId);

        // Instancia a caixa de dialogo
        newFragment = new DataDialog();
        newFragment.setEditTextParam(edtDataId);

    }


    public void showDatePickerDialog(View v) {
        newFragment.show(getSupportFragmentManager(), "datePicker");
        Log.i("POFFO", "showDatePickerDialog - Aguardando valor...");



    }

}

