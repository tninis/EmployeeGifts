package cloud.tninis.employeegifts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ms.square.android.expandabletextview.ExpandableTextView;

public class StartFragment extends Fragment {


    public StartFragment() {

    }


    public static StartFragment newInstance(String param1, String param2) {
        StartFragment fragment = new StartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.startFragmentTitle));
        View v = inflater.inflate(R.layout.fragment_start, container, false);

        ExpandableTextView expTv1 = (ExpandableTextView) v.findViewById(R.id.easterInfo)
                .findViewById(R.id.expand_text_view);
        ExpandableTextView expTv2 = (ExpandableTextView) v.findViewById(R.id.christmasInfo)
                .findViewById(R.id.expand_text_view);
        expTv1.setText(Html.fromHtml(getString(R.string.startFragmentEasterInfo)));
        expTv2.setText(Html.fromHtml(getString(R.string.startFragmentChristmasInfo)));
        return v;
    }

}
