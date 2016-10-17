package aba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aba.persistence_vo.DetailVO;

@Repository
public interface DetailRepository extends JpaRepository<DetailVO, Integer> {
	
};
