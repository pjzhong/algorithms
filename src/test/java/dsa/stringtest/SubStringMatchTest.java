package dsa.stringtest;

import dsa.string.SubStringMatchBruteForce;
import dsa.string.SubStringMatchKMP;
import dsa.string.SubStringMatchBMBC;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;

public class SubStringMatchTest {

    private static String pattern;
    private static String text;
    private static int javaStringResult;

    @BeforeClass
    public static void before() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader((Thread.currentThread().getContextClassLoader().getResourceAsStream("data.txt"))));
            StringBuilder builder = new StringBuilder();
            reader.lines().forEach(builder::append);

            text = builder.toString();
            pattern = "going";
            javaStringResult = text.indexOf(pattern);
        } finally {
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    @Test
    public void KMPTest() {
        SubStringMatchKMP match = new SubStringMatchKMP(pattern);
        Assert.assertEquals(javaStringResult, match.match(text));
    }

    @Test
    public void bruteForceTest() {
        SubStringMatchBruteForce match = new SubStringMatchBruteForce(pattern);
        Assert.assertEquals(javaStringResult, match.match(text));
    }

    @Test
    public void BMBCTest() {
        SubStringMatchBMBC match = new SubStringMatchBMBC(pattern);
        Assert.assertEquals(javaStringResult, match.match(text));
    }


}
