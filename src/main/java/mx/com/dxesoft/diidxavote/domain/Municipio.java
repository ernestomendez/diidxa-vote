package mx.com.dxesoft.diidxavote.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Municipio.
 */
@Entity
@Table(name = "T_MUNICIPIO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Municipio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "cabecera_municipal")
    private String cabeceraMunicipal;

    @ManyToOne
    private Distrito distrito;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCabeceraMunicipal() {
        return cabeceraMunicipal;
    }

    public void setCabeceraMunicipal(String cabeceraMunicipal) {
        this.cabeceraMunicipal = cabeceraMunicipal;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Municipio municipio = (Municipio) o;

        if (id != null ? !id.equals(municipio.id) : municipio.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Municipio{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                ", cabeceraMunicipal='" + cabeceraMunicipal + "'" +
                '}';
    }
}
