package cl.sgutierc.balance;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import cl.sgutierc.balance.dao.CategoriasDao;
import cl.sgutierc.balance.dao.CategoriasQuery;
import cl.sgutierc.libdatarepository.SQLiteRepo;

public class MainActivity extends AppCompatActivity {
private static final int LOAD_GASTO_ACTIVITY_ID=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button gastoBttn=(Button)findViewById(R.id.gastoBttn);
        gastoBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),GastoActivity.class);
                startActivityForResult(intent, LOAD_GASTO_ACTIVITY_ID);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            int monto=data.getIntExtra(GastoActivity.MONTO_BUNDLE_ID,-1);
            long categoriaId=data.getLongExtra(GastoActivity.CATEGORIA_BUNDLE_ID,-1l);

            if(monto!=-1 && categoriaId!=-1){
                Log.d(MainActivity.class.getName(),"WORKED! "+monto+" "+categoriaId);
            }
        }
    }
}
