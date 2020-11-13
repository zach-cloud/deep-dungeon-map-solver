package com.github.zachcloud;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class DeepDungeonMapTest extends TestCase {

    @Test
    public void testDeserializingMapIsNotNull() {
        String sampleMap = "*********************\n" +
                "*       **      U   *\n" +
                "**** ** ******** ** *\n" +
                "**** ** ******** ** *\n" +
                "*    **    **    ** *\n" +
                "* ******** ** ** ****\n" +
                "* ******** ** ** ****\n" +
                "*    **       ** ** *\n" +
                "* ** ** *********** *\n" +
                "* ** ** *********** *\n" +
                "* ** ** **          *\n" +
                "* ** ***** **********\n" +
                "* ** ***** **********\n" +
                "* ** **    **       *\n" +
                "**** ** *********** *\n" +
                "**** ** *********** *\n" +
                "*    **          ** *\n" +
                "* ************** ** *\n" +
                "* ************** ** *\n" +
                "*            D      *\n" +
                "*********************";

        DeepDungeonMap map = new DeepDungeonMap(sampleMap);
        Assert.assertNotEquals("Origin should not have been null, but it was.", null, map.getOrigin());
    }

    @Test
    public void testGeneratingSpeedwalk() {
        String sampleMap = "*********************\n" +
                "*       **      U   *\n" +
                "**** ** ******** ** *\n" +
                "**** ** ******** ** *\n" +
                "*    **    **    ** *\n" +
                "* ******** ** ** ****\n" +
                "* ******** ** ** ****\n" +
                "*    **       ** ** *\n" +
                "* ** ** *********** *\n" +
                "* ** ** *********** *\n" +
                "* ** ** **          *\n" +
                "* ** ***** **********\n" +
                "* ** ***** **********\n" +
                "* ** **    **       *\n" +
                "**** ** *********** *\n" +
                "**** ** *********** *\n" +
                "*    **          ** *\n" +
                "* ************** ** *\n" +
                "* ************** ** *\n" +
                "*            D      *\n" +
                "*********************";

        DeepDungeonMap map = new DeepDungeonMap(sampleMap);
        Speedwalk sw = map.solve();
        Assert.assertEquals("Speedwalk did not match", "s;w;s;w;n;w;n;w;s;w;s;e;s;s;s;w;s;e;e;e;e;d", sw.toString());
    }

}