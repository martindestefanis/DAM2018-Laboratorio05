package ar.edu.utn.frsf.isi.dam.laboratorio05;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.MyDatabase;
import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.Reclamo;
import ar.edu.utn.frsf.isi.dam.laboratorio05.modelo.ReclamoDao;

public class NuevoReclamoFragment extends Fragment {

    public static final int REQUEST_CAMERA_CODE = 1995;
    public static final int RESULT_OK = -1;
    private static final String LOG_TAG = "Grabar";

    public interface OnNuevoLugarListener {
        public void obtenerCoordenadas();
    }

    public void setListener(OnNuevoLugarListener listener) {
        this.listener = listener;
    }

    private Reclamo reclamoActual;
    private ReclamoDao reclamoDao;

    private EditText reclamoDesc;
    private EditText mail;
    private Spinner tipoReclamo;
    private TextView tvCoord;
    private Button buscarCoord;
    private Button btnGuardar;
    private Button btnFoto;
    private Button btnGrabar;
    private Button btnReproducir;
    private OnNuevoLugarListener listener;
    private ImageView imagen;
    private String pathFoto;
    private String pathAudio;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private String mFileName;
    private Boolean grabando = false;
    private Boolean reproduciendo = false;

    private ArrayAdapter<Reclamo.TipoReclamo> tipoReclamoAdapter;

    public NuevoReclamoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        reclamoDao = MyDatabase.getInstance(this.getActivity()).getReclamoDao();

        View v = inflater.inflate(R.layout.fragment_nuevo_reclamo, container, false);

        reclamoDesc = (EditText) v.findViewById(R.id.reclamo_desc);
        mail = (EditText) v.findViewById(R.id.reclamo_mail);
        tipoReclamo = (Spinner) v.findViewById(R.id.reclamo_tipo);
        tvCoord = (TextView) v.findViewById(R.id.reclamo_coord);
        buscarCoord = (Button) v.findViewById(R.id.btnBuscarCoordenadas);
        btnGuardar = (Button) v.findViewById(R.id.btnGuardar);
        btnFoto = (Button) v.findViewById(R.id.btnFoto);
        imagen = (ImageView) v.findViewById(R.id.imageView);
        btnGrabar = (Button) v.findViewById(R.id.btnGrabar);
        btnReproducir = (Button) v.findViewById(R.id.btnReproducir);
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath()+"/audiorecordtest.3gp";

        tipoReclamoAdapter = new ArrayAdapter<Reclamo.TipoReclamo>(getActivity(), android.R.layout.simple_spinner_item, Reclamo.TipoReclamo.values());
        tipoReclamoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoReclamo.setAdapter(tipoReclamoAdapter);

        int idReclamo = 0;
        if (getArguments() != null) {
            idReclamo = getArguments().getInt("idReclamo", 0);
        }

        cargarReclamo(idReclamo);

        boolean edicionActivada = !tvCoord.getText().toString().equals("0;0");
        reclamoDesc.setEnabled(edicionActivada);
        mail.setEnabled(edicionActivada);
        tipoReclamo.setEnabled(edicionActivada);
        btnGuardar.setEnabled(edicionActivada);

