package com.oneqst.quest.repository;

import com.oneqst.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByTitle(String title);

    boolean existsByTitle(String title);
}
