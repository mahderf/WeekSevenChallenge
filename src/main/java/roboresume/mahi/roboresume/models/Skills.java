package roboresume.mahi.roboresume.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String skillname;
    @NotNull
    private String rating;

    @ManyToOne(fetch=FetchType.EAGER)
    private Person personskill;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkillname() {
        return skillname;
    }

    public void setSkillname(String skillname) {
        this.skillname = skillname;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Person getPersonskill() {
        return personskill;
    }

    public void setPersonskill(Person personskill) {
        this.personskill = personskill;
    }
}
