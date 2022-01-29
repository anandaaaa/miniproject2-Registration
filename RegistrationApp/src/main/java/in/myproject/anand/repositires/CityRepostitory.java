package in.myproject.anand.repositires;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.myproject.anand.entities.CityEntity;

@Repository
public interface CityRepostitory extends JpaRepository<CityEntity, Serializable>  {
public List<CityEntity> findByStateId(Integer StateId);
	
}
