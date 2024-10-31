package com.doc.conversion.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mydata")
public class Mydata {
    private Guestbook guestbook;

    @XmlElement(name = "guestbook")
    public Guestbook getGuestbook() {
        return guestbook;
    }

    public void setGuestbook(Guestbook guestbook) {
        this.guestbook = guestbook;
    }
}
