package lib.data.dispatcher;

import lib.data.Data;
import lib.data.lib.data.handler.DataAction;

/**
 * Created by sgutierc on 14-07-2016.
 */
public interface Dispatcher <U extends Listener,V extends Interest>{

    void spread(DataAction data);
    void attachListener(U listener, V interest);
    void removeListener(U listener, V interest);
}
