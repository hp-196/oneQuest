package com.oneQuset.oneQuset.repository.user;

import com.oneQuset.oneQuset.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class User_Repository {

    private final EntityManager em;

    /**
     * Create Date : [ 2022 - 05 - 18 ]
     * Last Update Date :
     * 기능 : 유저의 정보를 저장
     */
    public void save(User user) {
        em.persist(user);
    }

    /**
     * Create Date : [ 2022 - 05 - 18 ]
     * Last Update Date :
     * 기능 : 유저의 식별자를 가지고 유저를 찾음
     */
    public User findOne(User user) {
        return em.find(User.class, user);
    }

    /**
     * Create Date : [ 2022 - 05 - 18 ]
     * Last Update Date :
     * 기능 : 전체 유저의 정보를 리스트로 가져옴
     */
    public List<User> findAll() {
        return em.createQuery("select U from User as U ", User.class)
                .getResultList();
    }

    /**
     * Create Date : [ 2022 - 05 - 18 ]
     * Last Update Date :
     * 기능 : 유저의 이름을 가지고 유저의 정보를 가져옴
     */
    public List<User> findByName(String name) {
        return em.createQuery("select  U from  User  as U where U.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }
}
