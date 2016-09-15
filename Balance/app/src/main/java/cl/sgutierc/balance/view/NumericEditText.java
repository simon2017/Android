package cl.sgutierc.balance.view;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by sgutierc on 15-09-2016.
 */
public class NumericEditText extends EditText {

    public NumericEditText(Context context) {
        super(context);
        setWatcher();
    }

    public NumericEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWatcher();
    }

    public NumericEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWatcher();
    }

   public Number getNumber() throws ParseException{
       String value= super.getText().toString();
       return formatter.parse(value);

   }

    private static final DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());

    private void setWatcher() {
        NumericWatcher watcher = new NumericWatcher(this, formatter);
        this.addTextChangedListener(watcher);
    }

}
