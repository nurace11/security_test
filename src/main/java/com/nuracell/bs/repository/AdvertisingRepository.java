package com.nuracell.bs.repository;

import com.nuracell.bs.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//@RepositoryRestResource(path = "ads") - optional annotation
@RepositoryRestResource(collectionResourceRel = "ads", path = "ads")
public interface AdvertisingRepository extends JpaRepository<Ad, Long> {
    List<Ad> findByTitle(@Param("title") String title);
}
