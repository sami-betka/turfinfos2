package turfinfos2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import turfinfos2.model.ArchiveInfo;

@Repository
public interface ArchiveInfoRepository extends JpaRepository<ArchiveInfo, Long>{

}
