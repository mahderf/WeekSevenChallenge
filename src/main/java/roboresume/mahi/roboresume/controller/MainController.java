package roboresume.mahi.roboresume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import roboresume.mahi.roboresume.models.Education;
import roboresume.mahi.roboresume.models.RoboResume;
import roboresume.mahi.roboresume.models.Skills;
import roboresume.mahi.roboresume.models.WorkExperience;
import roboresume.mahi.roboresume.repository.EducationRepository;
import roboresume.mahi.roboresume.repository.ResumeRepository;
import roboresume.mahi.roboresume.repository.SkillsRepository;
import roboresume.mahi.roboresume.repository.WorkRepository;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class MainController {
    @Autowired
    ResumeRepository resumeRepository;
    @Autowired
    EducationRepository educationRepository;
    @Autowired
    WorkRepository workRepository;
    @Autowired
    SkillsRepository skillsRepository;



    @GetMapping("/")
    public String Welcomepage(Model model)
    {
        String message="Welcome to Robo Resume";
        model.addAttribute("message", message);
        return "welcome";
    }

    @GetMapping("/addpersonalinfo")

    public String PersonalInfo(Model model)
    {
        model.addAttribute("robopersonal", new RoboResume());
        return"addpersonalinfo";
    }

    @PostMapping("/addpersonalinfo")
    public String PostInfo(@Valid @ModelAttribute("robopersonal") RoboResume otherpersonal, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "addpersonalinfo";
        }
        resumeRepository.save(otherpersonal);
        return "personalresult";
    }

    @GetMapping("/addeducation")

    public String EducationInfo(Model model)
    {
        model.addAttribute("roboeducation", new Education());
        return"addeducation";
    }

    @PostMapping("/addeducation")
    public String PostEducation(@Valid @ModelAttribute("roboeducation") Education othereducation, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "addeducation";
        }
        educationRepository.save(othereducation);
        return "educationresult";
    }
    @GetMapping("/addworkexperience")

    public String WorkInfo(Model model)
    {
       model.addAttribute("robowork", new WorkExperience());
       return "addworkexperience";
    }
    @PostMapping("/addworkexperience")
    public String PostWork(@Valid @ModelAttribute("robowork") WorkExperience otherwork, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "addworkexperience";
        }
        workRepository.save(otherwork);
        return "workresult";
    }

    @GetMapping("/addskills")

    public String SkillInfo(Model model)
    {
        model.addAttribute("roboskills", new Skills());
        return "addskills";
    }
    @PostMapping("/addskills")
    public String PostSkill(@Valid @ModelAttribute("roboskills") Skills otherskills, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addskills";
        }
        skillsRepository.save(otherskills);
        return "skillsresult";
    }

    @GetMapping("/viewresume")

    public String PostResume(Model model)
    {
        RoboResume rbdata=resumeRepository.findOne(new Long(1));

        ArrayList<Education>educationlist=((ArrayList<Education>) educationRepository.findAll());
         rbdata.setEducations(educationlist);
       model.addAttribute("test", educationlist);

        ArrayList<WorkExperience>worklist=((ArrayList<WorkExperience>)workRepository.findAll());
        rbdata.setExperiences(worklist);

        ArrayList<Skills>skilllist=((ArrayList<Skills>)skillsRepository.findAll());
        rbdata.setNewskills(skilllist);



    for(WorkExperience item: rbdata.getExperiences()) {
        System.out.println(item.getOrganization()+item.getPosition()+item.getDuty()+item.getStartdate()+item.getStartdate());

    }
    for(Education item: rbdata.getEducations()) {
        System.out.println(item.getFieldofstudy()+item.getInstitute()+item.getYear());

    }
    for(Skills item: skilllist) {
        System.out.println(item.getSkillname()+item.getRating());

    }
        System.out.println(rbdata.getFirstname());
        System.out.println(rbdata.getLastname());

        model.addAttribute("robo", rbdata );
                return "viewresume";
    }

}
