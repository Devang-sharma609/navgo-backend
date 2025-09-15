package com.navgo.repository;

import com.navgo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	AppUser findByEmail(String email);
	
	boolean existsByEmail(String email);

	/**
	 * @param googleId
	 * @return
	 */
	AppUser findByGoogleId(String googleId);
	
}
