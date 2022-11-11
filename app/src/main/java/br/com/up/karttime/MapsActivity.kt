package br.com.up.karttime

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import br.com.up.karttime.databinding.ActivityMapsBinding
import br.com.up.karttime.model.Race
import br.com.up.karttime.repository.RaceRepository
import com.google.android.gms.location.*
import com.google.firebase.Timestamp

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        RaceRepository.instance().get()

        if(checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED){

            getUserLocation()

        }else{
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1000
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 1000 &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED){

                getUserLocation()
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    @SuppressLint("NewApi", "MissingPermission")
    fun getUserLocation(){

        val locationRequest  =
            LocationRequest.Builder(5000).build()

        val locationCallback = object  : LocationCallback(){

            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)


                if(locationRequest != null){

                    val sydney = LatLng(p0.locations[0].latitude,

                        p0.locations[0].longitude)
                    mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

                }

            }
        }

        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)

         fusedLocationProviderClient
             .requestLocationUpdates(
                 locationRequest,
                 locationCallback,
                 Looper.getMainLooper())





    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}