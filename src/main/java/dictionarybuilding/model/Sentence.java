package dictionarybuilding.model;

import javax.persistence.*;

@Entity
public class Sentence {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private String text;

    @Lob
    private String xml;

//    @ManyToOne
//    private Document document;

    public Sentence() {}

    public Sentence(String text, String xml) {
        this.text = text;
        this.xml = xml;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

//    public Document getDocument() {
//        return document;
//    }
//
//    public void setDocument(Document document) {
//        this.document = document;
//    }
}
