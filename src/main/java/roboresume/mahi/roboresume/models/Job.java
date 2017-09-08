package roboresume.mahi.roboresume.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Table(name="JOB")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="jobtitle", nullable = false)
    private String title;

    @Column(name="employer", nullable = false)
    private String employer;
//needs to be changed to a range of salary
    @Column(name="salary", nullable = false)
    private String salary;

    @Column(name="description", nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name="job_id"),
    inverseJoinColumns = @JoinColumn(name="skill_id"))
    private Collection<Skills>jobskills;

    public Job(){
        this.jobskills=new HashSet<Skills>();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Skills> getJobskills() {
        return jobskills;
    }

    public void setJobskills(Collection<Skills> jobskills) {
        this.jobskills = jobskills;
    }

    public  void addSkill(Skills sk)
    {
        jobskills.add(sk);
    }
}
