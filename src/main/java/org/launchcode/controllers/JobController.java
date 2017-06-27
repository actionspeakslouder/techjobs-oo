package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model,@RequestParam int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job aJob = jobData.findById(id);
        model.addAttribute("job",aJob);

        return "job-detail";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String add(Model model ,@ModelAttribute @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (errors.hasErrors()){
            return "new-job";

        }
        int theEmployer = jobForm.getEmployerId();
        int theLocation = jobForm.getLocationId();
        int thePositionType = jobForm.getPositionTypeId();
        int theSkill = jobForm.getSkillId();

        Job newJob = new Job(jobForm.getName(), jobData.getEmployers().findById(theEmployer), jobData.getLocations().findById(theLocation), jobData.getPositionTypes().findById(thePositionType), jobData.getCoreCompetencies().findById(theSkill));
        jobData.add(newJob);

            return "redirect:.?id=" + newJob.getId();

        }



    }

