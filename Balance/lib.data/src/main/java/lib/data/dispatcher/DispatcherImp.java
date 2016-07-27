package lib.data.dispatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lib.data.Data;
import lib.data.lib.data.handler.DataAction;

/**
 * Created by sgutierc on 14-07-2016.
 *
 * @param <U> Listener
 * @param <V> Interest
 */
public class DispatcherImp<U extends Listener, V extends Interest> implements Dispatcher<U, V> {
    protected HashMap<V, List<U>> listenersMap = null;
    protected ExecutorService executor = null;

    public DispatcherImp() {
        this.listenersMap = new HashMap<V, List<U>>();
        executor = Executors.newSingleThreadExecutor();
    }

    /**
     * @param data
     * @return
     */
    List<V> getInteresteds(Object data) {
        Set<V> interests = listenersMap.keySet();
        List<V> result = new ArrayList<>();

        for (V criteria : interests) {
            if (criteria.isOfLike(data)) {
                result.add(criteria);
            }
        }

        return result;
    }

      /**
     * @param data
     */
    public void spread(Object data) {
        List<V> interesteds = getInteresteds(data);
        if ((interesteds == null) || (interesteds != null && interesteds.isEmpty() == true))
            return;

        for (V intst : interesteds) {
            List<U> listeners = listenersMap.get(intst);
            if (listeners == null)
                return;
            for (U listener : listeners) {
                executor.execute(new Notifier(listener, data));
            }
        }
    }

    /**
     * @param listener
     * @param interest
     */
    public void attachListener(U listener, V interest) {
        if (listener == null || interest == null)
            return;

        List<U> listeners = listenersMap.get(interest);
        synchronized (this) {
            if (listeners == null)
                listeners = new CopyOnWriteArrayList<U>();
        }

        listeners.add(listener);
        listenersMap.put(interest, listeners);
    }

    /**
     * @param listener
     * @param interest
     */
    public void removeListener(U listener, V interest) {
        if (listener == null || interest == null)
            return;

        List<U> listeners = listenersMap.get(interest);

        if (listeners == null)
            return;
        listeners.remove(listener);

        if (listeners.isEmpty())
            listenersMap.remove(interest);
        else
            listenersMap.put(interest, listeners);
    }

    /**
     * @author sgutierc
     */
    class Notifier implements Runnable {
        U listener;
        Object data;

        public Notifier(U listener, Object data) {
            this.listener = listener;
            this.data = data;
        }

        @Override
        public void run() {
            if (listener != null)
                listener.handle(data);
        }
    }

}
