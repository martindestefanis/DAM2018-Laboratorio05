package ar.edu.utn.frsf.isi.dam.laboratorio05;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NuevoReclamoFragmentEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        NuevoReclamoFragment nuevoReclamoFragment = new NuevoReclamoFragment();
        mActivityRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenido, nuevoReclamoFragment)
                .commit();
    }

    @Test
    public void testNoHayFoto(){
        onView(withId(R.id.reclamo_coord)).perform(setText("1;1"));
        onView(withId(R.id.reclamo_mail)).perform(setText("dam@facu.com"));
        onView(withId(R.id.reclamo_desc)).perform(setText(""));

        //SPINNER = VEREDAS
        onData(hasToString(startsWith("VEREDAS"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(not(isEnabled())));

        //SPINNER = CALLE_EN_MAL_ESTADO
        onData(hasToString(startsWith("CALLE"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(not(isEnabled())));
    }

    @Test
    public void testHayDesc(){
        onView(withId(R.id.reclamo_coord)).perform(setText("1;1"));
        onView(withId(R.id.reclamo_mail)).perform(setText("dam@facu.com"));
        onView(withId(R.id.reclamo_desc)).perform(setText("Descripción Reclamo"));

        //SPINNER = SEMAFOROS
        onData(hasToString(startsWith("SEMAFOROS"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));

        //SPINNER = ILUMINACION
        onData(hasToString(startsWith("ILUMINACION"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));

        //SPINNER = RESIDUOS
        onData(hasToString(startsWith("RESIDUOS"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));

        //SPINNER = RUIDOS_MOLESTOS
        onData(hasToString(startsWith("RUIDOS"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));

        //SPINNER = OTROS
        onData(hasToString(startsWith("OTRO"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));
    }

    @Test
    public void testNoHayDesc(){
        onView(withId(R.id.reclamo_coord)).perform(setText("1;1"));
        onView(withId(R.id.reclamo_mail)).perform(setText("dam@facu.com"));
        onView(withId(R.id.reclamo_desc)).perform(setText(""));

        //SPINNER = SEMAFOROS
        onData(hasToString(startsWith("SEMAFOROS"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(not(isEnabled())));

        //SPINNER = ILUMINACION
        onData(hasToString(startsWith("ILUMINACION"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(not(isEnabled())));

        //SPINNER = RESIDUOS
        onData(hasToString(startsWith("RESIDUOS"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(not(isEnabled())));

        //SPINNER = RUIDOS_MOLESTOS
        onData(hasToString(startsWith("RUIDOS"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(not(isEnabled())));

        //SPINNER = OTROS
        onData(hasToString(startsWith("OTRO"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(not(isEnabled())));
    }

    @Test
    public void testDescMenorA8(){
        onView(withId(R.id.reclamo_coord)).perform(setText("1;1"));
        onView(withId(R.id.reclamo_mail)).perform(setText("dam@facu.com"));
        onView(withId(R.id.reclamo_desc)).perform(setText("<8"));

        //SPINNER = SEMAFOROS
        onData(hasToString(startsWith("SEMAFOROS"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(not(isEnabled())));

        //SPINNER = ILUMINACION
        onData(hasToString(startsWith("ILUMINACION"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(not(isEnabled())));

        //SPINNER = RESIDUOS
        onData(hasToString(startsWith("RESIDUOS"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(not(isEnabled())));

        //SPINNER = RUIDOS_MOLESTOS
        onData(hasToString(startsWith("RUIDOS"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(not(isEnabled())));

        //SPINNER = OTROS
        onData(hasToString(startsWith("OTRO"))).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(not(isEnabled())));
    }

    //typeText no anda, declaro mi propio ViewAction para setear un texto
    public static ViewAction setText(final String value){
        return new ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }
}
