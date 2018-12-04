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
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.Reclamo;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class NuevoReclamoFragmentRobolectricTest {

    private NuevoReclamoFragment nuevoReclamoFragment;

    @Before
    public void setUp() throws Exception {
        nuevoReclamoFragment = new NuevoReclamoFragment();
        SupportFragmentTestUtil.startVisibleFragment(nuevoReclamoFragment);
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
        tipoReclamo.setSelection(0);
        imagen.setImageDrawable(null);

        assertTrue(!btnGuardar.isEnabled());

        //SPINNER = CALLE_EN_MAL_ESTADO
        tvCoord.setText("1;1");
        reclamoDesc.setText("");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(3);
        imagen.setImageDrawable(null);

        assertTrue(!btnGuardar.isEnabled());
    }

    @Test
    public void testNoHayDesc() throws Exception{
        TextView tvCoord = (TextView) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_coord);
        EditText reclamoDesc = (EditText) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_desc);
        EditText mail = (EditText) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_mail);
        Spinner tipoReclamo = (Spinner) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_tipo);
        Button btnGuardar = (Button) nuevoReclamoFragment.getActivity().findViewById(R.id.btnGuardar);
        ArrayAdapter<Reclamo.TipoReclamo> tipoReclamoAdapter;
        tipoReclamoAdapter = new ArrayAdapter<Reclamo.TipoReclamo>(nuevoReclamoFragment.getActivity(), android.R.layout.simple_spinner_item, Reclamo.TipoReclamo.values());
        tipoReclamoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoReclamo.setAdapter(tipoReclamoAdapter);

        //SPINNER = SEMAFOROS
        tvCoord.setText("1;1");
        reclamoDesc.setText("");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(1);

        assertTrue(!btnGuardar.isEnabled());

        //SPINNER = ILUMINACION
        tvCoord.setText("1;1");
        reclamoDesc.setText("");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(2);

        assertTrue(!btnGuardar.isEnabled());

        //SPINNER = RESIDUOS
        tvCoord.setText("1;1");
        reclamoDesc.setText("");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(4);

        assertTrue(!btnGuardar.isEnabled());

        //SPINNER = RUIDOS_MOLESTOS
        tvCoord.setText("1;1");
        reclamoDesc.setText("");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(5);

        assertTrue(!btnGuardar.isEnabled());

        //SPINNER = OTROS
        tvCoord.setText("1;1");
        reclamoDesc.setText("");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(6);

        assertTrue(!btnGuardar.isEnabled());
    }

    @Test
    public void testDescMenorA8() throws Exception{
        TextView tvCoord = (TextView) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_coord);
        EditText reclamoDesc = (EditText) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_desc);
        EditText mail = (EditText) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_mail);
        Spinner tipoReclamo = (Spinner) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_tipo);
        Button btnGuardar = (Button) nuevoReclamoFragment.getActivity().findViewById(R.id.btnGuardar);
        ArrayAdapter<Reclamo.TipoReclamo> tipoReclamoAdapter;
        tipoReclamoAdapter = new ArrayAdapter<Reclamo.TipoReclamo>(nuevoReclamoFragment.getActivity(), android.R.layout.simple_spinner_item, Reclamo.TipoReclamo.values());
        tipoReclamoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoReclamo.setAdapter(tipoReclamoAdapter);

        //SPINNER = SEMAFOROS
        tvCoord.setText("1;1");
        reclamoDesc.setText("desc");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(1);

        assertTrue(!btnGuardar.isEnabled());

        //SPINNER = ILUMINACION
        tvCoord.setText("1;1");
        reclamoDesc.setText("desc");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(2);

        assertTrue(!btnGuardar.isEnabled());

        //SPINNER = RESIDUOS
        tvCoord.setText("1;1");
        reclamoDesc.setText("desc");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(4);

        assertTrue(!btnGuardar.isEnabled());

        //SPINNER = RUIDOS_MOLESTOS
        tvCoord.setText("1;1");
        reclamoDesc.setText("desc");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(5);

        assertTrue(!btnGuardar.isEnabled());

        //SPINNER = OTROS
        tvCoord.setText("1;1");
        reclamoDesc.setText("desc");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(6);

        assertTrue(!btnGuardar.isEnabled());
    }

    @Test
    public void testHayDesc() throws Exception{
        TextView tvCoord = (TextView) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_coord);
        EditText reclamoDesc = (EditText) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_desc);
        EditText mail = (EditText) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_mail);
        Spinner tipoReclamo = (Spinner) nuevoReclamoFragment.getActivity().findViewById(R.id.reclamo_tipo);
        Button btnGuardar = (Button) nuevoReclamoFragment.getActivity().findViewById(R.id.btnGuardar);
        ArrayAdapter<Reclamo.TipoReclamo> tipoReclamoAdapter;
        tipoReclamoAdapter = new ArrayAdapter<Reclamo.TipoReclamo>(nuevoReclamoFragment.getActivity(), android.R.layout.simple_spinner_item, Reclamo.TipoReclamo.values());
        tipoReclamoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoReclamo.setAdapter(tipoReclamoAdapter);

        //SPINNER = SEMAFOROS
        tvCoord.setText("1;1");
        reclamoDesc.setText("Descripción Reclamo");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(1);

        assertTrue(btnGuardar.isEnabled());

        //SPINNER = ILUMINACION
        tvCoord.setText("1;1");
        reclamoDesc.setText("Descripción Reclamo");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(2);

        assertTrue(btnGuardar.isEnabled());

        //SPINNER = RESIDUOS
        tvCoord.setText("1;1");
        reclamoDesc.setText("Descripción Reclamo");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(4);

        assertTrue(btnGuardar.isEnabled());

        //SPINNER = RUIDOS_MOLESTOS
        tvCoord.setText("1;1");
        reclamoDesc.setText("Descripción Reclamo");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(5);

        assertTrue(btnGuardar.isEnabled());

        //SPINNER = RUIDOS_MOLESTOS
        tvCoord.setText("1;1");
        reclamoDesc.setText("Descripción Reclamo");
        mail.setText("dam@facu.com");
        tipoReclamo.setSelection(6);

        assertTrue(btnGuardar.isEnabled());
    }
}
