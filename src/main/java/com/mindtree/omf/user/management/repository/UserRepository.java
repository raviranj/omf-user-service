package com.mindtree.omf.user.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindtree.omf.user.management.model.OmfUser;

@Repository
public interface UserRepository extends JpaRepository<OmfUser, Long> {

	OmfUser findByName(String name);

}
