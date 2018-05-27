package vivian.com.testnotifiction;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class message extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    static Intent[] makeMessageIntentStack(Context context, CharSequence from, CharSequence msg){
        Intent[] intents = new Intent[4];


        intents[0] = Intent.makeRestartActivityTask(new ComponentName(context,root.class));

        intents[1] = new Intent(context,root1.class);
        intents[1].putExtra("Path","App");

        intents[2] = new Intent(context,root2.class);
        intents[2].putExtra("Path","App/Notifiction");

        intents[3] = new Intent(context,message.class);
        intents[3].putExtra("form",from);
        intents[3].putExtra("msg",msg);
        return intents;
    }
}
