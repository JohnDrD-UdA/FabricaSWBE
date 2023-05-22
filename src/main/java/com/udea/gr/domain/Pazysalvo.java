package com.udea.gr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pazysalvo.
 */
@Entity
@Table(name = "pazysalvo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pazysalvo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "materiasobl", nullable = false)
    private Boolean materiasobl;

    @NotNull
    @Column(name = "materiaselec", nullable = false)
    private Boolean materiaselec;

    @NotNull
    @Column(name = "pendientesnota", nullable = false)
    private Boolean pendientesnota;

    @NotNull
    @Column(name = "biblioteca", nullable = false)
    private Boolean biblioteca;

    @NotNull
    @Column(name = "cartera", nullable = false)
    private Boolean cartera;

    @NotNull
    @Column(name = "impedimento", nullable = false)
    private Boolean impedimento;

    @ManyToOne(optional = false)
    @NotNull
    private Ceremoniagrados ceremoniagradosId;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "planestudiosId", "estudianteid" }, allowSetters = true)
    private Historiaacademica historiaacademicaId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pazysalvo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

    public Pazysalvo fecha(LocalDate fecha) {
        this.setFecha(fecha);
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Boolean getMateriasobl() {
        return this.materiasobl;
    }

    public Pazysalvo materiasobl(Boolean materiasobl) {
        this.setMateriasobl(materiasobl);
        return this;
    }

    public void setMateriasobl(Boolean materiasobl) {
        this.materiasobl = materiasobl;
    }

    public Boolean getMateriaselec() {
        return this.materiaselec;
    }

    public Pazysalvo materiaselec(Boolean materiaselec) {
        this.setMateriaselec(materiaselec);
        return this;
    }

    public void setMateriaselec(Boolean materiaselec) {
        this.materiaselec = materiaselec;
    }

    public Boolean getPendientesnota() {
        return this.pendientesnota;
    }

    public Pazysalvo pendientesnota(Boolean pendientesnota) {
        this.setPendientesnota(pendientesnota);
        return this;
    }

    public void setPendientesnota(Boolean pendientesnota) {
        this.pendientesnota = pendientesnota;
    }

    public Boolean getBiblioteca() {
        return this.biblioteca;
    }

    public Pazysalvo biblioteca(Boolean biblioteca) {
        this.setBiblioteca(biblioteca);
        return this;
    }

    public void setBiblioteca(Boolean biblioteca) {
        this.biblioteca = biblioteca;
    }

    public Boolean getCartera() {
        return this.cartera;
    }

    public Pazysalvo cartera(Boolean cartera) {
        this.setCartera(cartera);
        return this;
    }

    public void setCartera(Boolean cartera) {
        this.cartera = cartera;
    }

    public Boolean getImpedimento() {
        return this.impedimento;
    }

    public Pazysalvo impedimento(Boolean impedimento) {
        this.setImpedimento(impedimento);
        return this;
    }

    public void setImpedimento(Boolean impedimento) {
        this.impedimento = impedimento;
    }

    public Ceremoniagrados getCeremoniagradosId() {
        return this.ceremoniagradosId;
    }

    public void setCeremoniagradosId(Ceremoniagrados ceremoniagrados) {
        this.ceremoniagradosId = ceremoniagrados;
    }

    public Pazysalvo ceremoniagradosId(Ceremoniagrados ceremoniagrados) {
        this.setCeremoniagradosId(ceremoniagrados);
        return this;
    }

    public Historiaacademica getHistoriaacademicaId() {
        return this.historiaacademicaId;
    }

    public void setHistoriaacademicaId(Historiaacademica historiaacademica) {
        this.historiaacademicaId = historiaacademica;
    }

    public Pazysalvo historiaacademicaId(Historiaacademica historiaacademica) {
        this.setHistoriaacademicaId(historiaacademica);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pazysalvo)) {
            return false;
        }
        return id != null && id.equals(((Pazysalvo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pazysalvo{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", materiasobl='" + getMateriasobl() + "'" +
            ", materiaselec='" + getMateriaselec() + "'" +
            ", pendientesnota='" + getPendientesnota() + "'" +
            ", biblioteca='" + getBiblioteca() + "'" +
            ", cartera='" + getCartera() + "'" +
            ", impedimento='" + getImpedimento() + "'" +
            "}";
    }
}
