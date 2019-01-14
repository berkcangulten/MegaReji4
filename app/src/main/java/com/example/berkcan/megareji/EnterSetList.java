package com.example.berkcan.megareji;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EnterSetList extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    ArrayList<String> date;
    ArrayList<String> email;
    ArrayList<String>ids;
    ListView listViewEnterSetList;
    SetListPostClass customAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.add_new_set,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //If item id and selected item id is equal
        if(item.getItemId() == R.id.add_new_set){
            Intent intent2 =new Intent(getApplicationContext(),EnterSetName.class);
            startActivity(intent2);


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_set_list);
        date =new ArrayList<String>();
        email=new ArrayList<String>();
        ids=new ArrayList<String>();
        listViewEnterSetList=findViewById(R.id.listViewEnterSetList);
        firebaseDatabase=firebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
        customAdapter=new SetListPostClass(email,ids,this);


       listViewEnterSetList.setAdapter(customAdapter);
       getDataFromFirebase();
         listViewEnterSetList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(getApplicationContext(),SceneList.class);
                intent.putExtra("id",ids.get(position).toString());


                startActivity(intent);
            }
        });

    }


    public void getDataFromFirebase() {
        DatabaseReference newReference=firebaseDatabase.getReference("sets");
        newReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    HashMap<String,String> hashMap= (HashMap<String,String> )ds.getValue();

                    email.add(hashMap.get("Name"));
                    ids.add(ds.getKey());
                    customAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;


    }
}
