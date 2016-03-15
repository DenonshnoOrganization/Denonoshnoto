package com.example.chav.mapsproject.controller;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.chav.mapsproject.FilterSpotItem;
import com.example.chav.mapsproject.Message;
import com.example.chav.mapsproject.R;
import com.example.chav.mapsproject.Spot;
import com.example.chav.mapsproject.SpotManager;
import com.example.chav.mapsproject.UserManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
        , GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        AddSpotFragment.OnSpotSelectedListener, FilterSpotAdapter.FilterCallback {

    private boolean isLocated;
    private ImageButton mAddSpotButton;
    private ImageButton mAddFriendButton;
    private ImageButton mShowLocationButton;
    private GoogleMap mMap;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private HashMap<Long, Marker> mVisibleMarkers = new HashMap<Long, Marker>();
    private DrawerLayout mDrawerLayout;
    private RecyclerView mFilterRecyclerView;

    private LocationListener mListener;
    private GoogleApiClient mLocationClient;
    private ArrayList<FilterSpotItem> filterSpotItems = new ArrayList<>();

    private HashSet<Integer> mRemoveFromFilter = new HashSet<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SavedSharedPreference.getUsername(this).length() == 0) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        UserManager.getInstance(this).setCurrentUser(SavedSharedPreference.getUsername(this));
        createGoogleAPIClient();

        mShowLocationButton = (ImageButton) findViewById(R.id.show_location_button);
        mAddSpotButton = (ImageButton) findViewById(R.id.add_spot_button);
        mAddFriendButton = (ImageButton) findViewById(R.id.add_friend_button);
        mAddFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavedSharedPreference.clearUserName(v.getContext());
                AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
                EditText input = new EditText(v.getContext());
                input.setHint("please type username here");
                alertDialog.setTitle("title");
                alertDialog.setMessage(message);
                alertDialog.setView(input);


            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.maps_main_layout);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}
            @Override
            public void onDrawerOpened(View drawerView) {}
            @Override
            public void onDrawerClosed(View drawerView) {}
            @Override
            public void onDrawerStateChanged(int newState) {setMarkers();}
        });

        mFilterRecyclerView = (RecyclerView) findViewById(R.id.filter_spot_recycler_view);
        filterSpotItems.add(new FilterSpotItem(R.drawable.gas_color));
        filterSpotItems.add(new FilterSpotItem(R.drawable.shop_color));
        filterSpotItems.add(new FilterSpotItem(R.drawable.atm_color));
        filterSpotItems.add(new FilterSpotItem(R.drawable.pharmacy_color));
        filterSpotItems.add(new FilterSpotItem(R.drawable.animal_clinic));
        filterSpotItems.add(new FilterSpotItem(R.drawable.pizza_color));
        filterSpotItems.add(new FilterSpotItem(R.drawable.restaurant_color));
        filterSpotItems.add(new FilterSpotItem(R.drawable.casino_color));
        filterSpotItems.add(new FilterSpotItem(R.drawable.wi_fi));

        FilterSpotAdapter filterSpotAdapter = new FilterSpotAdapter(filterSpotItems, this);
        mFilterRecyclerView.setAdapter(filterSpotAdapter);
        mFilterRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Goeogle Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                setMarkers();
            }
        });
        mShowLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager lm = (LocationManager) getSystemService(v.getContext().LOCATION_SERVICE);
                if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Message.message(v.getContext(), "" + lm.isProviderEnabled(LocationManager.GPS_PROVIDER));
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("GPS is disabled on your device. Would you like to enable it")
                            .setCancelable(false)
                            .setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent callGPSSettingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(callGPSSettingsIntent);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                } else if(!isLocated){
                    activateLocationTracking();
                } else if (isLocated) {
                    stopLocationUpdates();
                }

            }
        });
        mAddSpotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLocated) {
                    AddSpotFragment d = new AddSpotFragment();
                    FragmentManager fm = getSupportFragmentManager();
                    d.show(fm, "sth");
                } else {
                    Message.message(v.getContext(), "Please check your current location first");
                }
            }
        });
    }

    private void setMarkers() {

        LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;

        for (Spot spot : SpotManager.getInstance(this).getAllSpots()) {
            LatLng latLng = new LatLng(spot.getLatitude(), spot.getLongitude());
            if (bounds.contains(new LatLng(spot.getLatitude(), spot.getLongitude())) && !mRemoveFromFilter.contains(spot.getImg())) {
                if (!mVisibleMarkers.containsKey(spot.getId())) {
                    MarkerOptions options = new MarkerOptions()
                            .position(latLng)
                            .title(spot.getName())
                            .icon(BitmapDescriptorFactory.fromResource(spot.getImg()));
                    mVisibleMarkers.put(spot.getId(), mMap.addMarker(options));
                }
            } else if (mVisibleMarkers.containsKey(spot.getId()) && mRemoveFromFilter.contains(spot.getImg())) {
                mVisibleMarkers.get(spot.getId()).remove();
                mVisibleMarkers.remove(spot.getId());

            } else if (mVisibleMarkers.containsKey(spot.getId())) {
                mVisibleMarkers.get(spot.getId()).remove();
                mVisibleMarkers.remove(spot.getId());
            }
        }
    }

