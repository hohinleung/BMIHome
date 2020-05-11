package com.example.bmihome;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.bmihome", appContext.getPackageName());


    }
    @Test
    public void CheckDate(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(currentTime);

        assertEquals(String.valueOf(formattedDate),"11-May-2020");
    }

    @Test
    public void CheckBMI(){
        Double height,weight;
        Double bmi;

        height=170.0;
        weight=50.0;

        bmi = weight / ((height/100)*(height/100));

        assertEquals(String.format("%.2f", bmi),"17.30");

    }

}
