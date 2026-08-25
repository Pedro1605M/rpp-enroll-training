package br.com.rezultz.rpp.enroll.message.content;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ParticipantCreateMessage (

    @NotBlank(message = "O nome é obrigatório")
    String name,

    @NotBlank(message = "O trade name nome é obrigatório")
    String tradeName,

    @NotBlank(message = "O documento é obrigatório")
    @Size(max = 45,message = "Documento não aceita mais de 45 caracteres")
    String document,

    @Size(max = 10, message = "O tipo de ocumento não aceita mais de 10 caracteres")
    @NotBlank(message = "O tipo do documento é obrigatorio")
    String documentType,

    String companyName

){}
