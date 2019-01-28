package com.pstglia.controledegastos;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.pstglia.controledegastos.database.Database;

import java.io.FileDescriptor;


public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        ImageView btnIncluir = (ImageView) findViewById(R.id.imgNovaDespesaId);
        ImageView btnGerenciar = (ImageView) findViewById(R.id.imgAdmDespesaId);

        btnIncluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent chamar = new Intent(Principal.this,IncluirGerenciar.class);
                startActivity(chamar);

            }
        });

        btnGerenciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database d = new Database();
                final SQLiteDatabase pHandleDb = d.openDatabase(getDatabasePath(getString(R.string.rscNomeDatabase)).getAbsolutePath());
                d.queryDespesasDbg(pHandleDb);
                d.closeDatabase(pHandleDb);

                Intent chamar = new Intent(Principal.this,ListarDespesas.class);
                startActivity(chamar);

            }
        });


    }
}
