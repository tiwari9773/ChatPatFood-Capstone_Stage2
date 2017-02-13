package in.seleption.chatpatfood.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.seleption.adapter.HomeMenuRecyclerAdapter;
import in.seleption.adapter.RecycleMarginDecoration;
import in.seleption.chatpatfood.R;
import in.seleption.db_helper.TableContract;
import in.seleption.listener.HomeMenuClickListener;
import in.seleption.model.Menu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeMenuClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MainActivity";
    private HomeMenuRecyclerAdapter adapter;

    // Unique Loader Id for every loader we create
    private static final int LIST_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*Initialise*/
        initialise();

        getSupportLoaderManager().initLoader(LIST_LOADER, null, this);

    }

    private void initialise() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_home_menu);
        TypedArray img = getResources().obtainTypedArray(R.array.arr_drawable_menu);
        String[] home_menu = getResources().getStringArray(R.array.arr_home_food_name);

        List<Menu> items = new ArrayList<>();
        int count = 0;
        for (String name : home_menu) {
            Menu item = new Menu(count + 1, img.getResourceId(count, 0), name);
            count++;
            items.add(item);
        }

        /*make resource free*/
        img.recycle();

        RecycleMarginDecoration decorator = new RecycleMarginDecoration(this);
        adapter = new HomeMenuRecyclerAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(decorator);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_camera:
                break;

            case R.id.nav_gallery:
                break;

            case R.id.nav_manage:
                break;

            case R.id.nav_share:
                break;

            case R.id.nav_register_stall:
                Intent in = new Intent(this, RegisterStallActivity.class);
                startActivity(in);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMenuClickListener(int id) {
        Toast.makeText(MainActivity.this, id + "Clicked", Toast.LENGTH_SHORT).show();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, TableContract.RegisterStall.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        toggleVisibility(data);

        if (data != null) {
            adapter.swapCursor(data);
        }
    }

    private void toggleVisibility(Cursor data) {
        if (data != null && data.getCount() > 0) {
            TextView textView = (TextView) findViewById(R.id.tv_msg);
            textView.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) findViewById(R.id.tv_msg);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
