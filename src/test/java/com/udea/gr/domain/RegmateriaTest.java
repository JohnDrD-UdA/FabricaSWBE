package com.udea.gr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.gr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegmateriaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regmateria.class);
        Regmateria regmateria1 = new Regmateria();
        regmateria1.setId(1L);
        Regmateria regmateria2 = new Regmateria();
        regmateria2.setId(regmateria1.getId());
        assertThat(regmateria1).isEqualTo(regmateria2);
        regmateria2.setId(2L);
        assertThat(regmateria1).isNotEqualTo(regmateria2);
        regmateria1.setId(null);
        assertThat(regmateria1).isNotEqualTo(regmateria2);
    }
}
