package cl.sgutierc.balance.controller;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.data.Presupuesto;

/**
 * Created by sgutierc on 01-07-2016.
 */
public class PresupuestoControllerImp implements PresupuestoController {
    private final String DB_TABLE_PRESUPUESTO="presupuesto";
    private final String DB_TABLE_CATEGORIA="categoria";

    private final String DB_ID="id";
    private final String DB_CATEGORIA_ID="idCategoria";
    private final String DB_MONTO="monto";

    private final String DB_CAT_DESCRIPCION="descripcion";

    private SQLiteDatabase sqLiteDatabase;

    public PresupuestoControllerImp(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    public List<Presupuesto> getPresupuestos() {
        String query = String.format("select %s.%s,%s,%s,%s from %s join %s on %s=%s.%s",
                                /*ARG*/DB_TABLE_PRESUPUESTO, DB_ID, DB_CATEGORIA_ID, DB_MONTO, DB_CAT_DESCRIPCION,
                                /*FROM*/DB_TABLE_PRESUPUESTO,/*JOIN*/ DB_TABLE_CATEGORIA, /*ON*/DB_CATEGORIA_ID, DB_TABLE_CATEGORIA, DB_ID);


        Log.d(this.getClass().getName(), "P Q: " + query);

        String selectionArgs[] = null;

        Cursor c = sqLiteDatabase.rawQuery(query, selectionArgs);
         List<Presupuesto> presupuestos=new ArrayList<>();

        while(c.moveToNext()){
            long idPres = c.getLong(0);
            long idCat = c.getLong(1);
            long monto= c.getLong(2);
            String descripcion=c.getString(3);

            Categoria categoria=new Categoria(idCat,descripcion);
            Presupuesto presupuesto=new Presupuesto(idPres,monto,categoria);

            presupuestos.add(presupuesto);
        }

        return presupuestos;
    }
}
