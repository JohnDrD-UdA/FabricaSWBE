package com.udea.gr.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Historiaacademica.
 */
@Entity
@Table(name = "historiaacademica")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Historiaacademica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    private User userId;

    @ManyToOne(optional = false)
    @NotNull
    private Planestudios planestudiosId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Historiaacademica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return this.userId;
    }

    public void setUserId(User user) {
        this.userId = user;
    }

    public Historiaacademica userId(User user) {
        this.setUserId(user);
        return this;
    }

    public Planestudios getPlanestudiosId() {
        return this.planestudiosId;
    }

    public void setPlanestudiosId(Planestudios planestudios) {
        this.planestudiosId = planestudios;
    }

    public Historiaacademica planestudiosId(Planestudios planestudios) {
        this.setPlanestudiosId(planestudios);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Historiaacademica)) {
            return false;
        }
        return id != null && id.equals(((Historiaacademica) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Historiaacademica{" +
            "id=" + getId() +
            "}";
    }
}
