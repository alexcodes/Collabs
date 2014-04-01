package test;

import collabs.model.data.ServerDocument;
import collabs.model.data.SimpleServerDocument;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Author: Aleksey A.
 * Date: 01.04.14
 * Time: 20:16
 */
public class SimpleServerDocumentTest extends TestCase {
    public void testInsert_begin_good() throws Exception {
        String expected = "tabcde";
        ServerDocument document = new SimpleServerDocument("abcde");
        document.insert("t", 0);
        Assert.assertEquals(true, expected.equals(document.getText()));
    }

    public void testInsert_middle_good() throws Exception {
        String expected = "abtcde";
        ServerDocument document = new SimpleServerDocument("abcde");
        document.insert("t", 2);
        Assert.assertEquals(true, expected.equals(document.getText()));
    }

    public void testInsert_end_good() throws Exception {
        String expected = "abcdet";
        ServerDocument document = new SimpleServerDocument("abcde");
        document.insert("t", 5);
        Assert.assertEquals(true, expected.equals(document.getText()));
    }

    public void testBackspace_begin_good() throws Exception {
        String expected = "bcde";
        ServerDocument document = new SimpleServerDocument("abcde");
        document.backspace(0, 1);
        Assert.assertEquals(true, expected.equals(document.getText()));
    }

    public void testBackspace_middle_good() throws Exception {
        String expected = "abde";
        ServerDocument document = new SimpleServerDocument("abcde");
        document.backspace(2, 1);
        Assert.assertEquals(true, expected.equals(document.getText()));
    }

    public void testBackspace_end_good() throws Exception {
        String expected = "abcd";
        ServerDocument document = new SimpleServerDocument("abcde");
        document.backspace(4, 1);
        Assert.assertEquals(true, expected.equals(document.getText()));
    }
}
