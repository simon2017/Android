package cl.sgutierc.balance.dispatcher;

import lib.data.dispatcher.*;

/**
 * Created by sgutierc on 15-07-2016.
 */
public class DataDispatcher  extends  DispatcherImp<Listener,ClassInterest> {
    /**
     * Initializes singleton.
     *
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


}
