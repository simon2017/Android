package lib.data.lib.data.handler;

import lib.data.Data;

/**
 * Created by sgutierc on 18-07-2016.
 */
public class DataAction {
    public static enum Trigger {UPDATE, INSERT, DELETE,LOAD};

    private Data data;
    private Trigger trigger;

    public DataAction(Data data, Trigger trigger) {
        this.data = data;
        this.trigger = trigger;
    }

    public Data getData(){
        return this.data;
    }

    public Trigger getTrigger(){
        return this.trigger;
    }

}
