package com.userfront.controller;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.userfront.dao.RoleDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.TokenState;
import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;
import com.userfront.events.OnRegistrationCompleteEvent;
import com.userfront.service.AccountService;
import com.userfront.service.UserService;


@Controller
public class HomeController {
	
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private MessageSource messages;
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private AccountService accountService;
	
	 @Autowired
     private ApplicationEventPublisher eventPublisher;
	 
	@RequestMapping("/")
	public String home(){
		return "redirect:/index";
	}
	
	
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	
	@RequestMapping(value="/signup", method = RequestMethod.GET)
	public String signup(Model model){
	  	 User user = new User();
	     model.addAttribute("user", user);
	     return "signup";
	}
	
	@RequestMapping(value="/signup", method = RequestMethod.POST)
	public String signupPost(@ModelAttribute("user") User user, Model model,final HttpServletRequest request){
		if(userService.checkUserExists(user.getUsername(),user.getEmail())) {
			if(userService.checkEmailExists(user.getUsername())) {
				model.addAttribute("emailExists", true);
			}
			if(userService.checkUsernameExists(user.getEmail())) {
				model.addAttribute("usernameExists",true);
			}
			return "signup";
		} else {
			
			Set<UserRole> userRoles = new HashSet<>();
			userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));
			userService.createUser(user,userRoles);
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), getAppUrl(request)));
			return "redirect:/";
		}
	}
	
	 @RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
	    public String confirmRegistration(final Locale locale, final Model model, @RequestParam("token") final String token) throws UnsupportedEncodingException {
	        final TokenState result = userService.validateVerificationToken(token);
	        if (result == TokenState.TOKEN_VALID) {
	            final User user = userService.getUser(token);
	            LOG.info(user.toString());
	            
	            model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
	           // return "redirect:/login?lang=" + locale.getLanguage();
	            return "redirect:/index";
	        }

	        model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
	        model.addAttribute("expired", "expired".equals(result));
	        model.addAttribute("token", token);
	        return "redirect:/badUser.html?lang=" + locale.getLanguage();
	    }

		@RequestMapping("/userFront")
		public String userFront(Principal principal, Model model) {
			User user = userService.findByUsername(principal.getName());
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			SavingsAccount savingsAccount = user.getSavingsAccount();
			model.addAttribute("primaryAccount", primaryAccount);
			model.addAttribute("savingsAccount", savingsAccount);
			
			return "userFront";
			
		}
	

		private String getAppUrl(HttpServletRequest request) {
	        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	    }
}
