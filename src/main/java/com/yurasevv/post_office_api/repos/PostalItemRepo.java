package com.yurasevv.post_office_api.repos;

import com.yurasevv.post_office_api.models.PostalItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostalItemRepo extends JpaRepository<PostalItem, Integer> {
}
