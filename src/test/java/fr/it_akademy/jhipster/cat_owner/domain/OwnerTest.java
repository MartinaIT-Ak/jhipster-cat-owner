package fr.it_akademy.jhipster.cat_owner.domain;

import static fr.it_akademy.jhipster.cat_owner.domain.CatTestSamples.*;
import static fr.it_akademy.jhipster.cat_owner.domain.DogTestSamples.*;
import static fr.it_akademy.jhipster.cat_owner.domain.OwnerTestSamples.*;
import static fr.it_akademy.jhipster.cat_owner.domain.VeterinaryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.jhipster.cat_owner.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class OwnerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Owner.class);
        Owner owner1 = getOwnerSample1();
        Owner owner2 = new Owner();
        assertThat(owner1).isNotEqualTo(owner2);

        owner2.setId(owner1.getId());
        assertThat(owner1).isEqualTo(owner2);

        owner2 = getOwnerSample2();
        assertThat(owner1).isNotEqualTo(owner2);
    }

    @Test
    void catsTest() throws Exception {
        Owner owner = getOwnerRandomSampleGenerator();
        Cat catBack = getCatRandomSampleGenerator();

        owner.addCats(catBack);
        assertThat(owner.getCats()).containsOnly(catBack);
        assertThat(catBack.getOwner()).isEqualTo(owner);

        owner.removeCats(catBack);
        assertThat(owner.getCats()).doesNotContain(catBack);
        assertThat(catBack.getOwner()).isNull();

        owner.cats(new HashSet<>(Set.of(catBack)));
        assertThat(owner.getCats()).containsOnly(catBack);
        assertThat(catBack.getOwner()).isEqualTo(owner);

        owner.setCats(new HashSet<>());
        assertThat(owner.getCats()).doesNotContain(catBack);
        assertThat(catBack.getOwner()).isNull();
    }

    @Test
    void dogTest() throws Exception {
        Owner owner = getOwnerRandomSampleGenerator();
        Dog dogBack = getDogRandomSampleGenerator();

        owner.addDog(dogBack);
        assertThat(owner.getDogs()).containsOnly(dogBack);
        assertThat(dogBack.getOwner()).isEqualTo(owner);

        owner.removeDog(dogBack);
        assertThat(owner.getDogs()).doesNotContain(dogBack);
        assertThat(dogBack.getOwner()).isNull();

        owner.dogs(new HashSet<>(Set.of(dogBack)));
        assertThat(owner.getDogs()).containsOnly(dogBack);
        assertThat(dogBack.getOwner()).isEqualTo(owner);

        owner.setDogs(new HashSet<>());
        assertThat(owner.getDogs()).doesNotContain(dogBack);
        assertThat(dogBack.getOwner()).isNull();
    }

    @Test
    void veterinaryTest() throws Exception {
        Owner owner = getOwnerRandomSampleGenerator();
        Veterinary veterinaryBack = getVeterinaryRandomSampleGenerator();

        owner.addVeterinary(veterinaryBack);
        assertThat(owner.getVeterinaries()).containsOnly(veterinaryBack);

        owner.removeVeterinary(veterinaryBack);
        assertThat(owner.getVeterinaries()).doesNotContain(veterinaryBack);

        owner.veterinaries(new HashSet<>(Set.of(veterinaryBack)));
        assertThat(owner.getVeterinaries()).containsOnly(veterinaryBack);

        owner.setVeterinaries(new HashSet<>());
        assertThat(owner.getVeterinaries()).doesNotContain(veterinaryBack);
    }
}
