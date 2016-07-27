package cl.sgutierc.balance.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import cl.sgutierc.balance.data.Gasto;
import cl.sgutierc.balance.dispatcher.DataDispatcher;
import cl.sgutierc.balance.view.gasto.GastoView;
import lib.data.Data;
import lib.data.dispatcher.ClassInterest;
import lib.data.dispatcher.Listener;
import lib.data.lib.data.handler.DataAction;

/**
 * Created by sgutierc on 19-07-2016.
 */
public abstract class BaseList<U extends Data, V extends DataView> extends ArrayAdapter<U> implements Listener {
    private int layoutId;
    private ListView listView;
    private ClassInterest interest = new ClassInterest(DataAction.class);
    private Activity activity;

    public BaseList(int layoutId, Activity activity) {
        super(activity, 0);
        this.activity = activity;
        this.layoutId = layoutId;

        listView = (ListView) activity.findViewById(layoutId);
        listView.setAdapter(this);

        //attach to notification bus
        DataDispatcher.getInstance().attachListener(this, interest);
    }

    public void handle(Object object) {
        if (object != null && interest.isOfLike(object)) {
            DataAction dataAction = (DataAction) object;
            U data = convertFrom(dataAction.getData());

            if (data != null) {
                DataAction.Trigger trigger = dataAction.getTrigger();
                activity.runOnUiThread(new UpdateList(data, trigger, this));
            }
        }
    }

    protected class UpdateList implements Runnable {
        U data;
        ArrayAdapter adapter;
        DataAction.Trigger trigger;

        public UpdateList(U data, DataAction.Trigger trigger, ArrayAdapter adapter) {
            this.data = data;
            this.trigger = trigger;
            this.adapter = adapter;
        }

        public void run() {
            if (trigger == DataAction.Trigger.DELETE)
                this.adapter.remove(data);
            else {
                int pos = this.adapter.getPosition(data);
                if (pos > -1) {
                    Object item = this.adapter.getItem(pos);
                    this.adapter.remove(item);
                }

                this.adapter.add(data);
            }
            this.adapter.notifyDataSetChanged();
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        U data = getItem(position);
        V view = (V) convertView;

        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            view = getView(getContext());
        }

        view.setData(data);

        return view;
    }

    protected abstract V getView(Context context);

    protected abstract U convertFrom(Object data);
}
