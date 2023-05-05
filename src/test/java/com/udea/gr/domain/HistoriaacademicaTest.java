package com.udea.gr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.gr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HistoriaacademicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Historiaacademica.class);
        Historiaacademica historiaacademica1 = new Historiaacademica();
        historiaacademica1.setId(1L);
        Historiaacademica historiaacademica2 = new Historiaacademica();
        historiaacademica2.setId(historiaacademica1.getId());
        assertThat(historiaacademica1).isEqualTo(historiaacademica2);
        historiaacademica2.setId(2L);
        assertThat(historiaacademica1).isNotEqualTo(historiaacademica2);
        historiaacademica1.setId(null);
        assertThat(historiaacademica1).isNotEqualTo(historiaacademica2);
    }
}
