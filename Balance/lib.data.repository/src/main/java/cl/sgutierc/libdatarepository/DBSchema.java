package cl.sgutierc.libdatarepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by sgutierc on 03-06-2016.
 */
public abstract class DBSchema {

    private LinkedBlockingQueue<String> scriptList;

    public DBSchema(){
        this.scriptList=new LinkedBlockingQueue<String>();
    }

    public void addCreateScript(String script){
        try{
            this.scriptList.put(script);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public abstract int getDBVersion();
    public abstract String getDatabaseName();

    public LinkedBlockingQueue<String> getCreateScripts(){
        return this.scriptList;
    }

}
