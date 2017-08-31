package roboresume.mahi.roboresume.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String organization;
    private String position;
    @DateTimeFormat(pattern ="MMM,yyyy")
    private Date startdate;
    @DateTimeFormat(pattern ="MMM,yyyy")
    private Date enddate;
    private String duty;


    @ManyToOne(fetch=FetchType.EAGER)
    private RoboResume roboResumeExp;

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoboResume getRoboResumeExp() {
        return roboResumeExp;
    }

    public void setRoboResumeExp(RoboResume roboResumeExp) {
        this.roboResumeExp = roboResumeExp;
    }
}

