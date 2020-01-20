package com.test.helloworld.test4android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerTestActivity extends AppCompatActivity {


    @BindView(R.id.drawer_layout_2)
    DrawerLayout drawerLayout;

    @BindView(R.id.coordinator_2)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.appbar_2)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_2)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar_2)
    Toolbar toolbar;

    NavController navController;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        navController = Navigation.findNavController(this, R.id.fragment_1);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        navController.navigate(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(RecyclerTestActivity.this, "菜单被点击了",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return super.onOptionsItemSelected(item);
    }
}
