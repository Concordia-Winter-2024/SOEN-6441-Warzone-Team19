package com.warzone;

import com.warzone.controller.ControllerSuiteClass;
import com.warzone.elements.EntitiesSuiteClass;
import com.warzone.elements.map.MapsSuiteClass;
import com.warzone.elements.orders.AllTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite to run all the test classes in the project
 */
@RunWith(Suite.class)
@SuiteClasses({ ControllerSuiteClass.class, EntitiesSuiteClass.class, MapsSuiteClass.class, AllTests.class })
public class AllTestsSuiteClass {

}
