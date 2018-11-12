package ar.edu.utn.frsf.isi.dam.laboratorio05;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.MyDatabase;
import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.Reclamo;
import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.ReclamoDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap miMapa;
    private int tipoMapa;
    private LatLng coordenadas;
    private OnMapaListener listener;
    private ReclamoDao reclamoDAO;
    private List<Reclamo> listaReclamos;

    public MapaFragment() {
        // Required empty public constructor
    }
    public void setListener(OnMapaListener listener) {
        this.listener = listener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        tipoMapa = 0;
        Bundle argumentos = getArguments();
        if(argumentos != null) {
            tipoMapa = argumentos.getInt("tipo_mapa",0);
        }
        getMapAsync(this);
        return rootView;
    }
    @Override
    public void onMapReady(GoogleMap map) {
        miMapa = map;
        actualizarMapa();

        switch (tipoMapa){
            case 1:
                miMapa.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        listener.coordenadasSeleccionadas(latLng);
                    }
                });
            case 2:
                cargarMapaConReclamos();
        }
    }
    private void actualizarMapa() {
        if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    9999);
            return;
        }
        miMapa.setMyLocationEnabled(true);
    }

    private void cargarMapaConReclamos(){
        reclamoDAO = MyDatabase.getInstance(getContext()).getReclamoDao();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                listaReclamos = reclamoDAO.getAll();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for(Reclamo r : listaReclamos){
                            Marker marker = miMapa.addMarker(new MarkerOptions()
                                                .position(new LatLng(r.getLatitud(),r.getLongitud()))
                                                .title(r.getId() + "[" + r.getTipo().toString() + "]")
                                                .snippet(r.getReclamo()));
                            builder.include(marker.getPosition());
                        }
                        LatLngBounds LIMITE = builder.build();
                        miMapa.moveCamera(CameraUpdateFactory.newLatLngBounds(LIMITE, 10));
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public interface OnMapaListener {
        public void coordenadasSeleccionadas(LatLng c);
    }

}
