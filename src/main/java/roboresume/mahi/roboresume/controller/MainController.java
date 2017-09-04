package roboresume.mahi.roboresume.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import roboresume.mahi.roboresume.models.*;
import roboresume.mahi.roboresume.repository.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    CourseRepository courseRepository;



    @GetMapping("/")
    public String Welcomepage(Model model)
    {
        String message="Welcome to Robo Resume";
        model.addAttribute("message", message);
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "welcome";
    }

    @GetMapping("/addpersonalinfo")

    public String PersonalInfo(Model model)
    {
        model.addAttribute("robopersonal", new RoboResume());
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return"addpersonalinfo";
    }

    @PostMapping("/addpersonalinfo")
    public String PostInfo(@Valid @ModelAttribute("robopersonal") RoboResume otherpersonal, BindingResult bindingResult,
                           Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "addpersonalinfo";
        }
        System.out.println("person id:"   +otherpersonal.getId());

        resumeRepository.save(otherpersonal);
//        model.addAttribute("numberOfPerson", resumeRepository.count());
//        model.addAttribute("numberOfEdu",educationRepository.count());
//        model.addAttribute("numberOfExpr",workRepository.count());
//        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "personalresult";
    }

    @GetMapping("/addeducation/{id}")
    public String EducationInfo(@PathVariable("id") long id,Model model)
    {
        Education othereducation= new Education();
        othereducation.setRoboResumeEdu(resumeRepository.findOne(id));
        model.addAttribute("roboeducation", othereducation);

        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());

        return"addeducation";
    }
    @PostMapping("/addeducation")
    public String PostEducation(@Valid @ModelAttribute("roboeducation") Education othereducation, BindingResult
            bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "addeducation";
        }
        educationRepository.save(othereducation);
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "educationresult";
    }

    @GetMapping("/addworkexperience/{id}")
    public String WorkInfo(@PathVariable("id") long id, Model model)
    {

        WorkExperience otherexperience=new WorkExperience();
        otherexperience.setRoboResumeExp(resumeRepository.findOne(id));
        model.addAttribute("robowork", otherexperience);
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
       return "addworkexperience";
    }
    @PostMapping("/addworkexperience")
    public String PostWork(@Valid @ModelAttribute("robowork") WorkExperience otherwork, BindingResult bindingResult,
                            Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "addworkexperience";
        }
        workRepository.save(otherwork);
        System.out.println(otherwork.getEnddate());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "workresult";
    }

    @GetMapping("/addskills/{id}")
    public String SkillInfo(@PathVariable("id") long id, Model model)
    {
        Skills otherskill= new Skills();
        otherskill.setRoboResumeSkill(resumeRepository.findOne(id));
        model.addAttribute("roboskills", otherskill);
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "addskills";
    }
    @PostMapping("/addskills")
    public String PostSkill(@Valid @ModelAttribute("roboskills") Skills otherskills, BindingResult bindingResult,
              Model model)
    {
        if (bindingResult.hasErrors()) {
            return "addskills";
        }
        skillsRepository.save(otherskills);
        model.addAttribute("numberOfSkill",skillsRepository.count());
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        return "skillsresult";
    }

    @GetMapping("addpeopletocourse/{id}")
    public String addPeopletoCourse(@PathVariable("id") long courseId, Model model)
    {
//        Courses courlist=new Courses();
//        courlist.setCourse("Java Boot Camp");
//        courseRepository.save(courlist);
//        courlist=new Courses();
//        courlist.setCourse("Cyber Advantage");
//        courseRepository.save(courlist);
//        courlist=new Courses();
//        courlist.setCourse("Phyton");
//        courseRepository.save(courlist);
        model.addAttribute("crs", courseRepository.findOne(new Long(courseId)));
        model.addAttribute("perlist",resumeRepository.findAll());
        return "courseaddpeople";
    }

    @PostMapping("addpeopletocourse/{crsid}")
    public String postPeopletoCourse(@PathVariable("crsid") long courseID,
                                     @RequestParam("people") String personId,
                                     @ModelAttribute("aPerson") RoboResume p,
                                     Model model)
    {
        Courses cr=courseRepository.findOne(new Long(courseID));
        cr.addRoboResume(resumeRepository.findOne(new Long(personId)));
        courseRepository.save(cr);
        model.addAttribute("personlist",resumeRepository.findAll());
        model.addAttribute("courselist",courseRepository.findAll());
        Iterable<RoboResume>people=resumeRepository.findAll();
        for(RoboResume item:people) {
            System.out.println(item.getFirstname());
        }
        return "redirect:/welcome";
    }


    @GetMapping("/viewresume/{id}")

    public String PostResume(@PathVariable("id") long id,Model model)
    {
        model.addAttribute("robo",resumeRepository.findRoboResumeById(id));
        return "viewresume";
    }
    @GetMapping("/editinfo/{id}")

    public String Editperson(@PathVariable("id") long id,Model model)
    {
        model.addAttribute("robo",resumeRepository.findRoboResumeById(id));
        return "editinfo";
    }

