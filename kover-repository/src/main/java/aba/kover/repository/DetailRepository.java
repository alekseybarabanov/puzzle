package aba.kover.repository;

import aba.kover.persistence_vo.DetailVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailRepository extends JpaRepository<DetailVO, Integer> {
	
};
