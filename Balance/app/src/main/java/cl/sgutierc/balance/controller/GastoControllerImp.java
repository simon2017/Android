package cl.sgutierc.balance.controller;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.sgutierc.balance.data.Categoria;
import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.database.CAT_TABLE;
import cl.sgutierc.balance.database.GASTO_TABLE;
import cl.sgutierc.balance.dispatcher.DataDispatcher;
import cl.sgutierc.balance.dispatcher.RepositoryChannel;
import lib.data.StringID;
import lib.data.dispatcher.ClassInterest;
import lib.data.dispatcher.Listener;
import lib.data.dispatcher.LongId;
import lib.data.lib.data.handler.DataAction;

/**
 * Created by sgutierc on 14-06-2016.
 */
public class GastoControllerImp implements GastoController, Listener {

    private SQLiteDatabase sqLiteDatabase;
    private final ClassInterest interest = new ClassInterest(DataAction.class);

    public GastoControllerImp(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
        //Conecta controller al bus de notificaciones
        RepositoryChannel.getInstance().attachListener(this, interest);
    }

    @Override
    public void handle(Object object) {

        if (object != null && interest.isOfLike(object)) {
            DataAction action = (DataAction) object;
            if (action.getData() != null && (action.getData() instanceof Gasto)) {
                Gasto gasto = (Gasto) action.getData();

                try {
                    switch (action.getTrigger()) {
                        case UPDATE:
                        case INSERT: {
                            updateGasto(gasto);
                            break;
                        }
                        case DELETE: { //TODO
                            break;
                        }
                        default: { //TODO
                            break;
                        }
                    }


                } catch (Exception e) {
                    Log.e(this.getClass().getName(), e.toString());
                }
            }
        }
    }

    public void updateGasto(Gasto gasto) throws Exception {
        boolean update = (gasto.getId() != null);

        ContentValues values = new ContentValues();
        StringID catId = (StringID) gasto.getCategoria().getId();
        values.put(GASTO_TABLE.FIELD_ID_CAT, catId.getId());
        values.put(GASTO_TABLE.FIELD_MONTO, gasto.getMonto());
        values.put(GASTO_TABLE.FIELD_FECHA, GASTO_TABLE.DATE_FORMAT.format(gasto.getFecha()));
        if (update == true) {
            LongId gid = (LongId) gasto.getId();
            String where = GASTO_TABLE.FIELD_ID + "=?";
            sqLiteDatabase.update(GASTO_TABLE.NAME, values, where, new String[]{gid.getId() + ""});
        } else {
            long resultId = sqLiteDatabase.insertWithOnConflict(GASTO_TABLE.NAME, null, values, SQLiteDatabase.CONFLICT_FAIL);
            if (resultId == -1)
                throw new Exception("Error on insert new gasto");
            LongId gid = new LongId(resultId);
            gasto.setId(gid);
        }

        DataDispatcher.getInstance().spread(new DataAction(gasto, DataAction.Trigger.UPDATE));
    }

    public List<Gasto> getGastos() {
        List<Gasto> gastos = new ArrayList<>();
        {
            //select gasto.id,idCategoria,monto,fecha,descripcion from gasto join categoria on idCategoria=categoria.id
            String query = String.format("select %s,%s,%s,%s,%s from %s join %s on %s=%s",
                /*select*/
                    GASTO_TABLE.NAME + "." + GASTO_TABLE.FIELD_ID, GASTO_TABLE.FIELD_ID_CAT, GASTO_TABLE.FIELD_MONTO, GASTO_TABLE.FIELD_FECHA, CAT_TABLE.FIELD_DESC,
                /*from*/
                    GASTO_TABLE.NAME, CAT_TABLE.NAME,
                /*on*/
                    GASTO_TABLE.NAME + "." + GASTO_TABLE.FIELD_ID_CAT, CAT_TABLE.NAME + "." + CAT_TABLE.FIELD_TITLE);

            String[] selectionArgs = null;
            Cursor cursor = sqLiteDatabase.rawQuery(query, selectionArgs);
            while (cursor.moveToNext()) {
                long gastoId = cursor.getLong(0);
                String catTitle = cursor.getString(1);
                long monto = cursor.getLong(2);
                Date fecha = new Date();
                try {
                    fecha = GASTO_TABLE.DATE_FORMAT.parse(cursor.getString(3));
                } catch (Exception e) {
                    Log.e(this.getClass().getName(), e.toString());
                }
                String descripcion = cursor.getString(4);
                Categoria categoria = new Categoria(catTitle, descripcion);
                Gasto gasto = new Gasto(new LongId(gastoId), monto, fecha, categoria);

                gastos.add(gasto);
            }
        }

        return gastos;
    }

}
