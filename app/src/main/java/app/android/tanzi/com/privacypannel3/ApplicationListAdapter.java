package app.android.tanzi.com.privacypannel3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Entu on 3/21/2016.
 */
public class ApplicationListAdapter extends ArrayAdapter {
    String[] application_list_text;

    public ApplicationListAdapter(Context context, String[] text) {
        super(context,R.layout.transparency_control_list_item, text);
        this.application_list_text = text;
        this.getContext();
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.application_list_item, null, true);
        TextView textView1 = (TextView) row.findViewById(R.id.application_list_text_view);

        textView1.setText(application_list_text[position]);

        return row;
    }
}
