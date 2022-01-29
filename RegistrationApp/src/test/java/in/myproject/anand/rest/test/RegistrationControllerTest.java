package in.myproject.anand.rest.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.myproject.anand.bindings.User;
import in.myproject.anand.constants.MyAppConstants;
import in.myproject.anand.rest.RegistrationController;
import in.myproject.anand.service.RegistrationService;

@WebMvcTest(value=RegistrationController.class)
public class RegistrationControllerTest {
	
	@MockBean
	private RegistrationService regservice;
	
	@Autowired
	private MockMvc mockmvc;
	
	
	
	@Test
	public void checkEmailTest() throws Exception
	{
		when(regservice.uniqueEmail("anand980980@gmail.com")).thenReturn(true);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/emailcheck/anand980980@gmail.com");
		MvcResult mvcResult = mockmvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		String status = mockHttpServletResponse.getContentAsString();
		assertEquals(status,MyAppConstants.UNIQUE);
		
	}
	
	@Test
	public void checkEmailTest2() throws Exception
	{
		when(regservice.uniqueEmail("anand980980@gmail.com")).thenReturn(false);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/emailcheck/anand980980@gmail.com");
		MvcResult mvcResult = mockmvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		String status = mockHttpServletResponse.getContentAsString();
		assertEquals(status,MyAppConstants.DUPLICATE);
		
	}
	
	
	@Test
	public void getCountriesTest() throws Exception
	{
		HashMap hashMap = new HashMap<Integer,String>();
		hashMap.put(1, "India");
		hashMap.put(2, "USA");


		when(regservice.getCountries()).thenReturn(hashMap);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/countries");
		MvcResult mvcResult = mockmvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		int status = mockHttpServletResponse.getStatus();
		assertEquals(200,status);
	}
	
	@Test
	public void getStatesTest() throws Exception
	{
		HashMap hashMap = new HashMap<Integer,String>();
		hashMap.put(1, "Andhrapradesh");
		hashMap.put(2, "NewJersy");


		when(regservice.getStates(1)).thenReturn(hashMap);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/states/1");
		MvcResult mvcResult = mockmvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		int status = mockHttpServletResponse.getStatus();
		assertEquals(200,status);
	}
	
	@Test
	public void getCitiesTest() throws Exception
	{
		HashMap hashMap = new HashMap<Integer,String>();
		hashMap.put(1, "Guntur");
		hashMap.put(2, "Ongole");


		when(regservice.getCites(1)).thenReturn(hashMap);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cites/1");
		MvcResult mvcResult = mockmvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse mockHttpServletResponse = mvcResult.getResponse();
		int status = mockHttpServletResponse.getStatus();
		assertEquals(200,status);
	}
	    @Test
		public void saveUserTest() throws Exception
		{
	    	User user = new User();
	    	user.setUserId(1);
	    	user.setUserFName("anand");
	    	user.setUserLName("kumar");
	    	user.setUserEmail("Anand@gmail.com");
	    	
	    	when(regservice.registerUser(user)).thenReturn(true);
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	String writeValueAsString = objectMapper.writeValueAsString(user);
	    	MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/saveuser").contentType("application/json").content(writeValueAsString);
			MvcResult mvcResult = mockmvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = mvcResult.getResponse();
			String contentAsString = response.getContentAsString();
			assertEquals(contentAsString,MyAppConstants.SUCCESS);
		}
	    
	    @Test
		public void saveUserTest1() throws Exception
		{
	    	User user = new User();
	    	user.setUserId(1);
	    	user.setUserFName("anand");
	    	user.setUserLName("kumar");
	    	user.setUserEmail("Anand@gmail.com");
	    	
	    	when(regservice.registerUser(user)).thenReturn(false);
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	String writeValueAsString = objectMapper.writeValueAsString(user);
	    	MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/saveuser").contentType("application/json").content(writeValueAsString);
			MvcResult mvcResult = mockmvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = mvcResult.getResponse();
			String contentAsString = response.getContentAsString();
			assertEquals(contentAsString,MyAppConstants.FAIL);
		}

}
