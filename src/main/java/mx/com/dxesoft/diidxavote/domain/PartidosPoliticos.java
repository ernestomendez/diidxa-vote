package mx.com.dxesoft.diidxavote.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A PartidosPoliticos.
 */
@Entity
@Table(name = "T_PARTIDOSPOLITICOS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PartidosPoliticos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartidosPoliticos partidosPoliticos = (PartidosPoliticos) o;

        if (id != null ? !id.equals(partidosPoliticos.id) : partidosPoliticos.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "PartidosPoliticos{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                '}';
    }
}
