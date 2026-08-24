package br.com.rezultz.rpp.enroll.repository;

import br.com.rezultz.rpp.enroll.entity.Participant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long>{

    List<Participant> findAllByDeletedFalseOrderByNameAsc();
    boolean existsByDocument(String document);
    Optional<Participant> findByDocument(String document);

    @Modifying
    @Transactional
    @Query("UPDATE Participant p SET p.name = :name, p.updateDate = :updateDate WHERE p.document = :document")
    void updateNameByDocument(@Param("name") String name,
                             @Param("updateDate") LocalDateTime updateDate,
                             @Param("document") String document);

    @Modifying
    @Transactional
    @Query("UPDATE Participant p SET p.deleted = true, p.updateDate = :updateDate WHERE p.document = :document and p.deleted = false")
    void logicDelete(@Param("updateDate") LocalDateTime updateDate,
                    @Param("document") String document);

}

