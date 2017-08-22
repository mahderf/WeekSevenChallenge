package roboresume.mahi.roboresume.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Entity
public class RoboResume {


    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotEmpty
    @Size(max=20)
    private String firstname;
    @NotEmpty
    @Size(max=20)
    private String lastname;

    @NotEmpty
    @Email
    private String email;

    private ArrayList<Education>educations=new ArrayList<Education>();
    private ArrayList<WorkExperience>experiences=new ArrayList<WorkExperience>();
    private ArrayList<Skills>newskills=new ArrayList<Skills>();


    public ArrayList<Education> getEducations() {
        return educations;
    }

    public void setEducations(ArrayList<Education> educations) {
        this.educations = educations;
    }

    public ArrayList<WorkExperience> getExperiences() {
        return experiences;
    }

    public void setExperiences(ArrayList<WorkExperience> experiences) {
        this.experiences = experiences;
    }

    public ArrayList<Skills> getNewskills() {
        return newskills;
    }

    public void setNewskills(ArrayList<Skills> newskills) {
        this.newskills = newskills;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
