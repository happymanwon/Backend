package org.hmanwon.domain.zzan.purchase.dao;

import java.util.List;
import java.util.Optional;
import org.hmanwon.domain.member.entity.Member;
import org.hmanwon.domain.zzan.purchase.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    @Query("select p from PurchaseHistory p left join fetch p.zzanItem where p.id = :id")
    Optional<PurchaseHistory> findById(@Param("id") Long id);

    @Query("select p from PurchaseHistory  p "
            + "left join fetch p.zzanItem "
            + "where p.member = :member")
    List<PurchaseHistory> findByMember(@Param("member") Member member);
}
