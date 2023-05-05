package com.udea.gr.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Materia.
 */
@Entity
@Table(name = "materia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 20)
    @Column(name = "nombre", length = 20, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 20)
    @Column(name = "tipomateria", length = 20, nullable = false)
    private String tipomateria;

    @ManyToOne(optional = false)
    @NotNull
    private Planestudios planestudiosId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Materia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Materia nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipomateria() {
        return this.tipomateria;
    }

    public Materia tipomateria(String tipomateria) {
        this.setTipomateria(tipomateria);
        return this;
    }

    public void setTipomateria(String tipomateria) {
        this.tipomateria = tipomateria;
    }

    public Planestudios getPlanestudiosId() {
        return this.planestudiosId;
    }

    public void setPlanestudiosId(Planestudios planestudios) {
        this.planestudiosId = planestudios;
    }

    public Materia planestudiosId(Planestudios planestudios) {
        this.setPlanestudiosId(planestudios);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Materia)) {
            return false;
        }
        return id != null && id.equals(((Materia) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Materia{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipomateria='" + getTipomateria() + "'" +
            "}";
    }
}
