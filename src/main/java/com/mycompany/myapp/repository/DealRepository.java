package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Deal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Deal entity.
 */
@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

    @Query(value = "select distinct deal from Deal deal left join fetch deal.amountOfGoods",
        countQuery = "select count(distinct deal) from Deal deal")
    Page<Deal> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct deal from Deal deal left join fetch deal.amountOfGoods")
    List<Deal> findAllWithEagerRelationships();

    @Query("select deal from Deal deal left join fetch deal.amountOfGoods where deal.id =:id")
    Optional<Deal> findOneWithEagerRelationships(@Param("id") Long id);
}
