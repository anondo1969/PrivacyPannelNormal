package app.android.tanzi.com.privacypannel3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Entu on 3/19/2016.
 */
public class PrivacyPanelAdapter extends ArrayAdapter {
    String[] privacy_pannel_text;
    Integer[] privacy_pannel_image;

    public PrivacyPanelAdapter(Context context, Integer[] image_id, String[] text) {
        super(context, R.layout.privacy_panel_list_item, text);
        this.privacy_pannel_text = text;
        this.privacy_pannel_image = image_id;
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

        View row = inflater.inflate(R.layout.privacy_panel_list_item, null, true);
        TextView textView = (TextView) row.findViewById(R.id.privacy_panel_text_view);
        ImageView imageView = (ImageView) row.findViewById(R.id.privacy_panel_imageView);

        textView.setText(privacy_pannel_text[position]);
        imageView.setImageResource(privacy_pannel_image[position]);

        return row;
    }
}
