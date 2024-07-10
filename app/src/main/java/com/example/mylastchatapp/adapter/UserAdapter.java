package com.example.mylastchatapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylastchatapp.Chatting;
import com.example.mylastchatapp.R;
import com.example.mylastchatapp.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Myviewholder>{

    UserModel userModel;

    Context context;
    List<UserModel> userModelList ;

    public UserAdapter(Context context) {
        this.context = context;
        userModelList=new ArrayList<>();
    }
    public void add(UserModel userModel){
        userModelList.add(userModel);
    }
    public void clear(){
        userModelList.clear();
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_data_row,parent,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        UserModel userModel=userModelList.get(position);
        holder.username.setText(userModel.getUsername());
//        String usernammm = holder.username.setText(userModel.getUsername());
        holder.email.setText(userModel.getEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Chatting.class);
                intent.putExtra("id",userModel.getUserID());
                intent.putExtra("name",userModel.getUsername());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    class Myviewholder extends RecyclerView.ViewHolder{
        TextView username,email;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.user_name_text);
            email=itemView.findViewById(R.id.email_text);
        }
    }
}
