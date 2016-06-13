package cl.sgutierc.libdatarepository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by sgutierc on 03-06-2016.
 */
public class SQLiteRepo extends SQLiteOpenHelper {

    private DBSchema schema;

    public SQLiteRepo(Context context, DBSchema schema) {
        super(context, schema.getDatabaseName(), null, schema.getDBVersion());
        this.schema = schema;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            LinkedBlockingQueue<String> scriptList = schema.getCreateScripts();

            String script = scriptList.poll();
            while (script != null) {

                System.out.println(script);
                sqLiteDatabase.execSQL(script);
                script = scriptList.poll();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //TODO
    }

}
