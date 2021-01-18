package com.example.idid;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;




public class MissionShower extends Fragment {

    TextView txt_name_show,txt_subject_show,txt_text_show;
    String name,subject,text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_mission_station, container, false);

        txt_name_show = v.findViewById(R.id.name_show_txt);
        txt_subject_show = v.findViewById(R.id.subject_show_txt);
        txt_text_show = v.findViewById(R.id.text_show_txt);

        //Bir fragmenti o fragmentin içinden nasıl kapatırız???

        name = getArguments().getString("name");
        subject = getArguments().getString("subject");
        text = getArguments().getString("text");

        txt_name_show.setText(name);
        txt_subject_show.setText(subject);
        txt_text_show.setText(text);



        return v;
    }

    /*
    private void downloadMission(String path)
    {

        // "Missions/" + missionKey "

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<MissionInfo> missionList = new ArrayList<>();
                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    missionList.add(data.getKey().);
                }
                i_missions.missions(missionList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG,databaseError.getMessage());
            }
        });
    }
*/
}
