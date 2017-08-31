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
    private RoboResume roboResumeSkill;

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

    public RoboResume getRoboResumeSkill() {
        return roboResumeSkill;
    }

    public void setRoboResumeSkill(RoboResume roboResumeSkill) {
        this.roboResumeSkill = roboResumeSkill;
    }
}
