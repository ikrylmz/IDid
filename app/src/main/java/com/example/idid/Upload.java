package com.example.idid;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Upload {

    public void uploadMission(MissionInfo mission, String path)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(path);
        databaseReference.setValue(mission);
    }
    public void uploadUser(User user)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users/"+getKey());
        databaseReference.setValue(user);
    }

    public static String getKey()
    {
        return FirebaseDatabase.getInstance().getReference().push().getKey();
    }


}
