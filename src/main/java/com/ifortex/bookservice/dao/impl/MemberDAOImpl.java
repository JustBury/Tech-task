package com.ifortex.bookservice.dao.impl;

import com.ifortex.bookservice.dao.MemberDAO;
import com.ifortex.bookservice.model.Book;
import com.ifortex.bookservice.model.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberDAOImpl implements MemberDAO {

    private final String SELECT_LAST_REGISTERED_MEMBER_READ_ROMANCE =
            "SELECT m FROM Member m JOIN m.borrowedBooks b ORDER BY m.membershipDate DESC";
    private final String SELECT_REGISTERED_MEMBER_IN_2023_DONT_READ_BOOKS =
            "SELECT m FROM Member m LEFT JOIN m.borrowedBooks b WHERE YEAR(m.membershipDate) = 2023 AND SIZE(m.borrowedBooks) = 0";
    private final String ROMANCE = "Romance";

    EntityManager entityManager;

    @Autowired
    public MemberDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Member findMember() {
        TypedQuery<Member> query = entityManager.createQuery(SELECT_LAST_REGISTERED_MEMBER_READ_ROMANCE, Member.class);

        return query.getResultList().stream().filter(member -> {
            for(Book book : member.getBorrowedBooks()){
                if(book.getGenres().contains(ROMANCE)){
                    return true;
                }
            }
            return false;
        }).findFirst().orElse(null);
    }

    @Override
    public List<Member> findMembers() {
        Query query = entityManager.createQuery(SELECT_REGISTERED_MEMBER_IN_2023_DONT_READ_BOOKS);
        List<Member> list = query.getResultList();
        System.out.print(list);
        return list;
    }
}
