package com.oneqst.quest.repository;

import com.oneqst.quest.domain.Quest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional(readOnly = true)
class QuestRepositoryTest {

    @Autowired
    private QuestRepository questRepository;

    @Test
    public void total_search() {
        // given

        // when
        List<Quest> quests = questRepository.my_quests(1L);

        // then
        Assertions.assertEquals(quests.size(),2,"동일해야합니다.");
    }
}