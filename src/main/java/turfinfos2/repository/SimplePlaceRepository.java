package turfinfos2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import turfinfos2.model.SimplePlace;

public interface SimplePlaceRepository extends JpaRepository<SimplePlace, Long> {

}
