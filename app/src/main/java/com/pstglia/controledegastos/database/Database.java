package com.pstglia.controledegastos.database;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


// Sim, eu deveria usar um Helper mas sou um dinossauro...
// Yes, I should use a Helper, but I'm a dinossaur...
public class Database {

    // Campos das tabelas
    private Integer id_categoria;
    private String dt_lancamento;
    private Float vl_despesa;

    // getters e settlers
    // (o mundo era um lugar mais feliz quando tudo era global...)
    // (World was a happier place when everything was global...)
    public Integer getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getDt_lancamento() {
        return dt_lancamento;
    }

    public void setDt_lancamento(String dt_lancamento) {
        this.dt_lancamento = dt_lancamento;
    }

    public Float getVl_despesa() {
        return vl_despesa;
    }

    public void setVl_despesa(Float vl_despesa) {
        this.vl_despesa = vl_despesa;
    }

    private SQLiteDatabase vBancoHandle;

    // Abre um banco existente ou cria
    // Opens an existing database or creates it
    public SQLiteDatabase openDatabase(String pDatabase) {


        try {
            vBancoHandle = SQLiteDatabase.openOrCreateDatabase(pDatabase,null);

            criaTabelas(vBancoHandle);
        }
        catch (Exception e) {
            Log.e("CTRLGASTOSERR",e.getMessage());
            return null;
        }

        return vBancoHandle;

    }

    // Encerra uma conexao com o banco
    // Parametros:
    // 1 - Handle de conexao com o banco
    // Retorno:
    // Boolean (true se ok; false caso contrario)
    public Boolean closeDatabase(SQLiteDatabase pHandle) {


        try {
            pHandle.close();
        }
        catch (Exception e) {
            Log.e("CTRLGASTOSERR",e.getMessage());
            return false;
        }

        return true;

    }


    // Cria as tabelas necessaria ao app
    // (caso nao existam)
    // Create necessary database tables
    // (case they do not exists)
    private Boolean criaTabelas(SQLiteDatabase pHandle) {

        String vCmd;

        Cursor vCursor;
        int vQtde;

        vCursor = pHandle.rawQuery("select count(*) from sqlite_master where tbl_name = 'categoria'",null);

        vCursor.moveToFirst();

        vQtde = vCursor.getInt(0);

        Log.i("CTRLGASTOSINFO","qtde ret categoria: " + vQtde);

        if (vQtde == 0) {

            // Cria a tabela de categorias
            // A categoria define tambem os campos
            // especificos (ex: a categoria ABASTECIMENTO
            // pode ter um campo customizado com a quantidade
            // de litros abastecidos)

            // Create category table
            // Category defines also specific fields
            // (ex: A FUEL category can have a custom
            // field with amount of fuel (Liter/gallon)
            vCmd = "CREATE TABLE IF NOT EXISTS categoria (";
            vCmd = vCmd + "id_categoria INTEGER PRIMARY KEY, ";
            vCmd = vCmd + "ds_categoria TEXT, ";
            vCmd = vCmd + "id_categoria_pai INTEGER, ";
            vCmd = vCmd + "qt_vl_custom INTEGER, ";
            vCmd = vCmd + "ds_custom_1 TEXT, ";
            vCmd = vCmd + "ds_custom_2 TEXT, ";
            vCmd = vCmd + "ds_custom_3 TEXT ";
            vCmd = vCmd + ") ";

            try {
                vBancoHandle.execSQL(vCmd);

                // Insere algumas categorias
                // Insert some categories...
                // TODO: Criar um menu para gerenciar isso
                // TODO: Create a menu entry to do this
                vCmd = "insert into categoria (id_categoria, ds_categoria, id_categoria_pai,";
                vCmd = vCmd + " qt_vl_custom,ds_custom_1) values ( ";
                vCmd = vCmd + " 1, 'CARRO',NULL, 0, NULL); ";
                pHandle.execSQL(vCmd);

                vCmd = "insert into categoria (id_categoria, ds_categoria, id_categoria_pai,";
                vCmd = vCmd + " qt_vl_custom,ds_custom_1) values ( ";
                vCmd = vCmd + " 2, 'COMBUSTIVEL',1, 1, 'LITROS'); ";
                pHandle.execSQL(vCmd);

                vCmd = "insert into categoria (id_categoria, ds_categoria, id_categoria_pai,";
                vCmd = vCmd + " qt_vl_custom,ds_custom_1) values ( ";
                vCmd = vCmd + " 3, 'MANUTENÇÃO',1, 0, NULL); ";
                pHandle.execSQL(vCmd);



                vCmd = "insert into categoria (id_categoria, ds_categoria, id_categoria_pai,";
                vCmd = vCmd + " qt_vl_custom,ds_custom_1) values ( ";
                vCmd = vCmd + " 4, 'CASA',NULL, 0, NULL); ";
                pHandle.execSQL(vCmd);

                vCmd = "insert into categoria (id_categoria, ds_categoria, id_categoria_pai,";
                vCmd = vCmd + " qt_vl_custom,ds_custom_1) values ( ";
                vCmd = vCmd + " 5, 'CONDOMÍNIO',4, 0, NULL); ";
                pHandle.execSQL(vCmd);

                vCmd = "insert into categoria (id_categoria, ds_categoria, id_categoria_pai,";
                vCmd = vCmd + " qt_vl_custom,ds_custom_1) values ( ";
                vCmd = vCmd + " 6, 'ENERGIA',4, 0, NULL); ";
                pHandle.execSQL(vCmd);

                vCmd = "insert into categoria (id_categoria, ds_categoria, id_categoria_pai,";
                vCmd = vCmd + " qt_vl_custom,ds_custom_1) values ( ";
                vCmd = vCmd + " 7, 'INTERNET',4, 0, NULL); ";
                pHandle.execSQL(vCmd);

                vCmd = "insert into categoria (id_categoria, ds_categoria, id_categoria_pai,";
                vCmd = vCmd + " qt_vl_custom,ds_custom_1) values ( ";
                vCmd = vCmd + " 8, 'GÁS',4, 0, NULL); ";
                pHandle.execSQL(vCmd);


            } catch (Exception e) {
                Log.e("CTRLGASTOSERR", e.getMessage());
                return null;
            }

        }


        vCursor = pHandle.rawQuery("select count(*) from sqlite_master where tbl_name = 'despesa'",null);

        vCursor.moveToFirst();

        vQtde = vCursor.getInt(0);

        if (vQtde == 0) {

            // Cria a tabela de despesas
            // seq_despesa assume os valores do rowid

            // Create  expenses table
            // seq_despesa field assumes rowid ("sequence")
            vCmd = "CREATE TABLE IF NOT EXISTS despesa (";
            vCmd = vCmd + "seq_despesa INTEGER PRIMARY KEY, ";
            vCmd = vCmd + "dt_lancamento TEXT, ";
            vCmd = vCmd + "id_categoria INTEGER, ";
            vCmd = vCmd + "vl_despesa REAL, ";
            vCmd = vCmd + "vl_custom_1 REAL, ";
            vCmd = vCmd + "vl_custom_2 REAL, ";
            vCmd = vCmd + "vl_custom_3 REAL, ";
            vCmd = vCmd + "ds_obs TEXT ";
            vCmd = vCmd + ") ";

            try {
                pHandle.execSQL(vCmd);
            } catch (Exception e) {
                Log.e("CTRLGASTOSERR", e.getMessage());
                return null;
            }

        }


        return true;
    }

