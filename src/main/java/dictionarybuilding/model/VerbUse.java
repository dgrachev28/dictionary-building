package dictionarybuilding.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class VerbUse {

    @Id
    @GeneratedValue
    private Long id;

    private String verb;

    @ManyToOne
    private Sentence sentence;

    private Boolean used;

    public VerbUse() {}

    public VerbUse(String verb, Sentence sentence, Boolean used) {
        this.verb = verb;
        this.sentence = sentence;
        this.used = used;
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
}
