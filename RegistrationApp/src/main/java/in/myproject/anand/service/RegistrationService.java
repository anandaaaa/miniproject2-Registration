package in.myproject.anand.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import in.myproject.anand.bindings.User;

@Service
public interface RegistrationService {
	
	public boolean uniqueEmail(String Email);
	public Map<Integer,String> getCountries();
	public Map<Integer,String> getStates(Integer countryID);
	public Map<Integer,String> getCites(Integer stateID);
	public boolean registerUser(User user);


	

}
