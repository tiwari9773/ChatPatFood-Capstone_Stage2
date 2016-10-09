package in.seleption.chatpatfood.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.seleption.Utility.JsonHelper;
import in.seleption.adapter.HomeMenuRecyclerAdapter;
import in.seleption.adapter.RecycleMarginDecoration;
import in.seleption.chatpatfood.R;
import in.seleption.listener.HomeMenuClickListener;
import in.seleption.model.Menu;
import in.seleption.model.Stall;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeMenuClickListener {

    private static final String TAG = MainActivity.class.getName();

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

        Menu menu = new Menu(1,"Vada pav");
        Menu menu2 = new Menu(2,"Idli");

        List<Menu> lsMenu = new ArrayList<>(2);
        lsMenu.add(menu);
        lsMenu.add(menu2);

        Stall stall = new Stall();
        stall.setAddress("Mumbai");
        stall.setComment("Test");
        stall.setStall_name("Hari Om food Stall");
        stall.setMobile_no("9773319913");
        stall.setStart_time(11);
        stall.setEnd_time(18);
        stall.setLatittude(91.00000d);
        stall.setLongitude(81.00000d);
        stall.setUrl("http://google.com/abc.png");
        stall.setMenu(lsMenu);
        /*TEst*/
        Log.e(TAG, "onCreate: "+ JsonHelper.ConvertToJson(stall));
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
        HomeMenuRecyclerAdapter adapter = new HomeMenuRecyclerAdapter(items, this);
        GridLayoutManager manager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(decorator);
        recyclerView.setAdapter(adapter);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

        // Intent in = new Intent()
    }
}
