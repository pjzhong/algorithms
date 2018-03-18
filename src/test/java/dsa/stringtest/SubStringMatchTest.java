package dsa.stringtest;

import dsa.string.SubStringMatchBruteForce;
import dsa.string.SubStringMatchKMP;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SubStringMatchTest {

    private String pattern;
    private String text;
    private int javaStringResult;

    @Before
    public void before() {
        this.pattern = "aaaaaaaaac";
        int size = 29999999;
        StringBuilder builder = new StringBuilder(size);
        for(int i = 0; i < size; i++) {
            builder.append('a');
        }
        builder.append('c');
        this.text = builder.toString();
        this.javaStringResult = text.indexOf(pattern);
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


}
