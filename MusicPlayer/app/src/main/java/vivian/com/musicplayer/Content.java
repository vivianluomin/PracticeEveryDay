package vivian.com.musicplayer;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus1 on 2017/7/20.
 */

public class Content {
    public static List<Controller> controllerList = new ArrayList<>();
    public static  void resigter(Controller controller){


            controllerList.add(controller);
            if(playMusic.class.isInstance(controller)){
                Controller content=controllerList.get(0);
                controllerList.set(0,controller);
               controllerList.set((controllerList.size()-1),content);

            
        }

        Log.d("conSize",String.valueOf(controllerList.size()));

    }
    public  static  void unresigter(Controller controller){
        if(controllerList.contains(controller))
        controllerList.remove(controller);
    }

    public  static  void removeAll(){
        controllerList.clear();
    }


}