    // Obtem a lista de categorias
    // Parametros:
    // 1 - Handle de conexao com o banco
    // 2 - Id Categoria pai (0 = Apenas categorias pai)
    // Retorno:
    // Cursor com a lista de itens

    // Get category list
    // Params:
    // 1 - Database handle
    // 2 - Parent category (0 = Lists justs parent categories)
    // Return:
    // Item results cursor
    public Cursor obtemCategorias(SQLiteDatabase pHandle, int pIdCatPai) {


        ArrayList<String> vCategoriasArr = new ArrayList<String>();

        String vCmd;
        vCmd = "select ds_categoria, id_categoria as _id from categoria where ";

        if (pIdCatPai == 0) {
            vCmd = vCmd + " id_categoria_pai is null";
        }
        else {
            vCmd = vCmd + " id_categoria_pai = " + pIdCatPai;
        }
        vCmd = vCmd + " order by ds_categoria";


        Cursor vCursor = pHandle.rawQuery(vCmd,null);

        return vCursor;

    }

    // Registra uma despesa no banco de dados
    // Parametros:
    // 1 - Handle de conexao com o banco
    // Retorno:
    // Boolean (True se ok)
    public Boolean insereDespesa(SQLiteDatabase pHandle) {


        String vCmd;
        vCmd = "insert into despesa (dt_lancamento, id_categoria, vl_despesa) values ( ";
        vCmd = vCmd + "'" + getDt_lancamento() + "'," + getId_categoria() + "," ;
        vCmd = vCmd + getVl_despesa() + ")";

        try {
            pHandle.execSQL(vCmd);
        } catch (Exception e) {
            Log.e("CTRLGASTOSERR",e.getMessage());
            return false;
        }

        return true;

    }

    public void queryDespesasDbg(SQLiteDatabase pHandle) {


        String vCmd;
        String vStrDbg;
        vCmd = "select * from despesa";
        Cursor c = pHandle.rawQuery(vCmd,null);

        if (c.moveToFirst()) {

            do {
                vStrDbg = c.getString(0) + "#" + c.getString(1);
                vStrDbg = vStrDbg +  "#" + c.getString(2) + "#" + c.getString(3);
                Log.d("CTRLGASTOSDBG",vStrDbg);

            } while (c.moveToNext());

        }

        c.close();

    }


}
