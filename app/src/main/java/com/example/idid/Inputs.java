package com.example.idid;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class Inputs extends Fragment {


    EditText et_name;
    Button btn_ok_input;
    MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_inputs, container, false);

        et_name = v.findViewById(R.id.name_et);
        btn_ok_input = v.findViewById(R.id.ok_input_btn);

        btn_ok_input.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

               MyInfo.myName = et_name.getText().toString();
               mainActivity.uploadUserInfo();
               mainActivity.closeMyFragment();
            }
        });

        return v;
    }


}
