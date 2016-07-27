package lib.data.lib.data.handler;

import lib.data.Data;

/**
 * Created by sgutierc on 18-07-2016.
 */
public class DataAction {
    public static enum Trigger {UPDATE, INSERT, DELETE, LOAD}

    ;

    private Object data;
    private Trigger trigger;

    public DataAction(Object data, Trigger trigger) {
        this.data = data;
        this.trigger = trigger;
    }

    public Object getData() {
        return this.data;
    }

    public Trigger getTrigger() {
        return this.trigger;
    }

}
