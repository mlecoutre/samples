package am.projects.webtransco.client;

import org.junit.Test;

import static org.junit.Assert.*;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;

/**
 * User: mlecoutre
 * Date: 30/08/12
 * Time: 16:28
 */
public class TranscoXSLExtTest {

    @Test
    public void testTranscoXSLExt() throws Exception {

        String xmlInputFile = TranscoXSLExtTest.class.getResource("../../../../source.xml").getFile();
        Source xmlSource = new StreamSource(new FileInputStream(xmlInputFile));
        String xsltInputFile = TranscoXSLExtTest.class.getResource("../../../../transfo.xsl").getFile();
        Source xsltSource = new StreamSource(new
                FileInputStream(xsltInputFile));


        StringWriter sw = new StringWriter();
        Result transResult = new StreamResult(sw);



        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer(xsltSource);


        t.transform(xmlSource, transResult);

        System.out.println(sw.toString());
        assertTrue("Output xml should contain sapl2p.pa.agn after the call to the transco.", sw.toString().contains("sapl2p.pa.agn"));
    }
}
