package cl.sgutierc.balance.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.data.Presupuesto;

/**
 * Created by sgutierc on 01-07-2016.
 */
public class PresupuestoControllerImp implements PresupuestoController {
    private final String DB_TABLE_PRESUPUESTO = "presupuesto";
    private final String DB_TABLE_CATEGORIA = "categoria";

    private final String DB_ID = "id";
    private final String DB_MONTH = "month";
    private final String DB_YEAR = "year";
    private final String DB_CATEGORIA_ID = "idCategoria";
    private final String DB_MONTO = "monto";

    private final String DB_CAT_DESCRIPCION = "descripcion";

    private SQLiteDatabase sqLiteDatabase;

    public PresupuestoControllerImp(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    public List<Presupuesto> getPresupuestos() {
        String query = String.format("select %s.%s,%s,%s,%s,%s from %s join %s on %s=%s.%s",
                                /*ARG*/DB_TABLE_PRESUPUESTO, DB_MONTH,DB_YEAR, DB_CATEGORIA_ID, DB_MONTO, DB_CAT_DESCRIPCION,
                                /*FROM*/DB_TABLE_PRESUPUESTO,/*JOIN*/ DB_TABLE_CATEGORIA, /*ON*/DB_CATEGORIA_ID, DB_TABLE_CATEGORIA, DB_ID);


        Log.d(this.getClass().getName(), "P Q: " + query);

        String selectionArgs[] = null;

        Cursor c = sqLiteDatabase.rawQuery(query, selectionArgs);
        List<Presupuesto> presupuestos = new ArrayList<>();

        while (c.moveToNext()) {
            long month=c.getLong(0);
            long year = c.getLong(1);
            long idCat = c.getLong(2);
            long monto = c.getLong(3);
            String descripcion = c.getString(4);

            Categoria categoria = new Categoria(idCat, descripcion);
            Presupuesto presupuesto = new Presupuesto(month,year, monto, categoria);

            presupuestos.add(presupuesto);
        }

        return presupuestos;
    }

    @Override
    public void insertPresupuesto(Presupuesto presupuesto) throws Exception {
        ContentValues values = new ContentValues();
        Categoria cat = presupuesto.getCategoria();

        int month= Calendar.getInstance().get(Calendar.MONTH);
        int year= Calendar.getInstance().get(Calendar.YEAR);

        values.put(DB_MONTH, month);
        values.put(DB_YEAR, year);
        values.put(DB_CATEGORIA_ID, cat.getId());
        values.put(DB_MONTO, presupuesto.getMonto());

        long result=sqLiteDatabase.insertWithOnConflict(DB_TABLE_PRESUPUESTO,null,values,SQLiteDatabase.CONFLICT_IGNORE);

        if(result==-1) {
            String where=String.format("%s=? and %s=? and %s=?",DB_MONTH,DB_YEAR,DB_CATEGORIA_ID);
            result=sqLiteDatabase.update(DB_TABLE_PRESUPUESTO, values, where,new String[]{String.valueOf(month),String.valueOf(year),String.valueOf(cat.getId())});
        }

        if(result==-1)throw new Exception("Error on insert new Presupuesto");
    }
}

