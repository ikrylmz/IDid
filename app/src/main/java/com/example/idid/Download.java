package com.example.idid;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Download {

   private IUsers i_users;
   private IMissions i_missions;

   public void set_IUsers(IUsers i_users){
       this.i_users = i_users;
   }
   public void set_IMissions(IMissions i_users){
       this.i_missions = i_missions;
   }

   public void downloadUser(String path)
    {

        FirebaseDatabase  firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<User> userList = new ArrayList<>();
                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    if(isMe(data.getValue(User.class)))
                        continue;
                    userList.add(data.getValue(User.class));
                }
                i_users.users(userList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG,databaseError.getMessage());
            }
        });
    }
    private Boolean isMe(User u)
    {
        if(u.getPhoneNumber().equals(MyInfo.myPhoneNumber))
            return true;
        return false;
    }
    public void downloadMission(String path)
    {

        FirebaseDatabase  firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MissionInfo mission;
                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    mission = data.getValue(MissionInfo.class);
                    i_missions.missions(mission);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG,databaseError.getMessage());
            }
        });
    }
    public interface IUsers{
        void users(ArrayList<User> userList);
    }
    public interface IMissions{
        void missions(MissionInfo mission);
    }
}
