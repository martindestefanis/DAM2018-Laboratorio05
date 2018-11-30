package ar.edu.utn.frsf.isi.dam.laboratorio05;


import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.Reclamo;

import static org.junit.Assert.assertTrue;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
public class NuevoReclamoFragmentRobolectricTest {
    /*private MainActivity activity;*/
    private NuevoReclamoFragment nuevoReclamoFragment;

    @Before
    public void setUp() throws Exception {
        /*SupportFragmentController<NuevoReclamoFragment> controller = SupportFragmentController.of(nuevoReclamoFragment, MainActivity.class);
        controller.create().resume().get();*/

        nuevoReclamoFragment = new NuevoReclamoFragment();
        startFragment(nuevoReclamoFragment);
    }

    @Test
    public void testNoHayFoto() throws Exception{
        TextView tvCoord = (TextView) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_coord);
        EditText reclamoDesc = (EditText) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_desc);
        EditText mail = (EditText) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_mail);
        Spinner tipoReclamo = (Spinner) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_tipo);
        ImageView imagen = (ImageView) nuevoReclamoFragment.getActivity().findViewById(R.id.imageView);
        Button btnGuardar = (Button) nuevoReclamoFragment.getActivity().findViewById(R.id.btnGuardar);
        ArrayAdapter<Reclamo.TipoReclamo> tipoReclamoAdapter;
        tipoReclamoAdapter = new ArrayAdapter<Reclamo.TipoReclamo>(nuevoReclamoFragment.getActivity(), android.R.layout.simple_spinner_item, Reclamo.TipoReclamo.values());
        tipoReclamoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoReclamo.setAdapter(tipoReclamoAdapter);

        //SPINNER = VEREDAS
        tvCoord.setText("1;1");
        reclamoDesc.setText("");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(1);
        imagen.setImageDrawable(null);

        assertTrue(!btnGuardar.isEnabled());

        //SPINNER = CALLE_EN_MAL_ESTADO
        tvCoord.setText("1;1");
        reclamoDesc.setText("");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(4);
        imagen.setImageDrawable(null);

        assertTrue(!btnGuardar.isEnabled());
    }
}
