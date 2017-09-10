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
import java.util.*;

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

    // this method also checks the role repo and load roles in it if there aren't any
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

    Iterable<Person>checkid= personRepository.findAllById(person.getId());
    long numb=checkid.spliterator().getExactSizeIfKnown();
    if(numb==0) {
// checks if the username already exists
        Person checkusername = personRepository.findAllByUsername(person.getUsername());
        if (checkusername != null) {
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
            Principal principal,Model model)
    {
        Person person=personRepository.findAllByUsername(principal.getName());
        if (bindingResult.hasErrors()) {
            return "addskills";
        }
        skillsRepository.save(otherskill);
        //to redirect the recruiter to the assign skills after adding a new skill
//                if(person.getRoleselect().equalsIgnoreCase("RECRUITER")
//        {
//            return "redirect:"
//        }

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
        jobRepository.save(job);
        return "redirect:/addskilltojob/" + job.getId();
    }

    @GetMapping("/addskilltojob/{id}")
    public String addSkillToJob(@PathVariable("id") long jobID, Model model)
    {
        System.out.println("Found Job ID"+jobID);
        model.addAttribute("job", jobRepository.findOne(new Long(jobID)));
        model.addAttribute("skilllist",skillsRepository.findAll());

        System.out.println("Skill has just been added");
        return "jobaddskills";
    }

    @PostMapping("/addskilltojob/{jobid}")
    public String Skilltojob(@PathVariable ("jobid") long id,
                             @RequestParam("job") String jobID,
                             @ModelAttribute("aSkill")Person p,
                             Model model)
    {
        System.out.println("Before creating new SKILLS");
        Skills nskill=new Skills();
        System.out.println("Before finding JOB");
        Job njob=jobRepository.findOne(new Long(id));
        njob.addSkill(skillsRepository.findOne(new Long(jobID)));
        System.out.println("Before SAVING");
        jobRepository.save(njob);

//        System.out.println(nskill.getId());
//        for(Skills t:njob.getJobskills())
//        {
//            System.out.println(t.getSkillname());
//            System.out.println(t.getId());
//            System.out.println(personRepository.findOne(t.getPersonskill().getId()).getRoleselect()+"with ID"+t.getPersonskill().getId());
//        }
        System.out.println(njob.getJobskills());
        // compare the skillID in job_jobskill with skills table
/*
        if(nskill.getSkillname().equals(skillsRepository.findAllByPersonskill(nskill.getSkillname()))) {
            System.out.println(personRepository.findOne(nskill.getPersonskill().getId()));

        }*/
            // if there is a match
            // extract the userID in the skills table
            //nskill.getPersonskill().getId();
            // output a notification

        /*for (Skills personSkill : personRepository.findAllBySkills(nskill.getSkillname())) {
            System.out.println("Entering the for each loop....");
            if (njob.getJobskills().equals(personSkill)) {
                model.addAttribute("matchMessages", "Match has been found for job: " + njob.getTitle());
                System.out.println("Skill has been found to job: " + p.getFirstName() + "and with job: " + njob.getTitle());
            }
            else {
                System.out.println("No match was found with skill: " + nskill.getSkillname());
            }
        }*/

        return "redirect:/addskilltojob/" + id;
    }
    @GetMapping("/viewresume")
    public String PostResume( Principal principal,Model model)
    {
        model.addAttribute("person",personRepository.findAllByUsername(principal.getName()));
        return "viewresume";
    }
    @GetMapping("/editinfo")

    public String Editperson(Principal principal,Model model)
    {
        model.addAttribute("person",personRepository.findAllByUsername(principal.getName()));
        return "editinfo";
    }

    @GetMapping("/updateperson/{id}")
    public String editPerson(@PathVariable("id") long id, Model model){
        model.addAttribute("user", personRepository.findOne(id));

        return "registration";
    }
    @GetMapping("/updateeducation/{id}")
    public String updateEducation(@PathVariable("id") long id, Model model){
        model.addAttribute("neweducation", educationRepository.findOne(id));

        return "addeducation";
    }
    @GetMapping("/updateexperience/{id}")
    public String updateExperience(@PathVariable("id") long id, Model model){
        model.addAttribute("newwork", workRepository.findOne(id));

        return "addworkexperience";
    }
    @GetMapping("/updateskill/{id}")
    public String updateSkill(@PathVariable("id") long id, Model model){
        model.addAttribute("newskill", skillsRepository.findOne(id));

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

    @GetMapping("/listjobs")
    public String jobsListed(Model model)
    {
        model.addAttribute("joblist",jobRepository.findAll());

        return"joblist";
    }
    @GetMapping("/jobdetail/{id}")
    public String jobdetail(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("joblist",jobRepository.findOne(id));

        return"jobdetail";
    }
//search for people and their resume using a person's name
    @GetMapping("/searchpeople")
    public String searchPeople(Model model) {

        model.addAttribute("user",new Person());

        return "searchpeople";
    }
    @PostMapping("/searchpeople")
    public String showPeople(@ModelAttribute("user") Person otherperson,
                             Model model) {

        Iterable<Person>listpeople=personRepository.findAllByFirstName(otherperson.getFirstName());
        model.addAttribute("person",listpeople);

        return "peopleresult";
    }

    // search for person using the school they attended
//    @GetMapping("/searchschool")
//    public String searchSchool(Model model) {
//
//        model.addAttribute("neweducation",new Education());
//
//        return "searchschool";
//    }
//
//    @PostMapping("/searchschool")
//    public String searchSchool(@ModelAttribute("neweducation") Education othereducation,
//                             Model model) {
//        Iterable<Education>listedu=educationRepository.findAllByInstitute(othereducation.getInstitute());
//        model.addAttribute("educ",listedu);
//
//        return "schoolresult";
//    }


    // searching for a job using job title
    @GetMapping("/searchjobs")
    public String searchJobs(Model model) {

        model.addAttribute("joblist",new Job());

        return "searchjobs";
    }
    @PostMapping("/searchjobs")
    public String searchJobs(@ModelAttribute("joblist") Job otherjob,
                             Model model) {

        Iterable<Job>listjobs=jobRepository.findAllByTitle(otherjob.getTitle());
        model.addAttribute("joblist",listjobs);

        return "joblist";
    }

    //search for jobs using the name of the employer
    @GetMapping("/searchemployer")
    public String searchEmployer(Model model) {

        model.addAttribute("joblist",new Job());

        return "searchemployer";
    }

    @PostMapping("/searchemployer")
    public String emploerResult(@ModelAttribute("joblist") Job otherjob,
                             Model model){

        Iterable<Job>listjobs=jobRepository.findAllByEmployer(otherjob.getEmployer());
        model.addAttribute("joblist",listjobs);

        return "joblist";
    }

    // matching the skills of a job post with a person's skill set for notification
    @GetMapping("/jobsmatchingskill")
    public String jobMatchs(Principal principal,Skills nskill, Model model) {
        //get all the skills in the jobSKills repository - place it in a local variable
        Iterable<Job> jobList = jobRepository.findAll();
        // the booleans are to stop the loop if it match's a person skill to a job's skill
//        boolean stopper = false;
//        boolean stopper2=false;
        Set<Job> test=new HashSet<>();
        for (Job jb : jobList) {
            //loops through skills in jobs
            for (Skills nsk : jb.getJobskills()) {
//                while(stopper) {
                //finds this person and loops through the skills of that person
                    Person p = personRepository.findAllByUsername(principal.getName());
                    for (Skills sk : p.getSkills()) {
                        // checks if a job skill matches a person's skill
                        if (nsk.getSkillname().equals(sk.getSkillname())) {
                            System.out.println("Job matching your skills found");
                            System.out.println("Job found is: " + jb.getTitle());
                            test.add(jb);
                            model.addAttribute("jobfound", test);
//                            stopper = true;
                            return "jobmessage";
                        }
                        else {
                            System.out.println("no Job found");
                        }

                    }
//                }
            }
        }
            return "jobmessage";
    }
}
