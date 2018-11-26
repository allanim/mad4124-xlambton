package ca.lambton.allan.xlambton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // agents list
        Button menuList = findViewById(R.id.menu_list);
        menuList.setOnClickListener(v -> {
            Intent intent = new Intent(this, AgentListActivity.class);
            startActivity(intent);
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
