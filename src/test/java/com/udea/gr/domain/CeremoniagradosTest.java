package com.udea.gr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.gr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CeremoniagradosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ceremoniagrados.class);
        Ceremoniagrados ceremoniagrados1 = new Ceremoniagrados();
        ceremoniagrados1.setId(1L);
        Ceremoniagrados ceremoniagrados2 = new Ceremoniagrados();
        ceremoniagrados2.setId(ceremoniagrados1.getId());
        assertThat(ceremoniagrados1).isEqualTo(ceremoniagrados2);
        ceremoniagrados2.setId(2L);
        assertThat(ceremoniagrados1).isNotEqualTo(ceremoniagrados2);
        ceremoniagrados1.setId(null);
        assertThat(ceremoniagrados1).isNotEqualTo(ceremoniagrados2);
    }
}
