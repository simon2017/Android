package cl.sgutierc.balance.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import lib.data.Data;

/**
 * Created by sgutierc on 08-09-2016.
 */
public class HeadedList<U extends Data, V extends DataView> extends LinearLayout {
    private ListView listView = null;
    private BaseListAdapter<U, V> adapter = null;
    private Context context;

    public HeadedList(Context context, AttributeSet attrs, String[] headers) {
        super(context, attrs);
        this.context = context;

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setGravity(Gravity.TOP);
        loadHeaders(headers);

        listView = new ListView(context);
        LinearLayout.LayoutParams layouts=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        listView.setLayoutParams(layouts);
        this.addView(listView);
    }

    private void loadHeaders(String[] headers) {
        if (headers == null || (headers != null && headers.length <= 0))
            return;

        LinearLayout headLayout = new LinearLayout(context);
        headLayout.setOrientation(LinearLayout.HORIZONTAL);
        headLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        headLayout.setWeightSum(1.0f);

        float weight = 1.0f / headers.length;

        for (String head : headers) {
            TextView tview = new TextView(context);
            tview.setGravity(Gravity.CENTER);
            setLayoutParams(tview, weight);
            tview.setText(head);
            tview.setTypeface(null, Typeface.BOLD);
            headLayout.addView(tview);
        }

        this.addView(headLayout);
    }

    private TextView setLayoutParams(TextView source, float weight) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.weight = weight;
        source.setLayoutParams(params);
        return source;
    }

    public void setAdapter(BaseListAdapter<U, V> adapter) {
        this.adapter = adapter;
        this.listView.setAdapter(adapter);
    }


}
