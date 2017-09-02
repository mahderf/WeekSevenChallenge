package roboresume.mahi.roboresume.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String course;

    public Courses(){
        this.people=new HashSet<RoboResume>();
    }

    @ManyToMany
    private Set<RoboResume> people;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Set<RoboResume> getPeople() {
        return people;
    }

    public void setPeople(Set<RoboResume> people) {
        this.people = people;
    }

    public void addRoboResume(RoboResume pers)
    {
        people.add(pers);
    }
}
