package org.mat.samples.xslt;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.net.URL;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        // create the XML content input source:
        // can be a DOM node, SAX stream, or any
        // Java input stream/reader

        String xmlInputFile = App.class.getResource("source.xml").getFile();
        Source xmlSource = new StreamSource(new FileInputStream(xmlInputFile));

        // create the XSLT Stylesheet input source
        // can be a DOM node, SAX stream, or a
        // java input stream/reader
        String xsltInputFile = App.class.getResource("mytransfo.xsl").getFile();
        Source xsltSource = new StreamSource(new
                FileInputStream(xsltInputFile));

        // create the result target of the transformation
        // can be a DOM node, SAX stream, or a java out
        // stream/reader
        StringWriter sw = new StringWriter();
        Result transResult = new StreamResult(sw);

        // create the transformerfactory & transformer instance
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer(xsltSource);

        // execute transformation & fill result target object
        t.transform(xmlSource, transResult);
        System.out.println(sw.toString());
    }
}
