package ca.lambton.allan.xlambton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // agents list
        Button menuList = findViewById(R.id.menu_list);
        menuList.setOnClickListener(v -> {

        });

        // search agent
        Button menuSearch = findViewById(R.id.menu_search);
        menuSearch.setOnClickListener(v -> {

        });

        // add agent
        Button menuAdd = findViewById(R.id.menu_add);
        menuAdd.setOnClickListener(v -> {

        });

    }
}
