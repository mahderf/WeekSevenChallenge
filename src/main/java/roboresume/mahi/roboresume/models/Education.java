package roboresume.mahi.roboresume.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Education {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotEmpty
    @Size(max=60)
    private String fieldofstudy;
    @NotNull
    @DateTimeFormat(pattern ="yyyy")
    private Date year;
    @NotEmpty
    @Size(max=40)
    private String institute;

    @ManyToOne(fetch=FetchType.EAGER)
    private Person personeducation;

    public String getFieldofstudy() {
        return fieldofstudy;
    }

    public void setFieldofstudy(String fieldofstudy) {
        this.fieldofstudy = fieldofstudy;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getPersoneducation() {
        return personeducation;
    }

    public void setPersoneducation(Person personeducation) {
        this.personeducation = personeducation;
    }
}

