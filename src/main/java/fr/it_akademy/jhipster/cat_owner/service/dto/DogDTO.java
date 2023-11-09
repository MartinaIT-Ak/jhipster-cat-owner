package fr.it_akademy.jhipster.cat_owner.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.jhipster.cat_owner.domain.Dog} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DogDTO implements Serializable {

    private Long id;

    private String name;

    private String race;

    private Long age;

    private String healthStatus;

    private OwnerDTO owner;

    private VeterinaryDTO veterinary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public OwnerDTO getOwner() {
        return owner;
    }

    public void setOwner(OwnerDTO owner) {
        this.owner = owner;
    }

    public VeterinaryDTO getVeterinary() {
        return veterinary;
    }

    public void setVeterinary(VeterinaryDTO veterinary) {
        this.veterinary = veterinary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DogDTO)) {
            return false;
        }

        DogDTO dogDTO = (DogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DogDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", race='" + getRace() + "'" +
            ", age=" + getAge() +
            ", healthStatus='" + getHealthStatus() + "'" +
            ", owner=" + getOwner() +
            ", veterinary=" + getVeterinary() +
            "}";
    }
}
