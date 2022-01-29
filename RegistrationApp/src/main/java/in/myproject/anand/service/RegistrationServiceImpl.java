package in.myproject.anand.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.myproject.anand.bindings.User;
import in.myproject.anand.constants.MyAppConstants;
import in.myproject.anand.entities.CityEntity;
import in.myproject.anand.entities.CountryEntity;
import in.myproject.anand.entities.StateEntity;
import in.myproject.anand.entities.UserEntity;
import in.myproject.anand.prop.AppProperties;
import in.myproject.anand.repositires.CityRepostitory;
import in.myproject.anand.repositires.CountryRepository;
import in.myproject.anand.repositires.StateRepository;
import in.myproject.anand.repositires.UserRepository;
import in.myproject.anand.util.EmailUtil;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private UserRepository userepo;
	
	@Autowired
	private CountryRepository countryrepo;
	
	@Autowired
	private StateRepository staterepo;
	
	@Autowired
	private CityRepostitory cityrepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EmailUtil emailutil;

	@Autowired
	private AppProperties appProperties;
	
	@Override
	public boolean uniqueEmail(String Email) {
		UserEntity userEntity = userepo.findByUserEmail(Email);
		
		if(userEntity==null)
		{
			System.out.println("entered");
			return true;
		}

		return false;
	}

	@Override
	public Map<Integer, String> getCountries() {
	   List<CountryEntity> findAll = countryrepo.findAll();
	   
	   Map<Integer,String> countrydata=new HashMap<>();
	   
	   for(CountryEntity entity:findAll) {
		   countrydata.put(entity.getCountryId(), entity.getCountryName());
	   }
		return countrydata;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryID) {
		List<StateEntity> stateEntity = staterepo.findByCountryId(countryID);
		
		Map<Integer,String> stateData=new HashMap<>();
		
		
		for(StateEntity sentity:stateEntity)
		{
			stateData.put(sentity.getStateId(), sentity.getStateName());
		}
		
		return stateData;
	}

	@Override
	public Map<Integer, String> getCites(Integer stateID) {
		
		List<CityEntity> findByStateId = cityrepo.findByStateId(stateID);
		
		Map<Integer,String> cityData=new HashMap<>();
		
		for(CityEntity city:findByStateId)
		{
			cityData.put(city.getCityId(), city.getCityName());
		}
		return cityData;
	}

	@Override
	public boolean registerUser(User user) {
		// TODO Auto-generated method stub
		
		user.setUserPwd(generateTemppwd());
		user.setUserAccStatus("LOCKED");
		
		UserEntity entity= new UserEntity();
		BeanUtils.copyProperties(user, entity);
		
		UserEntity save = userRepo.save(entity);
		
		if(null!=save.getUserId())
		{
			return sendRegEmail(user);
		}
			
		return false;
	}

	private Boolean sendRegEmail(User user) {
		boolean sentEmail=true;
		
		//String subject="This is registration confirmation mail";
		
		Map<String, String> messages = appProperties.getMessages();
		String subject=	messages.get(MyAppConstants.MY_REG_EMAIL_SUBJECT);

		String emailBodyFileName=messages.get(MyAppConstants.MY_REG_EMAIL_BODY_FILE_NAME);
		String body=readMailBody(emailBodyFileName,user);
		
		 sentEmail = emailutil.sendEmail(subject, user.getUserEmail(), body);
		
		return sentEmail;
		
	}
	
	public String readMailBody(String Filename,User user)
	{
		String mailBody="";
		StringBuffer buffer =new StringBuffer();
		
		Path path =Paths.get(Filename);
		try {
			Stream<String> stream= Files.lines(path);
			stream.forEach(line->{
				buffer.append(line);
			});

			mailBody = buffer.toString();
			mailBody=mailBody.replace(MyAppConstants.FNAME, user.getUserFName());
			mailBody=mailBody.replace(MyAppConstants.EMAIL, user.getUserEmail());
			mailBody=mailBody.replace(MyAppConstants.TEMP_PWD, user.getUserPwd());

		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return mailBody;
	}

	private String generateTemppwd() {
				
		  int leftLimit = 48; // numeral '0'
		    int rightLimit = 122; // letter 'z'
		    int targetStringLength = 6;
		    Random random = new Random();

		    String tempPWD = random.ints(leftLimit, rightLimit + 1)
		      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		      .limit(targetStringLength)
		      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		      .toString();

		   // System.out.println(tempPWD);

		return tempPWD;
		
	}

}
