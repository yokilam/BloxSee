package com.example.franciscoandrade.bloxsee.views.teacher;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franciscoandrade.bloxsee.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RosterFragment extends Fragment {

    private RecyclerView recyclerView;

    public RosterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_roster, container, false);
        recyclerView = view.findViewById(R.id.recycler_container);

        return view;
    }

}
