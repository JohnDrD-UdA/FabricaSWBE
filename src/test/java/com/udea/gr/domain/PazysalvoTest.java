package com.udea.gr.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udea.gr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PazysalvoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pazysalvo.class);
        Pazysalvo pazysalvo1 = new Pazysalvo();
        pazysalvo1.setId(1L);
        Pazysalvo pazysalvo2 = new Pazysalvo();
        pazysalvo2.setId(pazysalvo1.getId());
        assertThat(pazysalvo1).isEqualTo(pazysalvo2);
        pazysalvo2.setId(2L);
        assertThat(pazysalvo1).isNotEqualTo(pazysalvo2);
        pazysalvo1.setId(null);
        assertThat(pazysalvo1).isNotEqualTo(pazysalvo2);
    }
}
