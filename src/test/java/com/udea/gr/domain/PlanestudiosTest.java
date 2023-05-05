package com.udea.gr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.gr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanestudiosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Planestudios.class);
        Planestudios planestudios1 = new Planestudios();
        planestudios1.setId(1L);
        Planestudios planestudios2 = new Planestudios();
        planestudios2.setId(planestudios1.getId());
        assertThat(planestudios1).isEqualTo(planestudios2);
        planestudios2.setId(2L);
        assertThat(planestudios1).isNotEqualTo(planestudios2);
        planestudios1.setId(null);
        assertThat(planestudios1).isNotEqualTo(planestudios2);
    }
}
