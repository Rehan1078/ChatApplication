package com.example.mylastchatapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylastchatapp.R;
import com.example.mylastchatapp.model.MessageModel;
import com.example.mylastchatapp.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Myviewholder>{

    Context context;
    List<MessageModel> messageModelList ;

    public MessageAdapter(Context context) {
        this.context = context;
        messageModelList=new ArrayList<>();
    }
    public void add(MessageModel userModel){
        messageModelList.add(userModel);
    }
    public void clear(){
        messageModelList.clear();
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_row_chat,parent,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        MessageModel messageModel = messageModelList.get(position);
        if (messageModel.getSenderID().equals(FirebaseAuth.getInstance().getUid())) {
            // If the sender is the current user, display the message on the right side
            holder.msgright.setText(messageModel.getMessage());
            holder.layoutright.setVisibility(View.VISIBLE);
            holder.layoutleft.setVisibility(View.GONE);
        } else {
            // If the sender is not the current user, display the message on the left side
            holder.msgleft.setText(messageModel.getMessage());
            holder.layoutright.setVisibility(View.GONE);
            holder.layoutleft.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    class Myviewholder extends RecyclerView.ViewHolder{
        TextView msgright,msgleft;
        LinearLayout layoutleft,layoutright;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            msgleft=itemView.findViewById(R.id.left_chat_textview);
            msgright=itemView.findViewById(R.id.right_chat_textview);
            layoutright=itemView.findViewById(R.id.right_chat_layout);
            layoutleft=itemView.findViewById(R.id.left_chat_layout);

        }
    }
}
