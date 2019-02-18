package com.pstglia.controledegastos.database;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.StringRes;
import android.util.Log;

import com.pstglia.controledegastos.R;

import java.io.File;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


// Sim, eu deveria usar um Helper mas sou um dinossauro...
// Yes, I should use a Helper, but I'm a dinossaur...
public class Database {

    // Campos das tabelas
    private Integer id_categoria;
    private String dt_lancamento;
    private Float vl_despesa;
    private Float vl_custom_1;
    private Float vl_custom_2;
    private Float vl_custom_3;

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

    public Float getVl_custom_1() {
        return vl_custom_1;
    }

    public void setVl_custom_1(Float vl_custom_1) {
        this.vl_custom_1 = vl_custom_1;
    }

    public Float getVl_custom_2() {
        return vl_custom_2;
    }

    public void setVl_custom_2(Float vl_custom_2) {
        this.vl_custom_2 = vl_custom_2;
    }

    public Float getVl_custom_3() {
        return vl_custom_3;
    }

    public void setVl_custom_3(Float vl_custom_3) {
        this.vl_custom_3 = vl_custom_3;
    }


    private SQLiteDatabase vBancoHandle;

    // Abre um banco existente ou cria
    // Opens an existing database or creates it
    public SQLiteDatabase openDatabase(String pDatabase) {


        try {
            Log.i("CTRLGASTOSDBG","Banco de dados: " + pDatabase);

            File f = new File(pDatabase);
            f.getParentFile().mkdir();

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


        Cursor vCursor;
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

        try {
            vCursor = pHandle.rawQuery(vCmd,null);
        }
        catch (Exception E) {
            Log.i("CTRLGASTOSERR","Problemas na execucao da query " + E.getMessage());
            return null;
        }

        return vCursor;

    }

    /**
     * Retorna um ArrayList com as categorias pais e filhas separadas por hifen
     * @param pHandle
     * @return
     */
    public ArrayList<String> listaCategorias(SQLiteDatabase pHandle) {

        Cursor vCursor;
        ArrayList<String> vCategoriasArr = new ArrayList<String>();

        String vCmd;
        vCmd = "select pai.ds_categoria, filha.ds_categoria ";
        vCmd = vCmd + " from categoria pai, categoria filha  ";
        vCmd = vCmd + " where filha.id_categoria_pai = pai.id_categoria";

        try {
            vCursor = pHandle.rawQuery(vCmd,null);
        }
        catch (Exception E) {
            Log.i("CTRLGASTOSERR","Problemas na execucao da query " + E.getMessage());
            return null;
        }

        if (vCursor.moveToFirst() ) {
            do {
                vCategoriasArr.add(vCursor.getString(0) + "-" + vCursor.getString(1));
                            }
            while (vCursor.moveToNext());
        }

        return vCategoriasArr;

    }

    // Registra uma despesa no banco de dados
    // Parametros:
    // 1 - Handle de conexao com o banco
    // Retorno:
    // Boolean (True se ok)
    public Boolean insereDespesa(SQLiteDatabase pHandle) {


        String vCmd;
        vCmd = "insert into despesa (dt_lancamento, id_categoria, vl_despesa, vl_custom_1, ";
        vCmd = vCmd + "vl_custom_2, vl_custom_3) values ( ";
        vCmd = vCmd + "'" + getDt_lancamento() + "'," + getId_categoria() + "," ;
        vCmd = vCmd + getVl_despesa() + "," + getVl_custom_1()  + "," + getVl_custom_2();
        vCmd = vCmd + "," + getVl_custom_3() + ")";

        try {
            pHandle.execSQL(vCmd);
        } catch (Exception e) {
            Log.e("CTRLGASTOSERR",e.getMessage());
            return false;
        }

        return true;

    }

    public Boolean removeDespesa(SQLiteDatabase pHandle, String pSeqDespesa) {


        String vCmd;
        vCmd = "delete from  despesa where seq_despesa = " + pSeqDespesa;

        try {
            pHandle.execSQL(vCmd);
        } catch (Exception e) {
            Log.e("CTRLGASTOSERR",e.getMessage());
            return false;
        }

        return true;

    }


    // Relaciona a quantidade de campos adicionais de uma categoria
    // Parametros:
    // 1 - Handle de conexao com o banco
    // 2 - Id da Categoria
    // Retorno:
    // String com a a qtde de campos e a lista de descrições (separados por @)
    public String listaCamposAdicionaisCat(SQLiteDatabase pHandle, int pIdCategoria) {


        String vCmd;
        String vStrRet;

        vStrRet="";
        vCmd = "select qt_vl_custom, ds_custom_1, ds_custom_2, ds_custom_3 from categoria where id_categoria = " + pIdCategoria;
        Cursor c = pHandle.rawQuery(vCmd,null);

        if (c.moveToFirst()) {
            vStrRet = c.getString(0) + "@" + c.getString(1)+ "@" + c.getString(2)+ "@" + c.getString(3) ;
        }

        c.close();
        return vStrRet;

    }

    /**
     * Verifica se uma categoria existe e retorna seus ids (caso existam)
     * @param pHandle
     * @param pCatPai
     * @param pCatSec
     * @return
     */
    public String consultaCategoria(SQLiteDatabase pHandle, String pCatPai, String pCatSec) {


        String vCmd;
        String vStrRet;
        String vIdCatPai;

        vStrRet="";

        // Consulta a categoria principal
        vCmd = "select id_categoria from categoria where ds_categoria = '" +pCatPai+"'";
        Cursor c = pHandle.rawQuery(vCmd,null);

        if (c.moveToFirst()) {
            vStrRet = c.getString(0);
            vIdCatPai = vStrRet;
        } else {
            return "";
        }

        c.close();


        // Consulta a categoria secundaria
        vCmd = "select id_categoria from categoria ";
        vCmd = vCmd + " where ds_categoria = '" +pCatSec+"' and id_categoria_pai = " + vIdCatPai;
        c = pHandle.rawQuery(vCmd,null);

        if (c.moveToFirst()) {
            vStrRet = vStrRet + "@" + c.getString(0);

        }

        return vStrRet;

    }

    /**
     * Retorna uma lista de despesas na forma de um ArrayList<String>
     * @param pHandle
     * @return
     */
    public void obtemListaDespesas(SQLiteDatabase pHandle, String pDataDe, String pDataAte,ArrayList<String[]> pArr) {


        String vCmd;
        String vStrDbg;
        int vContRegistro = 0;
        vCmd = "select d.seq_despesa as _id, cat_pai.ds_categoria, cat_filha.ds_categoria, d.dt_lancamento, d.vl_despesa ";
        vCmd = vCmd + " from despesa d, categoria cat_filha, categoria cat_pai ";
        vCmd = vCmd + " where d.id_categoria = cat_filha.id_categoria ";
        vCmd = vCmd + " and cat_filha.id_categoria_pai = cat_pai.id_categoria ";

        vCmd = vCmd + " and d.dt_lancamento >= '" + pDataDe + "'";
        vCmd = vCmd + " and d.dt_lancamento <= '" + pDataAte + "'";

        vCmd = vCmd + " order by d.dt_lancamento desc";

        Log.i("CTRLGASTOSDBG",vCmd);
        Cursor c = pHandle.rawQuery(vCmd,null);
        ArrayList<String[]> arrListaResult = pArr;
        arrListaResult.clear();


        //arrListaResult = new ArrayList<String[]>();
        String[][] arrListaResultRegistro = new String[c.getCount() + 1][5];

        // Imprime os cabecalhos
        // Print headers
        /*arrListaResultRegistro[0][0] = pCabecalhos[0];
        arrListaResultRegistro[0][1] = pCabecalhos[1];
        arrListaResultRegistro[0][2] = pCabecalhos[2];
        arrListaResultRegistro[0][3] = pCabecalhos[3];
        arrListaResultRegistro[0][4] = pCabecalhos[4];

        arrListaResult.add(arrListaResultRegistro[0]);*/

        if (c.moveToFirst()) {

            do {

                arrListaResultRegistro[vContRegistro][0] = c.getString(0);
                arrListaResultRegistro[vContRegistro][1] = c.getString(1);
                arrListaResultRegistro[vContRegistro][2] = c.getString(2);
                arrListaResultRegistro[vContRegistro][3] = c.getString(3);
                arrListaResultRegistro[vContRegistro][4] = c.getString(4);
                //arrListaResultRegistro.clear();
                arrListaResult.add(arrListaResultRegistro[vContRegistro]);
                vContRegistro = vContRegistro + 1;

            } while (c.moveToNext());

        }

        c.close();

        Log.i("CTRLGASTOSDGB","Retorno despesas: " + arrListaResult.size());
        //return arrListaResult;

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


    /**
     * Registra uma categoria pai/fila no banco
     * @param pHandle
     * @param pCatPai
     * @param pCatFilha
     * @param pIdCatPai
     * @return
     */
    public Boolean insereCategoria(SQLiteDatabase pHandle, String pCatPai, String pCatFilha, String pIdCatPai) {


        String vCmd;

        if (pIdCatPai.length() == 0 || pIdCatPai == "0") {
            vCmd = "insert into categoria (ds_categoria,qt_vl_custom, id_categoria_pai) ";
            vCmd = vCmd + "values ( ";
            vCmd = vCmd + "'" + pCatPai + "',0,null)";


            try {
                pHandle.execSQL(vCmd);

                // Obtem o id da categoria inserida
                vCmd = "select id_categoria from categoria where ds_categoria = '" + pCatPai + "'";

                Cursor c = pHandle.rawQuery(vCmd, null);

                if (!c.moveToFirst()) {
                    return false;
                } else {
                    pIdCatPai = c.getString(0);
                }

            } catch (Exception e) {
                Log.e("CTRLGASTOSERR", e.getMessage());
                return false;
            }
        }

        vCmd = "insert into categoria (ds_categoria,qt_vl_custom, id_categoria_pai) ";
        vCmd = vCmd + "values ( ";
        vCmd = vCmd + "'" + pCatFilha + "',0," + pIdCatPai + ")";

        try {
            pHandle.execSQL(vCmd);
        } catch (Exception e) {
            Log.e("CTRLGASTOSERR",e.getMessage());
            return false;
        }


        return true;

    }

    /**
     * Remove uma categoria pela string da categoria pai e filha
     * @param pHandle
     * @param pCatPai
     * @param pCatFilha
     * @return
     */
    public Boolean removeCategoria(SQLiteDatabase pHandle, String pCatPai, String pCatFilha) {


        String vCmd;
        String vIdCatPai;

        try {

            vCmd = "select id_categoria_pai from categoria where ds_categoria = '" + pCatFilha + "'";

            Cursor c = pHandle.rawQuery(vCmd, null);

            if (!c.moveToFirst()) {
                return false;
            } else {
                vIdCatPai = c.getString(0);
            }

        } catch (Exception e) {
            Log.e("CTRLGASTOSERR", e.getMessage());
            return false;
        }


        vCmd = "delete from  categoria where ds_categoria = '"+pCatFilha+"' and id_categoria_pai = " + vIdCatPai ;

        try {
            pHandle.execSQL(vCmd);
        } catch (Exception e) {
            Log.e("CTRLGASTOSERR", e.getMessage());
            return false;
        }

        // Caso nenhuma categoria filha tenha sobrado, exclui a pai
        vCmd = "select * from categoria where id_categoria_pai = " + vIdCatPai ;

        Cursor c = pHandle.rawQuery(vCmd, null);

        if (!c.moveToFirst()) {
            vCmd = "delete from  categoria where id_categoria = " + vIdCatPai ;

            try {
                pHandle.execSQL(vCmd);
            } catch (Exception e) {
                Log.e("CTRLGASTOSERR", e.getMessage());
                return false;
            }

        }

        return true;
    }

}
