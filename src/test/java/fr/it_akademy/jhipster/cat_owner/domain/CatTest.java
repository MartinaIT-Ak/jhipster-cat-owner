package fr.it_akademy.jhipster.cat_owner.domain;

import static fr.it_akademy.jhipster.cat_owner.domain.CatTestSamples.*;
import static fr.it_akademy.jhipster.cat_owner.domain.OwnerTestSamples.*;
import static fr.it_akademy.jhipster.cat_owner.domain.VeterinaryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.jhipster.cat_owner.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cat.class);
        Cat cat1 = getCatSample1();
        Cat cat2 = new Cat();
        assertThat(cat1).isNotEqualTo(cat2);

        cat2.setId(cat1.getId());
        assertThat(cat1).isEqualTo(cat2);

        cat2 = getCatSample2();
        assertThat(cat1).isNotEqualTo(cat2);
    }

    @Test
    void veterinaryTest() throws Exception {
        Cat cat = getCatRandomSampleGenerator();
        Veterinary veterinaryBack = getVeterinaryRandomSampleGenerator();

        cat.setVeterinary(veterinaryBack);
        assertThat(cat.getVeterinary()).isEqualTo(veterinaryBack);

        cat.veterinary(null);
        assertThat(cat.getVeterinary()).isNull();
    }

    @Test
    void ownerTest() throws Exception {
        Cat cat = getCatRandomSampleGenerator();
        Owner ownerBack = getOwnerRandomSampleGenerator();

        cat.setOwner(ownerBack);
        assertThat(cat.getOwner()).isEqualTo(ownerBack);

        cat.owner(null);
        assertThat(cat.getOwner()).isNull();
    }
}
