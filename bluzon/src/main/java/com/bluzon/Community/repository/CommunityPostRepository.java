package com.bluzon.Community.repository;

import com.bluzon.Community.Entity.CommunityPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityPostRepository extends JpaRepository<CommunityPostEntity, Long> {

    List<CommunityPostEntity> findByTopicTagIgnoreCaseOrderByCreatedAtDesc(String topicTag);

    List<CommunityPostEntity> findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String title);
}
