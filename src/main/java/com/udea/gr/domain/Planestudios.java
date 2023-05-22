package com.udea.gr.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Planestudios.
 */
@Entity
@Table(name = "planestudios")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Planestudios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "idprograma", nullable = false)
    private Integer idprograma;

    @NotNull
    @Column(name = "facultad", nullable = false)
    private String facultad;

    @NotNull
    @Column(name = "nombreprograma", nullable = false)
    private String nombreprograma;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Planestudios id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdprograma() {
        return this.idprograma;
    }

    public Planestudios idprograma(Integer idprograma) {
        this.setIdprograma(idprograma);
        return this;
    }

    public void setIdprograma(Integer idprograma) {
        this.idprograma = idprograma;
    }

    public String getFacultad() {
        return this.facultad;
    }

    public Planestudios facultad(String facultad) {
        this.setFacultad(facultad);
        return this;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public String getNombreprograma() {
        return this.nombreprograma;
    }

    public Planestudios nombreprograma(String nombreprograma) {
        this.setNombreprograma(nombreprograma);
        return this;
    }

    public void setNombreprograma(String nombreprograma) {
        this.nombreprograma = nombreprograma;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Planestudios)) {
            return false;
        }
        return id != null && id.equals(((Planestudios) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Planestudios{" +
            "id=" + getId() +
            ", idprograma=" + getIdprograma() +
            ", facultad='" + getFacultad() + "'" +
            ", nombreprograma='" + getNombreprograma() + "'" +
            "}";
    }
}
