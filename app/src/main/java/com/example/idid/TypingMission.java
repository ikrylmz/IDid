package com.example.idid;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TypingMission extends Fragment {

    TextView txt_user_name;
    EditText et_subject_mission,et_mission;
    Button btn_upload_mission,btn_back_typing;
    String userName;
    String phoneNumber;
    String  missionKey;
    MainActivity mainActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_typing_mission, container, false);

        et_subject_mission = v.findViewById(R.id.subject_mission_et);
        et_mission = v.findViewById(R.id.mission_et);
        txt_user_name = v.findViewById(R.id.user_name_t_txt);
        btn_upload_mission = v.findViewById(R.id.upload_mission_btn);
        btn_back_typing = v.findViewById(R.id.back_typing_btn);


        userName = getArguments().getString("userName");
        phoneNumber = getArguments().getString("phoneNumber");
        missionKey = getArguments().getString("missionKey");

        txt_user_name.setText(userName);

        btn_upload_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               uploadMission();
            //   mainActivity.closeMyFragment();
            }
        });

        btn_back_typing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return v;
    }

    private void uploadMission()
    {
        new Upload().uploadMission(setMission(),"Requested/"+phoneNumber+"/Request");
    }

    MissionInfo setMission()
    {
        MissionInfo missionInfo = new MissionInfo();
        missionInfo.setText(et_mission.getText().toString());
        missionInfo.setMissionKey(missionKey);
        missionInfo.setSubject(et_subject_mission.getText().toString());
        missionInfo.setPhoneNumber(phoneNumber);
        return missionInfo;
    }

}
