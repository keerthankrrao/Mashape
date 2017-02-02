package com.keerthan.mashape;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by LENOVO on 01-02-2017.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest{
    private DBHandler dbHandler;

    @Before
    public void  setup() throws Exception{
        getTargetContext().deleteDatabase(DBHandler.DBNAME);
        dbHandler = new DBHandler(getTargetContext());
    }

    @After
    public void tearDown() throws Exception{
        dbHandler.close();
    }

    @Test
    public void addTest()throws Exception
    {
        Country country = new Country();
        country.setCountryName("Russia");
        dbHandler.addCountry(country);

        List<Country> countryTypes = dbHandler.getAllCountries();
        assertThat(countryTypes.size(),is(1));
        assertTrue(countryTypes.get(0).getCountryName().equals("Russia"));
    }
}
