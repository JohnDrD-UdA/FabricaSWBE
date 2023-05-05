package com.udea.gr.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ceremoniagrados.
 */
@Entity
@Table(name = "ceremoniagrados")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ceremoniagrados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "liminscripcion", nullable = false)
    private LocalDate liminscripcion;

    @NotNull
    @Column(name = "lugar", nullable = false)
    private String lugar;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ceremoniagrados id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Ceremoniagrados fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getLiminscripcion() {
        return this.liminscripcion;
    }

    public Ceremoniagrados liminscripcion(LocalDate liminscripcion) {
        this.setLiminscripcion(liminscripcion);
        return this;
    }

    public void setLiminscripcion(LocalDate liminscripcion) {
        this.liminscripcion = liminscripcion;
    }

    public String getLugar() {
        return this.lugar;
    }

    public Ceremoniagrados lugar(String lugar) {
        this.setLugar(lugar);
        return this;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ceremoniagrados)) {
            return false;
        }
        return id != null && id.equals(((Ceremoniagrados) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ceremoniagrados{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", liminscripcion='" + getLiminscripcion() + "'" +
            ", lugar='" + getLugar() + "'" +
            "}";
    }
}
