package com.zurefaseverler.kithub;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sepet:
                Intent i = new Intent(this, CartActivity.class);
                startActivity(i);
                break;

            case R.id.profil:

                i = new Intent(this, ProfilePage.class );
                startActivity(i);
                break;

            case R.id.kategoriler:
                i = new Intent(this, Categories.class);
                startActivity(i);
                break;

            case R.id.siparisler:
                i = new Intent(this, ProfilePage.class );
                startActivity(i);
                break;

            case R.id.admin_drawer:

                i = new Intent(this, Admin.class);
                startActivity(i);
                break;

            case  R.id.yazarlar:
                i = new Intent(this, Authors.class );
                startActivity(i);
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
        else {
            finishAffinity();
            System.exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*  drawer  */
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.open_drawer,R.string.close_drawer);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton button_drawer = findViewById(R.id.button_drawer);
        button_drawer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_drawer:
                drawer.openDrawer(GravityCompat.END);
                break;

        }

    }


}
