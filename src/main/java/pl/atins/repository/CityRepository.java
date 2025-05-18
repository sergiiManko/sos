package pl.atins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.atins.domain.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}
