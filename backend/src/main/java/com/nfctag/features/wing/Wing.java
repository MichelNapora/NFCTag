package com.nfctag.features.wing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfctag.features.building.Building;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UuidGenerator;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "building_id"}))
public class Wing {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    public Wing(String name, Building building){
        this.name=name;
        this.building=building;
    }

    public Wing(){}

    public UUID getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public Building getBuilding(){
        return this.building;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setBuilding(Building building){
        this.building=building;
    }

}
