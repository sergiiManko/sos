package pl.atins.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.atins.domain.City;
import pl.atins.repository.CityRepository;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public City findById(Long id) {
        return cityRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
