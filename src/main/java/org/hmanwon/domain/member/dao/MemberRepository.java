package org.hmanwon.domain.member.dao;

import org.hmanwon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    Member findByEmail(String email);
}
