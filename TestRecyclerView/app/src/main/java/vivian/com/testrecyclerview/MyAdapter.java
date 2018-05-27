package vivian.com.testrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by asus1 on 2017/7/31.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myHolder> {
    List<String> data ;
    int[] size = new int[]{
            100,120,140,160,180,200
    };
    Context context;
    onItemClickListener listener;

    public interface onItemClickListener{
        void onClick(int position);
        void LongClivk(int position);
    }

    public void SetOnItmeClickListener(onItemClickListener listener){
        this.listener = listener;
    }

    public MyAdapter(List<String> data, Context context) {
        this.data =data;
        this.context = context;

    }

    @Override
    public void onBindViewHolder(final myHolder holder, int position) {
            holder.textView.setText(data.get(position));

        if(listener != null){
            holder.itemView .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.onClick(holder.getLayoutPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.LongClivk(holder.getLayoutPosition());
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        myHolder holder = new myHolder(LayoutInflater.from(context).
                inflate(R.layout.recycleritem,parent,false));

        return holder;

    }

    class  myHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public myHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }

    public  void addItem(int position){
        data.add(position,"ssss");
        notifyItemInserted(position);
    }

   public void remove(int position){
       data.remove(position);
       notifyItemRemoved(position);
   }
}
