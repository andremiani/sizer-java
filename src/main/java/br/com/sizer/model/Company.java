package br.com.sizer.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "company")
public class Company {
    @Schema(name = "Id Empresa")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(name = "Nome da Empresa")
    @Column(name = "name", length = 255)
    private String name;

    @Schema(name = "Máximo de Lojas Permitidas")
    @Column(name = "maxStores", length = 4)
    private int maxStores;

    @Schema(name = "Criado por")
    @Column(name = "createdBy")
    private String createdBy;

    @OneToMany(mappedBy = "company")
    private List<Store> store;

}
