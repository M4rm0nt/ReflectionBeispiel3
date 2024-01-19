package models;

import enums.Geschlecht;
import interfaces.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Mensch extends Lebewesen {
    @JsonProperty
    private int alter;
    @JsonProperty
    private String name;
    @JsonProperty
    private LocalDateTime geburtstag;
    @JsonProperty
    private LocalDateTime heuteTag;
    @JsonProperty
    private List<String> hobbies;
    @JsonProperty
    private Map<String, List<String>> haustiere;
    @JsonProperty
    private Geschlecht geschlecht;

    public Mensch(int alter, String name, LocalDateTime geburtstag, LocalDateTime heuteTag, List<String> hobbies, Map<String, List<String>> haustiere, Geschlecht geschlecht) {
        super("Homo sapiens");
        this.alter = alter;
        this.name = name;
        this.geburtstag = geburtstag;
        this.heuteTag = heuteTag;
        this.hobbies = hobbies;
        this.haustiere = haustiere;
        this.geschlecht = geschlecht;
    }

    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getGeburtstag() {
        return geburtstag;
    }

    public void setGeburtstag(LocalDateTime geburtstag) {
        this.geburtstag = geburtstag;
    }

    public LocalDateTime getHeuteTag() {
        return heuteTag;
    }

    public void setHeuteTag(LocalDateTime heuteTag) {
        this.heuteTag = heuteTag;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public Map<String, List<String>> getHaustiere() {
        return haustiere;
    }

    public void setHaustiere(Map<String, List<String>> haustiere) {
        this.haustiere = haustiere;
    }

    public Geschlecht getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(Geschlecht geschlecht) {
        this.geschlecht = geschlecht;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensch mensch = (Mensch) o;
        return alter == mensch.alter && Objects.equals(name, mensch.name) && Objects.equals(geburtstag, mensch.geburtstag) && Objects.equals(heuteTag, mensch.heuteTag) && Objects.equals(hobbies, mensch.hobbies) && Objects.equals(haustiere, mensch.haustiere) && geschlecht == mensch.geschlecht;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alter, name, geburtstag, heuteTag, hobbies, haustiere, geschlecht);
    }

    @Override
    public String toString() {
        return "Mensch{" +
                "alter=" + alter +
                ", name='" + name + '\'' +
                ", geburtstag=" + geburtstag +
                ", heuteTag=" + heuteTag +
                ", hobbies=" + hobbies +
                ", haustiere=" + haustiere +
                ", geschlecht=" + geschlecht +
                ", art='" + art + '\'' +
                '}';
    }
}