//    @GetMapping("/test")
//    public String showTable(Model model, RoboResume test)
//    {
////        model.addAttribute("robo",rbdata);
//        model.addAttribute("numberOfPerson", resumeRepository.count());
//        model.addAttribute("numberOfEdu",educationRepository.count());
//        model.addAttribute("numberOfExpr",workRepository.count());
//        model.addAttribute("numberOfSkill",skillsRepository.count());
//        return"resumetable";
//    }
    @GetMapping("/listperson")
    public String showTable( RoboResume persons, Model model) {

        model.addAttribute("robo",resumeRepository.findAll());
        return "listperson";
    }

    @GetMapping("/updateperson/{id}")
    public String editPerson(@PathVariable("id") long id, Model model){
        model.addAttribute("robopersonal", resumeRepository.findOne(id));

        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "addpersonalinfo";
    }
    @GetMapping("/updateeducation/{id}")
    public String updateEducation(@PathVariable("id") long id, Model model){
        model.addAttribute("roboeducation", educationRepository.findOne(id));
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "addeducation";
    }
    @GetMapping("/updateexperience/{id}")
    public String updateExperience(@PathVariable("id") long id, Model model){
        model.addAttribute("robowork", workRepository.findOne(id));
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "addworkexperience";
    }
    @GetMapping("/updateskill/{id}")
    public String updateSkill(@PathVariable("id") long id, Model model){
        model.addAttribute("roboskills", skillsRepository.findOne(id));
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "addskills";
    }

//    @GetMapping("/deleteeducation/{id}")
//    public String deleteEducation(@PathVariable("id") long id, Model model){
//        educationRepository.delete(id);
//        model.addAttribute("numberOfPerson", resumeRepository.count());
//        model.addAttribute("numberOfEdu",educationRepository.count());
//        model.addAttribute("numberOfExpr",workRepository.count());
//        model.addAttribute("numberOfSkill",skillsRepository.count());
//        return "redirect:/test";
//    }
//    @GetMapping("/deletework/{id}")
//    public String deleteWork(@PathVariable("id") long id, Model model){
//        workRepository.delete(id);
//        model.addAttribute("numberOfPerson", resumeRepository.count());
//        model.addAttribute("numberOfEdu",educationRepository.count());
//        model.addAttribute("numberOfExpr",workRepository.count());
//        model.addAttribute("numberOfSkill",skillsRepository.count());
//        return "redirect:/test";
//    }
//    @GetMapping("/deleteskill/{id}")
//    public String deleteSkill(@PathVariable("id") long id, Model model){
//        skillsRepository.delete(id);
//        model.addAttribute("numberOfPerson", resumeRepository.count());
//        model.addAttribute("numberOfEdu",educationRepository.count());
//        model.addAttribute("numberOfExpr",workRepository.count());
//        model.addAttribute("numberOfSkill",skillsRepository.count());
//        return "redirect:/test";
//    }

    @GetMapping("/login")
    public String logon(Model model) {
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());

        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
    @GetMapping("/welcome")
    public String WelcomeAfterLogin(Model model) {
        model.addAttribute("numberOfPerson", resumeRepository.count());
        model.addAttribute("numberOfEdu",educationRepository.count());
        model.addAttribute("numberOfExpr",workRepository.count());
        model.addAttribute("numberOfSkill",skillsRepository.count());
        return "welcome2";
    }
}
