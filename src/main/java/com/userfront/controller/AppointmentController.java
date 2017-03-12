package com.userfront.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.domain.Appointment;
import com.userfront.domain.User;
import com.userfront.service.AppointmentService;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/create",method = RequestMethod.GET)
	public String createAppointment(Model model) {
		Appointment appointment = new Appointment();
		model.addAttribute("appointment",appointment);
		model.addAttribute("dateString","");
		return "appointment";
	}
	
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String createAppointemntPost(@ModelAttribute("appointment") Appointment appointment,@ModelAttribute("dateString") String dateString,Model model) {
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date date;
		try {
			date = format1.parse(dateString);
			appointment.setDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		User user = userService.findByUsername(getPrincipal());
		appointment.setUser(user);
		
		appointmentService.createAppointment(appointment);
		
		return "redirect:/userFront";
		
	}
	
	
	private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
	
}
