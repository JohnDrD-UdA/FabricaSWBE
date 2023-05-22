package com.udea.gr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Regmateria.
 */
@Entity
@Table(name = "regmateria")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Regmateria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "estado", length = 10, nullable = false)
    private String estado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "planestudiosId" }, allowSetters = true)
    private Materia materiaId;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "planestudiosId", "estudianteid" }, allowSetters = true)
    private Historiaacademica historiaacademicaId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Regmateria id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return this.estado;
    }

    public Regmateria estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Materia getMateriaId() {
        return this.materiaId;
    }

    public void setMateriaId(Materia materia) {
        this.materiaId = materia;
    }

    public Regmateria materiaId(Materia materia) {
        this.setMateriaId(materia);
        return this;
    }

    public Historiaacademica getHistoriaacademicaId() {
        return this.historiaacademicaId;
    }

    public void setHistoriaacademicaId(Historiaacademica historiaacademica) {
        this.historiaacademicaId = historiaacademica;
    }

    public Regmateria historiaacademicaId(Historiaacademica historiaacademica) {
        this.setHistoriaacademicaId(historiaacademica);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Regmateria)) {
            return false;
        }
        return id != null && id.equals(((Regmateria) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Regmateria{" +
            "id=" + getId() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
