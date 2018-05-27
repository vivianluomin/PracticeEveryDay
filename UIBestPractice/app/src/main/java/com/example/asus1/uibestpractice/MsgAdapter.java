package com.example.asus1.uibestpractice;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asus1 on 2017/4/14.
 */

public class MsgAdapter extends RecyclerView.Adapter <MsgAdapter.ViewHolder>{
    private List<Msg> mMsgList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout;
        LinearLayout rightlaout;
        TextView leftMsg;
        TextView rightMsg;
        public ViewHolder(View view){
            super(view);
            leftLayout=(LinearLayout)view.findViewById(R.id.left_layout);
            rightlaout =(LinearLayout)view.findViewById(R.id.right_layout);
            leftMsg = (TextView)view.findViewById(R.id.left_msg);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);

        }
    }

    public MsgAdapter(List<Msg> msgList){
        mMsgList=msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.msg_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);
        if(msg.getType()==Msg.TYPE_RECEIVED){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightlaout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
        }else if(msg.getType()==Msg.TYPE_SEND){
            holder.rightlaout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
