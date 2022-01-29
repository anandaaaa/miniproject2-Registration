package in.myproject.anand.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.myproject.anand.bindings.User;
import in.myproject.anand.constants.MyAppConstants;
import in.myproject.anand.service.RegistrationService;

@RestController
public class RegistrationController {

	@Autowired
	private RegistrationService service;
	
	
	@GetMapping("/emailcheck/{Email}")
	public String checkEmail(@PathVariable String Email)
	{
		
		boolean uniqueEmail = service.uniqueEmail(Email);
		if(uniqueEmail==true)
		{
			return MyAppConstants.UNIQUE;
		}else {
			return MyAppConstants.DUPLICATE;

		}
	}
	
	@GetMapping("/countries")
	public Map<Integer,String> getCountries()
	{
		
		return service.getCountries();
		
	}
	
	@GetMapping("/states/{countryId}")
	public Map<Integer,String> getStates(@PathVariable Integer countryId)
	{
		
		return service.getStates(countryId);
		
	}
	
	@GetMapping("/cites/{stateId}")
	public Map<Integer,String> getCites(@PathVariable Integer stateId)
	{
		
		return service.getCites(stateId);
	}
	
	@PostMapping("/saveuser")
	public String saveUser(@RequestBody User user)
	{
		boolean registerUser = service.registerUser(user);
		if(registerUser)
		{
			return MyAppConstants.SUCCESS;
		}else {
			return MyAppConstants.FAIL;
		}
	}
	
}
