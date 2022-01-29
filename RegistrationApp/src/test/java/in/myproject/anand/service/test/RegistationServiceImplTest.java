package in.myproject.anand.service.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import in.myproject.anand.entities.UserEntity;
import in.myproject.anand.prop.AppProperties;
import in.myproject.anand.repositires.CityRepostitory;
import in.myproject.anand.repositires.CountryRepository;
import in.myproject.anand.repositires.StateRepository;
import in.myproject.anand.repositires.UserRepository;
import in.myproject.anand.service.RegistrationServiceImpl;
import in.myproject.anand.util.EmailUtil;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RegistationServiceImplTest {

	@MockBean
	private UserRepository userRepo;

	@MockBean
	private CountryRepository countryRepo;

	@MockBean
	private StateRepository stateRepo;

	@MockBean
	private CityRepostitory cityRepo;

	@MockBean
	private EmailUtil emailUtils;

	@MockBean
	private AppProperties appProps;

	@InjectMocks
	private RegistrationServiceImpl service;

	@Test
	public void uniqueEmailTest() {
		when(userRepo.findByUserEmail("ashok@gmail.com")).thenReturn(new UserEntity());
		System.out.println("userService :: "+ service);
		boolean uniqueEmail = service.uniqueEmail("ashok@gmail.com");
		assertFalse(uniqueEmail);
	}

}