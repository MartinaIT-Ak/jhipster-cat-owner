package fr.it_akademy.jhipster.cat_owner.domain;

import static fr.it_akademy.jhipster.cat_owner.domain.DogTestSamples.*;
import static fr.it_akademy.jhipster.cat_owner.domain.OwnerTestSamples.*;
import static fr.it_akademy.jhipster.cat_owner.domain.VeterinaryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.jhipster.cat_owner.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dog.class);
        Dog dog1 = getDogSample1();
        Dog dog2 = new Dog();
        assertThat(dog1).isNotEqualTo(dog2);

        dog2.setId(dog1.getId());
        assertThat(dog1).isEqualTo(dog2);

        dog2 = getDogSample2();
        assertThat(dog1).isNotEqualTo(dog2);
    }

    @Test
    void ownerTest() throws Exception {
        Dog dog = getDogRandomSampleGenerator();
        Owner ownerBack = getOwnerRandomSampleGenerator();

        dog.setOwner(ownerBack);
        assertThat(dog.getOwner()).isEqualTo(ownerBack);

        dog.owner(null);
        assertThat(dog.getOwner()).isNull();
    }

    @Test
    void veterinaryTest() throws Exception {
        Dog dog = getDogRandomSampleGenerator();
        Veterinary veterinaryBack = getVeterinaryRandomSampleGenerator();

        dog.setVeterinary(veterinaryBack);
        assertThat(dog.getVeterinary()).isEqualTo(veterinaryBack);

        dog.veterinary(null);
        assertThat(dog.getVeterinary()).isNull();
    }
}
