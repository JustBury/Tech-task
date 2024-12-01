package com.ifortex.bookservice.service.impl;

import com.ifortex.bookservice.dao.MemberDAO;
import com.ifortex.bookservice.model.Member;
import com.ifortex.bookservice.service.MemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class MemberServiceImpl implements MemberService {

    MemberDAO memberDAO;

    @Autowired
    public MemberServiceImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    @Transactional
    public Member findMember() {
        return memberDAO.findMember();
    }

    @Override
    @Transactional
    public List<Member> findMembers() {
        return memberDAO.findMembers();
    }
}
