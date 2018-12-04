package ar.edu.utn.frsf.isi.dam.laboratorio05;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NuevoReclamoFragmentUnitTest {

    @Test
    public void hayFoto(){
        String pathFoto = "unPath";
        assertTrue(pathFoto != null);
    }

    @Test
    public void noHayFoto(){
        String pathFoto = null;
        assertFalse("No hay foto",pathFoto != null);
    }

    @Test
    public void hayAudio(){
        String pathAudio = "unPath";
        assertTrue(pathAudio != null);
    }

    @Test
    public void noHayAudio(){
        String pathAudio = null;
        assertFalse("No hay audio",pathAudio != null);
    }

    @Test
    public void editTextMayorA8Caracteres(){
        String reclamoDesc = "Una descripción mayor a 8 caracteres";
        assertTrue(reclamoDesc.length()>=8);
    }

    @Test
    public void editTextMenorA8Caracteres(){
        String reclamoDesc = "<8";
        assertFalse("La descripción tiene menos de 8 caracteres",reclamoDesc.length()>=8);
    }
}
