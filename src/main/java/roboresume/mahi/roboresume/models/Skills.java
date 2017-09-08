package roboresume.mahi.roboresume.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

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

    @ManyToMany(mappedBy="jobskills", fetch = FetchType.LAZY)
    private Collection<Job>jobs;

    public Skills(){
        this.jobs=new HashSet<Job>();
    }

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

    public Collection<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Collection<Job> jobs) {
        this.jobs = jobs;
    }

    public void addJob(Job jb)
    {
        jobs.add(jb);
    }
}
