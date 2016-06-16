package cl.sgutierc.balance.controller;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cl.sgutierc.balance.data.Gasto;

/**
 * Created by sgutierc on 14-06-2016.
 */
public class GastoControllerImp implements GastoController {
    private final String DB_CATEGORIA="idCategoria";
    private final String DB_MONTO="monto";
    private final String DB_FECHA="fecha";
    private final SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
    private final String TABLE_NAME="gasto";

    private SQLiteDatabase sqLiteDatabase;

    public GastoControllerImp(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public void insertGasto(Gasto gasto) throws Exception{
        ContentValues values=new ContentValues();
        values.put(DB_CATEGORIA,gasto.getCategoria().getId());
        values.put(DB_MONTO,gasto.getMonto());
        values.put(DB_FECHA,sdf.format(gasto.getFecha()));

        long result=sqLiteDatabase.insert(TABLE_NAME,null,values);

        if(result==-1)throw new Exception("Error on insert new gasto");
    }

    public List<Gasto> getGastos() {
        List<Gasto> gastos=new ArrayList<>();
        {


        }

        return gastos;
    }

}
