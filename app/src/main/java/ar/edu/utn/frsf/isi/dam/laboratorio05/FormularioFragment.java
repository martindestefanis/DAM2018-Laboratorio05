package ar.edu.utn.frsf.isi.dam.laboratorio05;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.MyDatabase;
import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.Reclamo;
import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.ReclamoDao;

public class FormularioFragment extends Fragment {

    private ReclamoDao reclamoDao;

    private Spinner tipoReclamo;
    private Button buscar;
    private ArrayAdapter<Reclamo.TipoReclamo> tipoReclamoAdapter;
    public FormularioFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        reclamoDao = MyDatabase.getInstance(this.getActivity()).getReclamoDao();

        View v = inflater.inflate(R.layout.fragment_formulario, container, false);
        tipoReclamo= (Spinner) v.findViewById(R.id.tipo_reclamo);
        buscar= (Button) v.findViewById(R.id.btnBuscar);

        tipoReclamoAdapter = new ArrayAdapter<Reclamo.TipoReclamo>(getActivity(),android.R.layout.simple_spinner_item,Reclamo.TipoReclamo.values());
        tipoReclamoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoReclamo.setAdapter(tipoReclamoAdapter);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reclamoTipo = tipoReclamo.getSelectedItem().toString();
                mostrarMapa(reclamoTipo);
            }
        });






       /* buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                public void mostrarMapa(int id) {
                    Fragment f = new MapaFragment();
                    Bundle args = new Bundle();
                    // setear los parametros tipo_mapa y idReclamo en el Bundle args
                    args.putInt("tipo_mapa", 5);
                    args.putString("tipoReclamo", tipoReclamo.getSelectedItem().toString()); //VER
                    f.setArguments(args);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contenido, f)
                            .commit();
                }
            }
        });*/

        return v;

    }
    public void mostrarMapa(String reclamoTipo) {
        Fragment f = new MapaFragment();
        Bundle args = new Bundle();
        // setear los parametros tipo_mapa y idReclamo en el Bundle args
        args.putInt("tipo_mapa", 5);
        args.putString("tipoReclamo", reclamoTipo); //VER
        f.setArguments(args);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenido, f)
                .commit();
    }


}
