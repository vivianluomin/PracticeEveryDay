package vivian.com.musicplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by asus1 on 2017/8/8.
 */

public class itemgedanAdapter extends ArrayAdapter<String> {


    Context context;
    int res;

    public itemgedanAdapter(Context context, int resource, List<String> objects) {
        super(context,resource,objects);
        this.context = context;
        this.res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = getItem(position);
        itemgedanAdapter.ViewHold viewHold;
        View view;
        if(convertView == null){
            view= LayoutInflater.from(context).inflate(res,parent,false);
            viewHold = new itemgedanAdapter.ViewHold();
            viewHold.name = (TextView) view.findViewById(R.id.list_item_gedan);

            view.setTag(viewHold);
        }else {
            view = convertView;
            viewHold = (itemgedanAdapter.ViewHold)view.getTag();
        }

        viewHold.name.setText(item);


        return view;

    }

    class  ViewHold{
        TextView name ;



    }
}
