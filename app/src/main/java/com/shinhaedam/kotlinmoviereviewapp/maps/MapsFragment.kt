package com.shinhaedam.kotlinmoviereviewapp.maps

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.shinhaedam.kotlinmoviereviewapp.MainActivity
import com.shinhaedam.kotlinmoviereviewapp.R

class MapsFragment : Fragment() {

    private val REQUEST_LOCATION_PERMISSION = 1

    private val callback = OnMapReadyCallback { googleMap ->
        val kwangwoon = LatLng(37.62393642270578, 127.0618384555333)
        val zoomLevel = 15f

        val locationManager: LocationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        val locationListner = LocationListener { p0 ->
            val current = LatLng(p0.latitude, p0.longitude)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, zoomLevel))
        }

        // 디폴트 카메라 위치 : 광운대역
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kwangwoon, zoomLevel))

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        } else { // 위치 권한 있으면
            // 위치로 카메라 이동
            locationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                3000L,
                30f,
                locationListner
            )

            // 내위치이동 버튼 생성
            googleMap.isMyLocationEnabled = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 액션바 타이틀 설정
        val activity: MainActivity = activity as MainActivity
        activity?.setActionBarTitle("현재 위치")

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}