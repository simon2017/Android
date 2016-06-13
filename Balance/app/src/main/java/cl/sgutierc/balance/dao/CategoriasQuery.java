package cl.sgutierc.balance.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sgutierc on 08-06-2016.
 */
public class CategoriasQuery {
    private final String TABLE_NAME="categoria";

    public CategoriasQuery(){};

    public List<CategoriasDao> getCategorias(SQLiteDatabase sqLiteDatabase){

        String columns[]={"id","descripcion"};
        String selection=null;
        String selectionArgs[]=null;
        String groupBy=null;
        String having=null;
        String orderBy=null;

        Cursor c=sqLiteDatabase.query(TABLE_NAME,columns,selection,selectionArgs,groupBy,having,orderBy);

        List<CategoriasDao> categorias=new ArrayList();

        while(c.moveToNext()){
            long id = c.getLong(0);
            String descripcion=c.getString(1);
            categorias.add(new CategoriasDao(id,descripcion));
        }
        return categorias;
    }
}
