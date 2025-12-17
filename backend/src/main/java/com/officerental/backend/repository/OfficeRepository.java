package com.officerental.backend.repository;

import com.officerental.backend.model.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, Long> {

    @Query("""
            select o from Office o
            where (:city is null or lower(o.city) like lower(concat('%', :city, '%')))
              and (:minPrice is null or o.pricePerMonth >= :minPrice)
              and (:maxPrice is null or o.pricePerMonth <= :maxPrice)
              and (:minSize is null or o.sizeInSqm >= :minSize)
              and (:maxSize is null or o.sizeInSqm <= :maxSize)
            """)
    List<Office> search(
            @Param("city") String city,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("minSize") Integer minSize,
            @Param("maxSize") Integer maxSize);

    List<Office> findByCityContainingIgnoreCaseAndPricePerMonthBetweenAndSizeInSqmBetween(
            String city,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Integer minSize,
            Integer maxSize);

    long count();
}
