package fr.it_akademy.jhipster.cat_owner.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.jhipster.cat_owner.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VeterinaryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VeterinaryDTO.class);
        VeterinaryDTO veterinaryDTO1 = new VeterinaryDTO();
        veterinaryDTO1.setId(1L);
        VeterinaryDTO veterinaryDTO2 = new VeterinaryDTO();
        assertThat(veterinaryDTO1).isNotEqualTo(veterinaryDTO2);
        veterinaryDTO2.setId(veterinaryDTO1.getId());
        assertThat(veterinaryDTO1).isEqualTo(veterinaryDTO2);
        veterinaryDTO2.setId(2L);
        assertThat(veterinaryDTO1).isNotEqualTo(veterinaryDTO2);
        veterinaryDTO1.setId(null);
        assertThat(veterinaryDTO1).isNotEqualTo(veterinaryDTO2);
    }
}
