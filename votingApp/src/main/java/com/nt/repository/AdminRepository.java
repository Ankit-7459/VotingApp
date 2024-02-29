package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nt.model.Admin;



public interface AdminRepository extends JpaRepository<Admin, Integer>{

	@Query("select v from Admin v where v.name = :name")
	public Admin getAdminByName(@Param("name") String name);

}
