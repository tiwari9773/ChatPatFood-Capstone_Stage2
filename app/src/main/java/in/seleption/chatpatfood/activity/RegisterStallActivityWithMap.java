package in.seleption.chatpatfood.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edmodo.rangebar.RangeBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.seleption.Utility.DBUtility;
import in.seleption.Utility.JsonHelper;
import in.seleption.adapter.ImageAdapter;
import in.seleption.chatpatfood.BuildConfig;
import in.seleption.chatpatfood.R;
import in.seleption.constant.AppConstant;
import in.seleption.model.Menu;
import in.seleption.model.Stall;

/**
 * Created by Lokesh on 28-11-2015.
 */
public class RegisterStallActivityWithMap extends FragmentActivity implements OnMapReadyCallback {

    /*Set Tag Value for Class Identification*/
    private String TAG = "RegisterStallActivity";

    @Bind(R.id.et_username)
    EditText etName;

    @Bind(R.id.et_number)
    EditText etNumber;

    @Bind(R.id.et_other)
    EditText etOther;

    @Bind(R.id.rangebar_time)
    RangeBar rangeBar;

    @Bind(R.id.tv_lable_time_range)
    TextView tvTimeRange;

    @Bind(R.id.gv_category_menu)
    GridView gvCategoryMenu;

    @Bind(R.id.ib_stall_image)
    ImageView ibStallPics;

    /*photo path location to store*/
    private String mCurrentPhotoPath;

    private GoogleMap mMap;

    /*Request Id for camera*/
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private int MY_LOCATION_REQUEST_CODE = 1;

    /*-------------------------------*/
    private int defaultStartTime = 10;
    private int defaultEndTime = 18;

    private Stall stall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_stalls);

        /*Initialise Butterknife lib for Injection*/
        ButterKnife.bind(this);

        /*Initialise Activity*/
        initialise();

        /*make by default keyboard popup down*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    /*Initialise all respective variable*/
    private void initialise() {

        stall = new Stall();
        stall.setStart_time(defaultStartTime);
        stall.setEnd_time(defaultEndTime);

//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        /*set Default Value*/
        String time = String.format(getString(R.string.label_tv_timing), defaultStartTime, defaultEndTime);
        tvTimeRange.setText(time);

        rangeBar.setThumbIndices(defaultStartTime, defaultEndTime);
        // Sets the display values of the indices
        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onIndexChangeListener(RangeBar rangeBar, int leftThumbIndex, int rightThumbIndex) {

                /*Rangebar Start form 0 so increment by 1 to match with expected value*/
                String time = String.format(getString(R.string.label_tv_timing), leftThumbIndex + 1, rightThumbIndex + 1);
                tvTimeRange.setText(time);

                stall.setStart_time(leftThumbIndex + 1);
                stall.setEnd_time(rightThumbIndex + 1);
            }
        });
        /*--------------------------------*/

        /*Define all available option for stall entry*/
        TypedArray typedArray = getResources().obtainTypedArray(R.array.arr_drawable_menu);
        String[] home_menu = getResources().getStringArray(R.array.arr_home_food_name);

        List<Menu> items = new ArrayList<>();
        int count = 0;
        for (String name : home_menu) {
            Menu item = new Menu(count + 1, typedArray.getResourceId(count, 0), name);
            count++;
            items.add(item);
        }

        /*make resource free*/
        typedArray.recycle();

//        /*Assign all related category to grid-view*/
//        ImageAdapter imageAdapter = new ImageAdapter(this, items, onClickFoodMenu);
//        gvCategoryMenu.setAdapter(imageAdapter);
    }


    OnClickFoodMenu onClickFoodMenu = new OnClickFoodMenu() {
        @Override
        public void onClickFoodMenu(String name, int position, boolean is_add_remove) {
            List<Menu> menu = stall.getMenu();
            if (menu == null) {
                menu = new ArrayList<>();
            }

            if (is_add_remove) {
                menu.add(new Menu(position, name));
            } else {
                int i = 0;
                for (Menu temp : menu) {
                    if (temp.getName().equalsIgnoreCase(name)) {
                        menu.remove(i);
                        break;
                    }
                    i++;
                }
            }
            stall.setMenu(menu);
            if (BuildConfig.DEBUG) {
                Toast.makeText(RegisterStallActivityWithMap.this, JsonHelper.ConvertToJson(menu), Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationChangeListener(myLocationChangeListener);

            } else {

                // Permission was denied. Display an error message.
                Toast.makeText(RegisterStallActivityWithMap.this, "Without Location permission we can not register you", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT > 22) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationChangeListener(myLocationChangeListener);

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_LOCATION_REQUEST_CODE);
            }
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationChangeListener(myLocationChangeListener);
        }

    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            if (mMap != null) {
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 5.0f));

                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(loc).title(etName.getText().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15));
                stall.setLatittude(loc.latitude);
                stall.setLongitude(loc.longitude);

                if (BuildConfig.DEBUG) {
                    Toast.makeText(RegisterStallActivityWithMap.this, "location->" + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    /*Camera launch*/
    @OnClick(R.id.ib_stall_image)
    void dispatchTakePictureIntent(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                if (AppConstant.DEBUG)
                    Log.d(TAG, "dispatchTakePictureIntent: " + ex.toString());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic();
        }
    }

    /*Create Image File*/
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        //Uri uri = Uri.parse("file:" + image.getAbsolutePath());
        return image;
    }

    /*Scale Down Picture*/
    private void setPic() {
        // Get the dimensions of the View
        int targetW = ibStallPics.getMeasuredWidth();
        int targetH = ibStallPics.getMeasuredHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        bmOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = 4;
        if (targetH != 0 && targetW != 0)
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        /*Log*/
        LogD(mCurrentPhotoPath);

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        File f = new File(mCurrentPhotoPath);
        try {
            FileOutputStream fos = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
        } catch (FileNotFoundException e) {
            LogD(e.toString());
            e.printStackTrace();
        }
        ibStallPics.setImageBitmap(bitmap);
    }

    @OnClick(R.id.btn_submit)
    public void onSubmit(View v) {
        String name = etName.getText().toString();
        if (name == null || name.length() < 2) {
            Toast.makeText(RegisterStallActivityWithMap.this, "Enter Shop name correctly", Toast.LENGTH_SHORT).show();
            return;
        }
        String number = etNumber.getText().toString();
        if (!Pattern.matches("\\d{10}", number)) {
            Toast.makeText(RegisterStallActivityWithMap.this, "Enter mobile number correctly", Toast.LENGTH_SHORT).show();
            return;
        }

        String other = etOther.getText().toString();
        stall.setOthers(other);

        stall.setStall_name(name);
        stall.setMobile_no(number);
        stall.setUrl(mCurrentPhotoPath);

        DBUtility.insertSingleStall(getApplicationContext(), stall);
        if (BuildConfig.DEBUG) {
            Toast.makeText(RegisterStallActivityWithMap.this, mMap.getMyLocation().getLatitude() + "-" + mMap.getMyLocation().getLongitude(), Toast.LENGTH_SHORT).show();
        }
    }

    private void LogD(String msg) {
        if (AppConstant.DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public interface OnClickFoodMenu {
        void onClickFoodMenu(String name, int position, boolean add_remove);
    }
}
