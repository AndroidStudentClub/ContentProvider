package com.example.admin.personallibrarycatalogue;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FirstLaunchFragment extends Fragment {

    public FirstLaunchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                      Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_first_launch, container, false);

        Button addBookButton = (Button)rootView.findViewById(R.id.button);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddBookActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
