package com.example.mylastchatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylastchatapp.adapter.MessageAdapter;
import com.example.mylastchatapp.model.MessageModel;
import com.example.mylastchatapp.model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class Chatting extends AppCompatActivity {
    String receiverID;
    DatabaseReference databaseReferencesender,databaseReferencereceiver;
    ImageButton messagesend,backbtn;
    String senderRoom,receiverRoom;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;

    TextView username;
    EditText sendmessageedittext;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        receiverID=getIntent().getStringExtra("id");
        username=findViewById(R.id.other_username);
        String name = getIntent().getStringExtra("name");
//        String email= getIntent().getStringExtra("email");
        username.setText(name);
        messagesend=findViewById(R.id.message_send_btn);
        sendmessageedittext=findViewById(R.id.chat_message_input);
        backbtn=findViewById(R.id.back_btn);


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Chatting.this, ChatActivity.class);
                startActivity(intent);
            }
        });
        messagesend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = sendmessageedittext.getText().toString();
                if(message.trim().length()>0){
                    sendmessage(message);
                }
            }
        });
        senderRoom= FirebaseAuth.getInstance().getUid()+receiverID;
        receiverRoom=receiverID+FirebaseAuth.getInstance().getUid();


        databaseReferencesender= FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        databaseReferencereceiver= FirebaseDatabase.getInstance().getReference("chats").child(receiverRoom);
        databaseReferencesender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    MessageModel messageModel=dataSnapshot.getValue(MessageModel.class);
                    messageAdapter.add(messageModel);
                    try {
                        messageAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(Chatting.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        // Handle the exception appropriately
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView=findViewById(R.id.chat_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(false);
        recyclerView.setLayoutManager(manager);
        messageAdapter= new MessageAdapter(this);
        recyclerView.setAdapter(messageAdapter);

    }

    void sendmessage(String message){
        String msgUID = UUID.randomUUID().toString();
        MessageModel model= new MessageModel(msgUID,FirebaseAuth.getInstance().getUid(),message);

        // Add the new message to the adapter
        messageAdapter.add(model);
        try {
            messageAdapter.notifyDataSetChanged();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Scroll the RecyclerView to the last item
        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

        // Save the message to sender's database reference
        databaseReferencesender
                .child(msgUID)
                .setValue(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        sendmessageedittext.setText("");
                    }
                });

        // Save the message to receiver's database reference
        databaseReferencereceiver
                .child(msgUID)
                .setValue(model);
    }

}