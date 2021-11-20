package turfinfos2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turfinfos2.model.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long>{

}
