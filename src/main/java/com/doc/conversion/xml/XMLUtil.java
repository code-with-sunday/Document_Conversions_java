package com.doc.conversion.xml;

import org.apache.poi.poifs.filesystem.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class XMLUtil {

    public Mydata createntXmlObject() {

        final List<String> names = Arrays.asList("Sunday Peter",
                "Sam Peter", "Sim Peter");

        final List<String> time = Arrays.asList("Sunday Peter",
                "Sam Peter", "Sim Peter");

        final List<String> comments = Arrays.asList("When you spend so much you have to understand",
                "When you spend so much quick for person gulf", "When you spend so u say thank you");

        final Guestbook guestbook= new Guestbook();

        for (int i = 0; i < names.size(); i++){
            Entry entry = new Entry();
            entry.setName(names.get(i));
            entry.setTime(time.get(i));
            entry.setComment(comments.get(i));
            guestbook.getEntry().add(entry);
        }

        Mydata mydata = new Mydata();
        mydata.setGuestbook(guestbook);
        return mydata;
    }

    public InputStream getInputStream(Mydata mydata) {
        try{
            final String encoding = "UTF-8";
            final JAXBContext jaxbContext = JAXBContext.newInstance(Mydata.class);
            final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            final StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(mydata, sw);
            return new ByteArrayInputStream(sw.toString().getBytes(encoding));
        } catch (JAXBException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
