package com.oneqst;

import com.oneqst.Member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

// 보류
@Component
@RequiredArgsConstructor
public class initDB {
    private final InitService initService;

    @PostConstruct
    public void init() {
//        initService.userInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void userInit() {
            System.out.println("userInit" + this.getClass());
            Member member = Member.builder()
                    .address("안성시")
                    .email("admin@gmail.com")
                    .nickname("admin")
                    .password("1234")
                    .build();

            em.persist(member);
        }
    }
}
