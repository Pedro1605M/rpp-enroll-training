package br.com.rezultz.rpp.enroll.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrl_participant")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "status_name", length = 50)
    private String statusName;

    @Column(name = "status_message")
    private String statusMessage;

    @Column(name = "cession_return")
    private Boolean cessionReturn;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "document", nullable = false, length = 45)
    private String document;

    @Column(name = "document_type", nullable = false, length = 10)
    private String documentType;

    @Column(name = "enroll_origin")
    private String enrollOrigin;

    @Column(name = "enroll_signature_url")
    private String enrollSignatureUrl;

    @Column(name = "external_crm_id")
    private String externalCrmId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "trade_name", nullable = false)
    private String tradeName;
}