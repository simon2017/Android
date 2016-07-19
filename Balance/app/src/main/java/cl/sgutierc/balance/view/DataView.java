package cl.sgutierc.balance.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import lib.data.Data;

/**
 * Created by sgutierc on 19-07-2016.
 */
public abstract class DataView<U extends Data> extends FrameLayout {

    private U data;
    private View view = null;
    private int layoutID;

    public DataView(int layoutID, Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.layoutID = layoutID;
        initView();
    }


    public DataView(int layoutID, Context context, AttributeSet attrs) {
        super(context, attrs);
        this.layoutID = layoutID;
        initView();
    }

    public DataView(int layoutID, Context context) {
        super(context);
        this.layoutID = layoutID;
        initView();
    }

    private void initView() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layoutID, null);

        this.addView(view);
    }

    public void setData(U data) {
        this.data = data;
        reload();
    }

    protected U getData()
    {
        return this.data;
    }

    protected View getViewLayout(){
        return this.view;
    }

    protected abstract void reload();

}
