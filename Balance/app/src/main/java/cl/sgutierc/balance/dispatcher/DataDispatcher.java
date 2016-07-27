package cl.sgutierc.balance.dispatcher;

import java.util.List;

import lib.data.dispatcher.*;
import lib.data.lib.data.handler.DataAction;

/**
 * Created by sgutierc on 15-07-2016.
 */
public class DataDispatcher extends DispatcherImp<Listener, ClassInterest> {
    /**
     * Initializes singleton.
     * <p/>
     * {@link SingletonHolder} is loaded on the first execution of
     * {@link Singleton#getInstance()} or the first access to
     * {@link SingletonHolder#INSTANCE}, not before.
     */
    private static class SingletonHolder {
        private static final DataDispatcher INSTANCE = new DataDispatcher();
    }

    public static DataDispatcher getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private DataDispatcher() {
        super();
    }

    /**
     * @param dataList
     * @param trigger
     */
    public void spread(List<?> dataList, DataAction.Trigger trigger) {
        executor.execute(new Spreader(dataList, trigger));
    }

    class Spreader implements Runnable {
        List<?> dataList;
        DataAction.Trigger trigger;

        public Spreader(List<?> dataList, DataAction.Trigger trigger) {
            this.dataList = dataList;
            this.trigger = trigger;
        }

        public void run() {
            for (Object data : dataList) {
                spread(new DataAction(data, trigger));
            }
        }
    }

}