//    private void setMarkers() {
//
//        Handler handler = new Handler(Looper.getMainLooper());
//
//        LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
//        double latitudeEast = bounds.northeast.latitude;
//        double longitudeEast = bounds.northeast.longitude;
//        double latitudeWest = bounds.southwest.latitude;
//        double longitudeWest = bounds.southwest.longitude;
//
//        //TODO add int icon to Spot and to ze database table
//        for (Spot spot : SpotManager.getInstance(this).getAllSpots()) {
//            LatLng latLng = new LatLng(spot.getLatitude(), spot.getLongitude());
//
//            Log.d("pos", "" + spot.getName() + (spot.getLongitude() > longitudeWest
//                    && spot.getLongitude() < longitudeEast
//                    && spot.getLatitude() > latitudeWest
//                    && spot.getLatitude() < latitudeEast));
//
//
//            if (spot.getLongitude() > longitudeWest //TODO SEE FAVORITES TO MAKE OPTIMIZATION HERE
//                    && spot.getLongitude() < longitudeEast
//                    && spot.getLatitude() > latitudeWest
//                    && spot.getLatitude() < latitudeEast) {
//            if (bounds.contains(new LatLng(spot.getLatitude(), spot.getLongitude()))) {
//
//                if (!mVisibleMarkers.containsKey(spot.getId())) {
//                    MarkerOptions options = new MarkerOptions()
//                            .position(latLng)
//                            .title(spot.getName())
//                            .icon(BitmapDescriptorFactory.fromResource(spot.getImg()));
//                    mVisibleMarkers.put(spot.getId(), mMap.addMarker(options));
//                }
//            } else if (mVisibleMarkers.containsKey(spot.getId())) {
//                mVisibleMarkers.get(spot.getId()).remove();
//                mVisibleMarkers.remove(spot.getId());
//            }
//        }
//    }

    private void activateLocationTracking() {
        mListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Message.message(MapsActivity.this, "Location Changed " + location.getLatitude() + " " + location.getLongitude());
                goToLocation(location.getLatitude(), location.getLongitude(), 15);
            }
        };

        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(3 * 5000)
                .setFastestInterval(5 * 1000);


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(request);

        builder.setAlwaysShow(true);

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        isLocated = true;
        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, request, mListener);
    }

    private void createGoogleAPIClient() {

        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getLastLocation();
        Message.message(this, "Connected!");

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void onStart() {
        mLocationClient.connect();
        Log.d("st", "started");
        super.onStart();
    }

    protected void onStop() {
        mLocationClient.disconnect();
        super.onStop();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
        LatLng latlng;
        if (currentLocation == null) {
            Message.message(this, "Couldn't connect");
            latlng = new LatLng(42.7, 23.3);
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlng, 15);

            mMap.moveCamera(update);
        } else {
            latlng = new LatLng(
                    currentLocation.getLatitude(),
                    currentLocation.getLongitude()
            );
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlng, 15);
            mMap.moveCamera(update);
        }
        setMarkers();
    }


    public void goToLocation(double lat, double lng, float zoom) {
        LatLng latlng = new LatLng(lat, lng);
        setMarkers();
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlng, zoom);
        mMap.moveCamera(update);
    }

    public void createNewSpot(String type, int image) {
        LocationServices.FusedLocationApi.removeLocationUpdates(mLocationClient, mListener);
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
        LatLng latlng = new LatLng(
                currentLocation.getLatitude(),
                currentLocation.getLongitude());
        addMarker( SpotManager.getInstance(this).addSpot(type, latlng, UserManager.getInstance(this).getUser(), image));
        stopLocationUpdates();

    }

    @Override
    public void onSpotSelected(String spotType, int image) {
        Log.d("asd", " gona crete " + spotType + " for ya");
        createNewSpot(spotType, image);
    }

    private void addMarker(Spot spot) {
        LatLng latLng = new LatLng(spot.getLatitude(), spot.getLongitude());
        MarkerOptions options = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(spot.getImg())).title(spot.getName());
        mVisibleMarkers.put(spot.getId(), mMap.addMarker(options));
        stopLocationUpdates();
        isLocated = false;
    }

    protected void stopLocationUpdates() {
        if (mLocationClient.isConnected() && isLocated) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mLocationClient, mListener);
            isLocated = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLocationClient.isConnected() && !isLocated) {
            activateLocationTracking();
        }
    }

    @Override
    public void onFilterCallback(Integer imageId) {
        if (mRemoveFromFilter.contains(imageId)) {
            mRemoveFromFilter.remove(imageId);
        } else {
            mRemoveFromFilter.add(imageId);
        }
    }

}
