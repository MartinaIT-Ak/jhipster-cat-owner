package fr.it_akademy.jhipster.cat_owner.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.jhipster.cat_owner.domain.Cat} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CatDTO implements Serializable {

    private Long id;

    private String name;

    private String race;

    private Long age;

    private String healtStatus;

    private VeterinaryDTO veterinary;

    private OwnerDTO owner;

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

    public String getHealtStatus() {
        return healtStatus;
    }

    public void setHealtStatus(String healtStatus) {
        this.healtStatus = healtStatus;
    }

    public VeterinaryDTO getVeterinary() {
        return veterinary;
    }

    public void setVeterinary(VeterinaryDTO veterinary) {
        this.veterinary = veterinary;
    }

    public OwnerDTO getOwner() {
        return owner;
    }

    public void setOwner(OwnerDTO owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatDTO)) {
            return false;
        }

        CatDTO catDTO = (CatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, catDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", race='" + getRace() + "'" +
            ", age=" + getAge() +
            ", healtStatus='" + getHealtStatus() + "'" +
            ", veterinary=" + getVeterinary() +
            ", owner=" + getOwner() +
            "}";
    }
}
