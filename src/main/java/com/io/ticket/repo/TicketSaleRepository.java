package com.io.ticket.repo;

import com.io.ticket.entity.TicketSale;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketSaleRepository extends JpaRepository<TicketSale, Long> {

    @Query("SELECT COUNT(t) FROM TicketSale t WHERE t.date = :date and t.requestUid is not null")
    long countByDate(@Param("date") String date);
    boolean existsByRequestUid(String requestUid);
    Optional<TicketSale> getTicketSaleByFactor(String Factor);
}
