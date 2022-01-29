package in.myproject.anand.bindings;

import javax.persistence.Column;
import javax.persistence.Id;

import lombok.Data;

@Data
public class City {
	
	private Integer cityId;
	private String cityName;
	private Integer stateId;
}
