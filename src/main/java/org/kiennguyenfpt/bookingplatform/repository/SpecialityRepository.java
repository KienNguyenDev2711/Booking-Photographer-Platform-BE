package org.kiennguyenfpt.bookingplatform.repository;

import org.kiennguyenfpt.bookingplatform.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialityRepository extends JpaRepository<Speciality, String> {
    Optional<Speciality> findByCode(String code);
}
