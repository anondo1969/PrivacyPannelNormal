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
public class PermissionSpecificApplicationAdapter extends ArrayAdapter{
    String[] permission_specific_application_text;

    public PermissionSpecificApplicationAdapter(Context context, String[] text) {
        super(context, R.layout.permission_specific_application_list_item, text);
        this.permission_specific_application_text = text;
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

        View row = inflater.inflate(R.layout.permission_specific_application_list_item, null, true);
        TextView textView1 = (TextView) row.findViewById(R.id.permission_specific_application_text_view);

        textView1.setText(permission_specific_application_text[position]);

        return row;
    }
}
