package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AmountOfGoods;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AmountOfGoods entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmountOfGoodsRepository extends JpaRepository<AmountOfGoods, Long> {
}
