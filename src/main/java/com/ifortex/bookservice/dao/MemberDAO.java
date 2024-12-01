package com.ifortex.bookservice.dao;

import com.ifortex.bookservice.model.Member;

import java.util.List;

public interface MemberDAO {

    Member findMember();

    List<Member> findMembers();
}
