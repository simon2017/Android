package cl.sgutierc.balance.dispatcher;

import lib.data.dispatcher.ClassInterest;
import lib.data.dispatcher.DispatcherImp;
import lib.data.dispatcher.Listener;

/**
 * Created by sgutierc on 15-07-2016.
 */
public class RepositoryChannel extends  DispatcherImp<Listener,ClassInterest> {
    /**
     * Initializes singleton.
     *
     * {@link SingletonHolder} is loaded on the first execution of
     * {@link Singleton#getInstance()} or the first access to
     * {@link SingletonHolder#INSTANCE}, not before.
     */
    private static class SingletonHolder {
        private static final RepositoryChannel INSTANCE = new RepositoryChannel();
    }

    public static RepositoryChannel getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RepositoryChannel() {
        super();
    }


}
