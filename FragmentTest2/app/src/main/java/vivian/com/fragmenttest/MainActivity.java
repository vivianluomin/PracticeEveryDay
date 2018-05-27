package vivian.com.fragmenttest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment fragment = new oneFragment();
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container,new oneFragment());
        transaction.add(R.id.container,new oneFragment());
        transaction.add(R.id.container,new twoFragment());
        transaction.add(R.id.container,new twoFragment());
        transaction.commit();
        Button button = (Button)findViewById(R.id.change);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment1 = new twoFragment();
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.replace(R.id.container,fragment1);
                transaction1.addToBackStack(null);
                transaction1.commit();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add("aaaaa");
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }
}
