package br.com.rezultz.rpp.enroll.message.content;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ParticipantCreateMessage (

    @NotBlank
    String name,

    @NotBlank
    String tradeName,

    @NotBlank
    @Size(max = 45)
    String document,

    @Size(max = 10)
    @NotBlank
    String documentType,

    String companyName

){}
