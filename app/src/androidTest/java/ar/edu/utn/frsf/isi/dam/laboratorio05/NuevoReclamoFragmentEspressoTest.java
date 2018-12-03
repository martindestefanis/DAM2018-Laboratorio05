package ar.edu.utn.frsf.isi.dam.laboratorio05;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NuevoReclamoFragmentEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
    public IntentsTestRule<MainActivity> intentsRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void setup() {
        NuevoReclamoFragment nuevoReclamoFragment = new NuevoReclamoFragment();
        mActivityRule.getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenido, nuevoReclamoFragment)
                .commit();
    }

    @Test
    public void testHayFoto(){
        onView(withId(R.id.reclamo_coord)).perform(typeText("1;1"));
        onView(withId(R.id.reclamo_mail)).perform(typeText("dam@facu.com"));
        onView(withId(R.id.reclamo_desc)).perform(typeText(""));
        onView(withId(R.id.reclamo_tipo)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(1).perform(click());

        // Create a bitmap we can use for our simulated camera image
        Bitmap icon = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.mipmap.ic_launcher);

        // Build a result to return from the Camera app
        Intent resultData = new Intent();
        resultData.putExtra("data", icon);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Stub out the Camera. When an intent is sent to the Camera, this tells Espresso to respond
        // with the ActivityResult we just created
        intending(toPackage("com.android.camera2")).respondWith(result);

        // Now that we have the stub in place, click on the button in our app that launches into the Camera
        onView(withId(R.id.btnFoto)).perform(click());

        // We can also validate that an intent resolving to the "camera" activity has been sent out by our app
        intended(toPackage("com.android.camera2"));

        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));
    }

    @Test
    public void testHayDesc(){
        onView(withId(R.id.reclamo_coord)).perform(typeText("1;1"));
        onView(withId(R.id.reclamo_mail)).perform(typeText("dam@facu.com"));
        onView(withId(R.id.reclamo_desc)).perform(typeText("Descripción Reclamo"));

        //SPINNER = SEMAFOROS
        onView(withId(R.id.reclamo_tipo)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(2).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));

        //SPINNER = ILUMINACION
        onView(withId(R.id.reclamo_tipo)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(3).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));

        //SPINNER = RESIDUOS
        onView(withId(R.id.reclamo_tipo)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(5).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));

        //SPINNER = RUIDOS_MOLESTOS
        onView(withId(R.id.reclamo_tipo)).perform(click());
        onData(allOf(is(instanceOf(String.class)))).atPosition(6).perform(click());
        onView(withId(R.id.btnGuardar)).check(matches(isEnabled()));
    }
}
