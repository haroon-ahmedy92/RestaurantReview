package com.example.restaurantreview;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge mode
        setContentView(R.layout.activity_main);

        // Set the Toolbar as the ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);

        // Create an ActionBarDrawerToggle and sync it with the DrawerLayout
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.open_nav, // String resource for "open drawer" description
                R.string.close_nav // String resource for "close drawer" description
        );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // Adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}