package com.test.helloworld.test4android;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.internal.view.SupportMenuItem;
import androidx.customview.widget.ViewDragHelper;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.test.helloworld.test4android.adapter.RecyclerViewAdapter;
import com.test.helloworld.test4android.bean.BeanFactory;

import java.lang.reflect.Field;

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
    @BindView(R.id.nav_view_2)
    NavigationView navigationView;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    NavController navController;
    AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_action_headline);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment1);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).
                setDrawerLayout(drawerLayout).build();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                Toast.makeText(RecyclerTestActivity.this, "抽屉菜单被点击了", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        try {
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper viewDragHelper = (ViewDragHelper) leftDraggerField.get(drawerLayout);
            Field edgeSizeField = viewDragHelper.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(viewDragHelper);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            edgeSizeField.setInt(viewDragHelper, Math.max(edgeSize, displayMetrics.widthPixels / 2));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {

        return Navigation.findNavController(this, R.id.nav_host_fragment1).navigateUp();

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerVisible(navigationView)) {
            drawerLayout.closeDrawers();
            return;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //todo 这里直接设置点击后的事件即可
        //todo actionbar的menu在本方法中设置，drawer的menu在navigation中设置
//        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Toast.makeText(RecyclerTestActivity.this, "菜单被点击了",
//                        Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });

        Toast.makeText(this, "菜单被点击了", Toast.LENGTH_SHORT).show();

//        if (item.getActionView() != toolbar) {
//            navigationView.setCheckedItem(item);
//        }

        return true;
    }
}
