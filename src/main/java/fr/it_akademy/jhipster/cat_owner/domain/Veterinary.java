package fr.it_akademy.jhipster.cat_owner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Veterinary.
 */
@Entity
@Table(name = "veterinary")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Veterinary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "veterinary")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "veterinary", "owner" }, allowSetters = true)
    private Set<Cat> cats = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "veterinary")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "owner", "veterinary" }, allowSetters = true)
    private Set<Dog> dogs = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "veterinaries")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cats", "dogs", "veterinaries" }, allowSetters = true)
    private Set<Owner> owners = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Veterinary id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Veterinary title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Veterinary firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Veterinary lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public Veterinary address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPhoneNumber() {
        return this.phoneNumber;
    }

    public Veterinary phoneNumber(Long phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Cat> getCats() {
        return this.cats;
    }

    public void setCats(Set<Cat> cats) {
        if (this.cats != null) {
            this.cats.forEach(i -> i.setVeterinary(null));
        }
        if (cats != null) {
            cats.forEach(i -> i.setVeterinary(this));
        }
        this.cats = cats;
    }

    public Veterinary cats(Set<Cat> cats) {
        this.setCats(cats);
        return this;
    }

    public Veterinary addCat(Cat cat) {
        this.cats.add(cat);
        cat.setVeterinary(this);
        return this;
    }

    public Veterinary removeCat(Cat cat) {
        this.cats.remove(cat);
        cat.setVeterinary(null);
        return this;
    }

    public Set<Dog> getDogs() {
        return this.dogs;
    }

    public void setDogs(Set<Dog> dogs) {
        if (this.dogs != null) {
            this.dogs.forEach(i -> i.setVeterinary(null));
        }
        if (dogs != null) {
            dogs.forEach(i -> i.setVeterinary(this));
        }
        this.dogs = dogs;
    }

    public Veterinary dogs(Set<Dog> dogs) {
        this.setDogs(dogs);
        return this;
    }

    public Veterinary addDog(Dog dog) {
        this.dogs.add(dog);
        dog.setVeterinary(this);
        return this;
    }

    public Veterinary removeDog(Dog dog) {
        this.dogs.remove(dog);
        dog.setVeterinary(null);
        return this;
    }

    public Set<Owner> getOwners() {
        return this.owners;
    }

    public void setOwners(Set<Owner> owners) {
        if (this.owners != null) {
            this.owners.forEach(i -> i.removeVeterinary(this));
        }
        if (owners != null) {
            owners.forEach(i -> i.addVeterinary(this));
        }
        this.owners = owners;
    }

    public Veterinary owners(Set<Owner> owners) {
        this.setOwners(owners);
        return this;
    }

    public Veterinary addOwner(Owner owner) {
        this.owners.add(owner);
        owner.getVeterinaries().add(this);
        return this;
    }

    public Veterinary removeOwner(Owner owner) {
        this.owners.remove(owner);
        owner.getVeterinaries().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Veterinary)) {
            return false;
        }
        return getId() != null && getId().equals(((Veterinary) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Veterinary{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", address='" + getAddress() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            "}";
    }
}
