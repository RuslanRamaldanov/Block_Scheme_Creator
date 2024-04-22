package tests;

import io.TxtIO;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TxtIOTest {

    @Test
    public void save_in_txt() {
    }

    @Test
    public void load_from_txt() {
        IOException expected = null;
        IOException actual = expected;
        File path = new File("save.txt");

        TxtIO txtIO = new TxtIO();
        try {
            txtIO.loadFromTxt(path);
        } catch(IOException ex) {
            actual = ex;
        }
        Assert.assertEquals(expected,actual);
    }
}