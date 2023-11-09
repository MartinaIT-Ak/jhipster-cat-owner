package fr.it_akademy.jhipster.cat_owner.domain;

import static fr.it_akademy.jhipster.cat_owner.domain.CatTestSamples.*;
import static fr.it_akademy.jhipster.cat_owner.domain.OwnerTestSamples.*;
import static fr.it_akademy.jhipster.cat_owner.domain.VeterinaryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.jhipster.cat_owner.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class VeterinaryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Veterinary.class);
        Veterinary veterinary1 = getVeterinarySample1();
        Veterinary veterinary2 = new Veterinary();
        assertThat(veterinary1).isNotEqualTo(veterinary2);

        veterinary2.setId(veterinary1.getId());
        assertThat(veterinary1).isEqualTo(veterinary2);

        veterinary2 = getVeterinarySample2();
        assertThat(veterinary1).isNotEqualTo(veterinary2);
    }

    @Test
    void catTest() throws Exception {
        Veterinary veterinary = getVeterinaryRandomSampleGenerator();
        Cat catBack = getCatRandomSampleGenerator();

        veterinary.addCat(catBack);
        assertThat(veterinary.getCats()).containsOnly(catBack);
        assertThat(catBack.getVeterinary()).isEqualTo(veterinary);

        veterinary.removeCat(catBack);
        assertThat(veterinary.getCats()).doesNotContain(catBack);
        assertThat(catBack.getVeterinary()).isNull();

        veterinary.cats(new HashSet<>(Set.of(catBack)));
        assertThat(veterinary.getCats()).containsOnly(catBack);
        assertThat(catBack.getVeterinary()).isEqualTo(veterinary);

        veterinary.setCats(new HashSet<>());
        assertThat(veterinary.getCats()).doesNotContain(catBack);
        assertThat(catBack.getVeterinary()).isNull();
    }

    @Test
    void ownerTest() throws Exception {
        Veterinary veterinary = getVeterinaryRandomSampleGenerator();
        Owner ownerBack = getOwnerRandomSampleGenerator();

        veterinary.addOwner(ownerBack);
        assertThat(veterinary.getOwners()).containsOnly(ownerBack);
        assertThat(ownerBack.getVeterinaries()).containsOnly(veterinary);

        veterinary.removeOwner(ownerBack);
        assertThat(veterinary.getOwners()).doesNotContain(ownerBack);
        assertThat(ownerBack.getVeterinaries()).doesNotContain(veterinary);

        veterinary.owners(new HashSet<>(Set.of(ownerBack)));
        assertThat(veterinary.getOwners()).containsOnly(ownerBack);
        assertThat(ownerBack.getVeterinaries()).containsOnly(veterinary);

        veterinary.setOwners(new HashSet<>());
        assertThat(veterinary.getOwners()).doesNotContain(ownerBack);
        assertThat(ownerBack.getVeterinaries()).doesNotContain(veterinary);
    }
}
