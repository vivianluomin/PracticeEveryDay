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
 * Created by asus1 on 2017/7/15.
 */

public class bendiyinyueAdapter extends ArrayAdapter<music> {

    Context context;
    int res;

    public bendiyinyueAdapter(Context context, int resource, List<music> objects) {
        super(context, resource, objects);
        this.context = context;
        this.res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        music item = getItem(position);
        ViewHold viewHold;
        View view;
        if(convertView == null){
             view= LayoutInflater.from(context).inflate(res,parent,false);
            viewHold = new ViewHold();
            viewHold.name = (TextView) view.findViewById(R.id.music_name);
            viewHold.artist = (TextView)view.findViewById(R.id.artist);
            view.setTag(viewHold);
        }else {
            view = convertView;
            viewHold = (ViewHold)view.getTag();
        }

        viewHold.name.setText(item.getTitle());
        viewHold.artist.setText(item.getArtist());

        return view;

    }

    class  ViewHold{
        TextView name ;
        TextView artist;


    }
}
