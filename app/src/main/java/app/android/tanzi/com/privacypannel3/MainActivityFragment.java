package app.android.tanzi.com.privacypannel3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    /***If you change this String then please modify code on onItemClick***/
    String[] privacyPanel = {
            "Location Accuracy",
            "User Profile",
            "Transparency Control",
            "Guided Tour",
            "About"
    };

    Integer[] privacyPanelIcon = {R.drawable.location,
            R.drawable.user_profile,
            R.drawable.transparenty,
            R.drawable.guided_tr,
            R.drawable.info};

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        final PrivacyPanelAdapter privacyPanelAdapter = new PrivacyPanelAdapter(getActivity(), privacyPanelIcon,
                privacyPanel);

        ListView panelListView = (ListView) rootView.findViewById(R.id.list_view_privacy_pannel);

        panelListView.setAdapter(privacyPanelAdapter);

        panelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = privacyPanelAdapter.getItem(position);

                String str = (String) item;
                String alter = str + " is not Implemented yet!";

                if (str == "Transparency Control") {
                    Intent intent = new Intent(getActivity(), TransparencyControlActivity.class);
                    //based on item add info to intent
                    startActivity(intent);
                } else if (str == "User Profile") {
                    Intent intent = new Intent(getActivity(), UserProfileViewActivity.class);
                    //based on item add info to intent
                    startActivity(intent);
                } else if (str == "Location Accuracy") {
                    Intent intent = new Intent(getActivity(), LocationAccuracyViewActivity.class);
                    //based on item add info to intent
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), alter, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}