        buscarCoord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.obtenerCoordenadas();

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveOrUpdateReclamo();
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacarGuardarFoto();
            }
        });

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(grabando){
                    ((Button) btnGrabar).setText("GRABAR");
                    grabando=false;
                    terminarGrabar();
                }
                else {
                    ((Button) btnGrabar).setText("GRABANDO");
                    grabando = true;
                    if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.RECORD_AUDIO},9999);
                        return;
                    }
                    else{
                        grabar();
                    }
                }
            }
        });

        btnReproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reproduciendo){
                    ((Button) btnReproducir).setText("REPRODUCIR");
                    reproduciendo=false;
                    terminarReproducir();
                }
                else{
                    ((Button) btnReproducir).setText("PAUSAR");
                    reproduciendo=true;
                    reproducir();
                }
            }
        });
        return v;
    }

    private void cargarReclamo(final int id) {
        if (id > 0) {
            Runnable hiloCargaDatos = new Runnable() {
                @Override
                public void run() {
                    reclamoActual = reclamoDao.getById(id);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mail.setText(reclamoActual.getEmail());
                            tvCoord.setText(reclamoActual.getLatitud() + ";" + reclamoActual.getLongitud());
                            reclamoDesc.setText(reclamoActual.getReclamo());
                            Reclamo.TipoReclamo[] tipos = Reclamo.TipoReclamo.values();
                            for (int i = 0; i < tipos.length; i++) {
                                if (tipos[i].equals(reclamoActual.getTipo())) {
                                    tipoReclamo.setSelection(i);
                                    break;
                                }
                            }
                            File file = new File(reclamoActual.getPathFoto());
                            Bitmap imageBitmap = null;
                            try {
                                imageBitmap = MediaStore.Images.Media
                                        .getBitmap(getActivity().getContentResolver(),
                                                Uri.fromFile(file));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (imageBitmap != null) {
                                imagen.setImageBitmap(imageBitmap);
                            }
                        }
                    });
                }
            };
            Thread t1 = new Thread(hiloCargaDatos);
            t1.start();
        } else {
            String coordenadas = "0;0";
            if (getArguments() != null) coordenadas = getArguments().getString("latLng", "0;0");
            tvCoord.setText(coordenadas);
            reclamoActual = new Reclamo();
        }

    }

    private void saveOrUpdateReclamo() {
        reclamoActual.setEmail(mail.getText().toString());
        reclamoActual.setReclamo(reclamoDesc.getText().toString());
        reclamoActual.setTipo(tipoReclamoAdapter.getItem(tipoReclamo.getSelectedItemPosition()));
        if (tvCoord.getText().toString().length() > 0 && tvCoord.getText().toString().contains(";")) {
            String[] coordenadas = tvCoord.getText().toString().split(";");
            reclamoActual.setLatitud(Double.valueOf(coordenadas[0]));
            reclamoActual.setLongitud(Double.valueOf(coordenadas[1]));
        }
        reclamoActual.setPathFoto(pathFoto);
        reclamoActual.setPathAudio(mFileName);
        Runnable hiloActualizacion = new Runnable() {
            @Override
            public void run() {

                if (reclamoActual.getId() > 0) reclamoDao.update(reclamoActual);
                else reclamoDao.insert(reclamoActual);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // limpiar vista
                        mail.setText(R.string.texto_vacio);
                        tvCoord.setText(R.string.texto_vacio);
                        reclamoDesc.setText(R.string.texto_vacio);
                        imagen.setImageResource(0);
                        getActivity().getFragmentManager().popBackStack();
                    }
                });
            }
        };
        Thread t1 = new Thread(hiloActualizacion);
        t1.start();
    }

    private void sacarGuardarFoto() {
        Intent camaraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File picFile = null;
        try {
            picFile = createImageFile();
        }
        catch (IOException ex) {
        }
        Uri uri = FileProvider.getUriForFile(getActivity().getApplicationContext(), getActivity().getApplicationContext().getPackageName() + ".provider", picFile);
        camaraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        camaraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(camaraIntent, REQUEST_CAMERA_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CAMERA_CODE){
                File file = new File(pathFoto);
                Bitmap imageBitmap = null;
                try {
                    imageBitmap = MediaStore.Images.Media
                            .getBitmap(getActivity().getContentResolver(),
                                    Uri.fromFile(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (imageBitmap != null) {
                    imagen.setImageBitmap(imageBitmap);
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                  imageFileName, /* prefix */
                 ".jpg", /* suffix */
                 dir /* directory */
        );
        pathFoto = image.getAbsolutePath();
        return image;
    }

    private void grabar(){
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        mRecorder.start();
    }

    private void terminarGrabar(){
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    private void reproducir() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void terminarReproducir() {
        mPlayer.release();
        mPlayer = null;
    }
}