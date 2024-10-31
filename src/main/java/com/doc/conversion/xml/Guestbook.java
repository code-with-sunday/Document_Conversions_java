package com.doc.conversion.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "guestbook")
public class Guestbook {
    private List<Entry> entries = new ArrayList<>();

    @XmlElement(name = "entry")
    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public void addEntry(Entry entry) {
        this.entries.add(entry);
    }
}
