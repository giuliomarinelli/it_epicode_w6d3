package it.epicode.w6d3.Models.dto;


import lombok.*;

import java.time.LocalDate;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoreDto {
    private String nome;
    private String cognome;
    private String email;
    private LocalDate dataDiNascita;
}
