package com.app.backyard;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import adapters.PlaceInfoArrayAdapter;
import butterknife.BindView;
import model.PlaceInfo;
import utils.Constants;
import utils.GpsStatusDetector;
import utils.MainApplication;

public class LocationSearchActivity extends BaseActivity implements TextWatcher,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener,
        GpsStatusDetector.GpsStatusDetectorCallBack, OnMapReadyCallback {

    private static final String TAG_RESULT = "predictions";
    final int LOCATION_CHECK = 1;
    String url;

    @BindView(R.id.ed_search)
    EditText etSearch;
    @BindView(R.id.txt_cancel)
    TextView txtCancel;
    @BindView(R.id.img_clear)
    ImageView imgClear;

    TextView tvMyLocation, tvLocationOnMap;
    LinearLayout llSetLocationOnMap, llMyLocation;

    @BindView(R.id.search_list)
    ListView listView;
    ArrayList<PlaceInfo> placeInfoArrayList = new ArrayList<>();
    PlaceInfoArrayAdapter mAdapter;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    ProgressDialog progDailog;
    private int LOCATION = 3;
    private String placeId;
    private double mLatitude;
    private double mLongitude;
    private String mAddress;
    private GpsStatusDetector mGpsStatusDetector;

    @Override
    protected int getContentView() {
        return R.layout.activity_location_search;
    }

    @Override
    protected void initUI() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/semibold.ttf");
        Typeface typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/regular.ttf");

        etSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.045));
        etSearch.setTypeface(typefaceRegular);
        etSearch.setPadding(mWidth / 32, mWidth / 32, 0, mWidth / 32);

        imgClear.setPadding(mWidth / 64, mWidth / 32, mWidth / 64, mWidth / 32);

        txtCancel.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.035));
        txtCancel.setPadding(mWidth / 32, mWidth / 32, 0, mWidth / 32);

        showHeader();

        mAdapter = new PlaceInfoArrayAdapter(LocationSearchActivity.this, placeInfoArrayList);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (connectedToInternet()) {
                    progDailog = ProgressDialog.show(mContext, getString(R.string.Please_wait), getString(R.string.Fetching_0Location), true);
                    String placeId = placeInfoArrayList.get(position - 1).getPlaceId();
                    getLocationById(placeId, position - 1);
                    Constants.closeKeyboard(mContext, llMyLocation);
                } else
                    showInternetAlert(llMyLocation);
            }
        });

    }

    void showHeader() {
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header_location, listView,
                false);

        tvLocationOnMap = (TextView) header.findViewById(R.id.tvset_location_on_map);
        tvLocationOnMap.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

        tvMyLocation = (TextView) header.findViewById(R.id.textView_my_location);
        tvMyLocation.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (mWidth * 0.040));

        llSetLocationOnMap = (LinearLayout) header.findViewById(R.id.ll_set_location_on_map);

        llMyLocation = (LinearLayout) header.findViewById(R.id.ll_my_location);

        listView.addHeaderView(header, null, false);
    }

    public void getLocationById(String placeId, final int position) {

        Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId)
                .setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(PlaceBuffer places) {
                        if (places.getStatus().isSuccess()) {
                            progDailog.dismiss();
                            final Place myPlace = places.get(0);
                            LatLng queriedLocation = myPlace.getLatLng();
                            mLatitude = queriedLocation.latitude;
                            mLongitude = queriedLocation.longitude;
                            Intent intent = new Intent();
                            intent.putExtra("latitude", String.valueOf(mLatitude));
                            intent.putExtra("longitude", String.valueOf(mLongitude));
                            intent.putExtra("address", placeInfoArrayList.get(position).getName());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        places.release();
                    }
                });

    }

    @Override
    protected void onCreateStuff() {
        if (!connectedToInternet())
            showInternetAlert(txtCancel);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_CHECK);
        } else {
            mGpsStatusDetector = new GpsStatusDetector(this);
            mGpsStatusDetector.checkGpsStatus(2);
        }
    }

    @Override
    protected void initListener() {
        llSetLocationOnMap.setOnClickListener(this);
        llMyLocation.setOnClickListener(this);
        imgClear.setOnClickListener(this);
        etSearch.addTextChangedListener(this);
        txtCancel.setOnClickListener(this);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        //stop location updates
        if (connectedToInternet())
            getAddress(location.getLatitude(), location.getLongitude());
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                googleMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            googleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    protected Context getContext() {
        return LocationSearchActivity.this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_set_location_on_map:
                Intent intent = new Intent(this, LocationMapActivity.class);
                startActivityForResult(intent, LOCATION);
                break;
            case R.id.txt_cancel:
                Constants.closeKeyboard(mContext, txtCancel);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                break;
            case R.id.img_clear:
                if (placeInfoArrayList != null)
                    placeInfoArrayList.clear();
                etSearch.setText("");
                break;
            case R.id.ll_my_location:
                if (tvMyLocation.getText().toString().isEmpty()) {
                    showAlert(llMyLocation, getString(R.string.Please_select_location));
                } else {
                    moveBack();
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            imgClear.setVisibility(View.VISIBLE);
            placeInfoArrayList = new ArrayList<PlaceInfo>();
            updateList(s.toString());
        } else {
            if (mAdapter != null) {
                placeInfoArrayList.clear();
                mAdapter.notifyDataSetChanged();
            }
            imgClear.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void updateList(String place) {
        String input = "";

        try {
            input = "input=" + URLEncoder.encode(place, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

//        https://maps.googleapis.com/maps/api/place/autocomplete/json?input=ncm+
//        &location=30.6577781,76.7327138&radius=1000&types=geocode&language=en&key=AIzaSyBAPTeUV-04HFtCIt3Ac8MtVFqim9CDlV4

        String output = "json";
//        String parameter = input + "&types=geocode&sensor=true&key=" + getResources().getString(R.string.google_maps_key);
//        url = "https://maps.googleapis.com/maps/api/place/autocomplete/" + output + "?" + parameter;

        String parameter = input + "&radius=1000&sensor=true&key=" + getResources().getString(R.string.google_maps_key);
        url = "https://maps.googleapis.com/maps/api/place/autocomplete/" + output + "?" + parameter;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                System.out.println("Response iss:: " + response);
                try {
                    JSONArray ja = response.getJSONArray(TAG_RESULT);

                    if (ja.length() > 0)
                        placeInfoArrayList.clear();

                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject c = ja.getJSONObject(i);
                        String description = c.getString("description");
                        placeId = c.getString("place_id");
                        placeInfoArrayList.add(new PlaceInfo(description, placeId));
                    }
                    mAdapter = new PlaceInfoArrayAdapter(LocationSearchActivity.this, placeInfoArrayList);
                    listView.setAdapter(mAdapter);
                } catch (Exception e) {
                    Log.e("Exce  = ", e + "");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
//                    Toast.makeText(this, "Check Internet Connection !", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        MainApplication.getInstance().addToRequestQueue(jsonObjReq, "jreq");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CHECK:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        onCreateStuff();
                    }
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    void getAddress(double latitude, double longitude) {

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null && addresses.size() > 0) {
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getSubLocality() + " , " + addresses.get(0).getSubAdminArea();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();

            tvMyLocation.setText(address + "," + city + "," + state + "," + country);
            mLatitude = latitude;
            mLongitude = longitude;
            mAddress = "";
            mAddress = address + "," + city + "," + state + "," + country;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == LOCATION) {
                mAddress = data.getStringExtra("address");
                mLatitude = Double.parseDouble(data.getStringExtra("latitude"));
                mLongitude = Double.parseDouble(data.getStringExtra("longitude"));
                moveBack();
            }
        }
        if (ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                mGpsStatusDetector.checkOnActivityResult(requestCode, resultCode);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void moveBack() {
        Intent intent = new Intent();
        intent.putExtra("latitude", String.valueOf(mLatitude));
        intent.putExtra("longitude", String.valueOf(mLongitude));
        intent.putExtra("address", mAddress);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    }


    @Override
    public void onGpsSettingStatus(boolean enabled) {
        if (enabled)
            buildGoogleApiClient();
    }

    @Override
    public void onGpsAlertCanceledByUser() {

    }

    @Override
    public void showLocationScreen() {

    }
}
