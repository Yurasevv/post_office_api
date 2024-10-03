package com.yurasevv.post_office_api.repos;

import com.yurasevv.post_office_api.models.PostalMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostalMovementRepo extends JpaRepository<PostalMovement, Integer> {
}
