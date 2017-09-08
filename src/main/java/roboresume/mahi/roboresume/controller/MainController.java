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
import roboresume.mahi.roboresume.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class MainController {
    @Autowired
   PersonRepository personRepository;
    @Autowired
    EducationRepository educationRepository;
    @Autowired
    WorkRepository workRepository;
    @Autowired
    SkillsRepository skillsRepository;
    @Autowired
    PersonService personService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JobRepository jobRepository;

    @GetMapping("/")
    public String Welcomepage(Model model)
    {
// checks if anyrole exists in the role repo and saves roles if there aren't any
        if(roleRepository.count()==0)
        {
//            this are the set of roles that exist in the database
            PersonRole rolelist=new PersonRole();
            rolelist.setRole("JOBSEEKER");
            roleRepository.save(rolelist);
            rolelist=new PersonRole();
            rolelist.setRole("RECRUITER");
            roleRepository.save(rolelist);
            rolelist=new PersonRole();
            rolelist.setRole("ADMIN");
            roleRepository.save(rolelist);
            return "welcome";
        }
else
        //to test the number of roles
//        System.out.println("Roles"+roleRepository.count());
        return "welcome";

    }
    @RequestMapping(value="/register",method= RequestMethod.GET)
    public String showRegistrationPage(Model model)
    {
        model.addAttribute("user",new Person());
        return "registration";
    }
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public  String processRegistrationPage(@Valid @ModelAttribute("user") Person person,
                                           BindingResult bindingResult, Model model) {
        model.addAttribute("user", person);


// checks if the username already exists
        Person checkusername=personRepository.findAllByUsername(person.getUsername());
        if (checkusername!=null) {
            String existingusername = "username '" + person.getUsername() + "' isn't available. Choose a different one";
            model.addAttribute("msg", existingusername);
            model.addAttribute("checkusername", checkusername);
            return "registration";
        }
// checks if an email already is registered
        Iterable<Person> checkemail = personRepository.findAllByEmail(person.getEmail());
        long emcount = checkemail.spliterator().getExactSizeIfKnown();
        System.out.println("++++++++++++++++++" + emcount + "++++++++++++++");
        if (emcount > 0) {
            String existingemail = "This email address '" + person.getEmail() + "' is already registered.";
            model.addAttribute("emmsg", existingemail);
            model.addAttribute("emcount", emcount);
            return "registration";
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        // depending on the role selection this assign's the user a specific role
        else if (person.getRoleselect().equalsIgnoreCase("jobseeker"))
        {
            personService.saveJobSeeker(person);
        }
        else if (person.getRoleselect().equalsIgnoreCase("recruiter"))
        {
            personService.saveRecruiter(person);
        }
        else if(person.getRoleselect().equalsIgnoreCase("admin"))
            {
            personService.saveAdmin(person);
            model.addAttribute("message", "User Account Successfully Created");
        }

        return "login";
    }



    @GetMapping("/addeducation")
    public String EducationInfo(Education othereducation,Principal principal,Model model)

    {
        Person person = personRepository.findAllByUsername(principal.getName());
        othereducation.setPersoneducation(person);
        model.addAttribute("neweducation", othereducation);


        return"addeducation";
    }
    @PostMapping("/addeducation")
    public String PostEducation(@Valid @ModelAttribute("neweducation") Education othereducation,
                                BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "addeducation";
        }
        educationRepository.save(othereducation);
        return "redirect:/welcome";
    }

    @GetMapping("/addworkexperience")
    public String WorkInfo( WorkExperience otherexperience,Principal principal,Model model)
    {

       Person person=personRepository.findAllByUsername(principal.getName());
       otherexperience.setPersonexperience(person);
       model.addAttribute("newwork", otherexperience);
       return "addworkexperience";
    }
    @PostMapping("/addworkexperience")
    public String PostWork(@Valid @ModelAttribute("newwork") WorkExperience otherexperience, BindingResult bindingResult,
                            Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "addworkexperience";
        }
        workRepository.save(otherexperience);
        //to see the end date
        System.out.println("End Date"+otherexperience.getEnddate());
        return "redirect:/welcome";
    }

    @GetMapping("/addskills")
    public String SkillInfo(Skills otherskill,Principal principal, Model model)
    {
        Person person=personRepository.findAllByUsername(principal.getName());
        otherskill.setPersonskill(person);

        model.addAttribute("newskill", otherskill);
        return "addskills";
    }
    @PostMapping("/addskills")
    public String PostSkill(@Valid @ModelAttribute("newskill") Skills otherskill, BindingResult bindingResult,
              Model model)
    {
        if (bindingResult.hasErrors()) {
            return "addskills";
        }
        skillsRepository.save(otherskill);

        return "redirect:/welcome";
    }
    @RequestMapping(value="/addjob",method= RequestMethod.GET)
    public String showJobForm(Model model)
    {
        // this is to check that principal is returning the loggedin user


        model.addAttribute("job",new Job());
        return "addjob";
    }
    @RequestMapping(value="/addjob", method = RequestMethod.POST)
    public  String processJob(@Valid @ModelAttribute("job") Job job,
                              BindingResult bindingResult, Model model) {
//        HttpServletRequest
// request.getParameter("");
//        String redirectoskill="addskills";
//        model.addAttribute("job", job);
//        job.setJobskills(job.getJobskills());
        jobRepository.save(job);

//       return"redirect:"+ redirectoskill;
        return "redirect:/addskilltojob/" + job.getId();
    }

    @GetMapping("/addskilltojob/{id}")
    public String addSkillToJob(@PathVariable("id") long jobID, Model model)
    {
        System.out.println("Found Job ID"+jobID);
        model.addAttribute("job", jobRepository.findOne(new Long(jobID)));
        model.addAttribute("skilllist",skillsRepository.findAll());

        return "jobaddskills";
    }
    @PostMapping("/addskilltojob/{jobid}")
    public String Skilltojob(@PathVariable ("jobid") long id,
                             @RequestParam("job") String jobID,
                             @ModelAttribute("aSkill")Person p,
                             Model model)
    {
        Job njob=jobRepository.findOne(new Long(id));
        njob.addSkill(skillsRepository.findOne(new Long(jobID)));
        jobRepository.save(njob);
//        Person person=personRepository.findAllByUsername(principal.getName());
//        otherskill.setPersonskill(person);

//        return "redirect:/addjob";
        return "redirect:/addskilltojob/" + id;
    }
    @GetMapping("/viewresume/{id}")

    public String PostResume(@PathVariable("id") long id,Model model)
    {
        model.addAttribute("robo",personRepository.findPersonById(id));
        return "viewresume";
    }
    @GetMapping("/editinfo/{id}")

    public String Editperson(@PathVariable("id") long id,Model model)
    {
        model.addAttribute("robo",personRepository.findPersonById(id));
        return "editinfo";
    }

    @GetMapping("/listperson")
    public String showTable( Person persons, Model model) {

        model.addAttribute("robo",personRepository.findAll());
        return "listperson";
    }

    @GetMapping("/updateperson/{id}")
    public String editPerson(@PathVariable("id") long id, Model model){
        model.addAttribute("robopersonal", personRepository.findOne(id));

        return "addpersonalinfo";
    }
    @GetMapping("/updateeducation/{id}")
    public String updateEducation(@PathVariable("id") long id, Model model){
        model.addAttribute("roboeducation", educationRepository.findOne(id));

        return "addeducation";
    }
    @GetMapping("/updateexperience/{id}")
    public String updateExperience(@PathVariable("id") long id, Model model){
        model.addAttribute("robowork", workRepository.findOne(id));

        return "addworkexperience";
    }
    @GetMapping("/updateskill/{id}")
    public String updateSkill(@PathVariable("id") long id, Model model){
        model.addAttribute("roboskills", skillsRepository.findOne(id));

        return "addskills";
    }

    @GetMapping("/login")
    public String logon(Model model) {

        return "login";
    }

    @GetMapping("/welcome")
    public String WelcomeAfterLogin(Model model) {

        return "welcome2";
    }
}
