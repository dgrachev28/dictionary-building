package dictionarybuilding.model;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "verbuse")
@Table(appliesTo = "verbuse", indexes = @Index(name = "verbIndex", columnNames = "verb"))
public class VerbUse {

    @Id
    @GeneratedValue
    private Long id;

    private String verb;

    @ManyToOne
    private Sentence sentence;

    private Boolean used;

    private Long position;

    public VerbUse() {}

    public VerbUse(String verb, Sentence sentence, Boolean used, Long position) {
        this.verb = verb;
        this.sentence = sentence;
        this.used = used;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public Sentence getSentence() {
        return sentence;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }
}
