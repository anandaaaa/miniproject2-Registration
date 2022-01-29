package in.myproject.anand.repositires;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.myproject.anand.entities.CountryEntity;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Serializable>{

}
