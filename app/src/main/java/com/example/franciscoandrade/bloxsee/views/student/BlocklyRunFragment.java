package com.example.franciscoandrade.bloxsee.views.student;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.franciscoandrade.bloxsee.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlocklyRunFragment extends Fragment {

    public BlocklyRunFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blockly_run, container, false);
    }

}
