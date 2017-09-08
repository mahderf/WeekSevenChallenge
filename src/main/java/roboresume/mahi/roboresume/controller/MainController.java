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
            //to test the number of roles
            System.out.println("Roles"+roleRepository.count());
        {
            //this are the set of roles that exist in the database
            PersonRole rolelist=new PersonRole();
            rolelist.setRole("JOBSEEKER");
            roleRepository.save(rolelist);
            rolelist=new PersonRole();
            rolelist.setRole("RECRUITER");
            roleRepository.save(rolelist);
            rolelist=new PersonRole();
            rolelist.setRole("ADMIN");
            roleRepository.save(rolelist);
        }

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
        Iterable<Person> checkusername = personRepository.findAllByUsername(person.getUsername());
        long count = checkusername.spliterator().getExactSizeIfKnown();
        System.out.println("++++++++++++++++++" + count + "++++++++++++++");
        if (count > 0) {
            String existingusername = "username '" + person.getUsername() + "' isn't available. Choose a different one";
            model.addAttribute("msg", existingusername);
            model.addAttribute("count", count);
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

    @RequestMapping(value="/addjob",method= RequestMethod.GET)
    public String showJobForm(Model model)
    {
        model.addAttribute("job",new Job());
        return "addjob";
    }
    @RequestMapping(value="/addjob", method = RequestMethod.POST)
    public  String processJob(@Valid @ModelAttribute("job") Job job,
                                           BindingResult bindingResult, Model model) {
        model.addAttribute("job", job);

        job.setJobskills(job.getJobskills());
        jobRepository.save(job);

       return"redirect:/addskills";
    }

    @GetMapping("/addeducation")
    public String EducationInfo(Model model)
    {

        model.addAttribute("neweducation", new Education());

        return"addeducation";
    }
    @PostMapping("/addeducation")
    public String PostEducation(@Valid @ModelAttribute("roboeducation") Education othereducation,
                                BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "addeducation";
        }
        Person nper= new Person();

//        nper.setEducations();

        educationRepository.save(othereducation);
        model.addAttribute("numberOfEdu",educationRepository);

        return "educationresult";
    }

    @GetMapping("/addworkexperience/{id}")
    public String WorkInfo(@PathVariable("id") long id, Model model)
    {

        WorkExperience otherexperience=new WorkExperience();
        otherexperience.setPersonexperience(personRepository.findOne(id));

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

        return "workresult";
    }

    @GetMapping("/addskills/{id}")
    public String SkillInfo(@PathVariable("id") long id, Model model)
    {
        Skills otherskill= new Skills();
        otherskill.setPersonskill(personRepository.findOne(id));

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

        return "skillsresult";
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
