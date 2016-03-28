package app.android.tanzi.com.privacypannel3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Entu on 3/23/2016.
 */
public class LocationAccuracyViewAdapter extends ArrayAdapter {
    String[] location_choice_text;
//    boolean[] selectedPosition2 = new boolean[4];
    String[] selectedPosition;
    Boolean[] selectedPosition2;
    ArrayList<Boolean> locationList;

    public LocationAccuracyViewAdapter(Context context, String[] text, ArrayList<Boolean> checkBoxSelect) {
        super(context, R.layout.location_list_item, text);
        this.location_choice_text = text;
        this.locationList = checkBoxSelect;
        this.selectedPosition2 = new Boolean[locationList.size()];
        int i=-1;
        //Log.d("TanziLogg", Integer.toString(l));
       for (Boolean app : locationList) {
           i++;
           selectedPosition2[i] = app;

       }

        this.getContext();


    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.location_list_item, null, true);
        TextView textView1 = (TextView) row.findViewById(R.id.location_list_text_view);
        final CheckBox checkbox = (CheckBox) row.findViewById(R.id.location_list_checkBox);

        textView1.setText(location_choice_text[position]);

        if(selectedPosition2[position]) checkbox.setChecked(true);
        else checkbox.setChecked(false);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Object item = getItem(position);
                Boolean status;
                status = checkbox.isChecked();
                String status1= Boolean.toString(status);
                locationList.set(position,status);
                String str = (String) item;
//                Toast.makeText(getContext(), status1, Toast.LENGTH_SHORT).show();

                if(selectedPosition2[position]==true){
                    selectedPosition2[position]=false;}
                else {selectedPosition2[position]= true;}
            }
        });

        return row;
    }


    public Boolean [] saveValues(){

        Boolean[] myArray = new Boolean[locationList.size()];
        //*****************************
        locationList.toArray(myArray);
//        Toast.makeText(getContext(), "returning", Toast.LENGTH_SHORT).show();
        return myArray;
    }

}
