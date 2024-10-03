package com.yurasevv.post_office_api.repos;

import com.yurasevv.post_office_api.models.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostOfficeRepo extends JpaRepository<PostOffice, Integer> {
}
