package mx.com.dxesoft.diidxavote.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * A Elector.
 */
@Entity
@Table(name = "T_ELECTOR")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Elector implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "genero")
    private String genero;

    @Column(name = "consecutivo_ife")
    private Long consecutivoIFE;

    @Column(name = "rol")
    private String rol;

    @Column(name = "email")
    private String email;

    @Column(name = "celular")
    private String celular;

    @Column(name = "telefono")
    private String telefono;

    @ManyToOne
    private Estado estado;

    @ManyToOne
    private Distrito distrito;

    @ManyToOne
    private Municipio municipio;

    @ManyToOne
    private Ruta ruta;

    @ManyToOne
    private Seccion seccion;

    @ManyToOne
    private PartidosPoliticos partidosPoliticos;

    @ManyToOne
    private Elector responsable;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "seccional_movilizador",
            joinColumns={@JoinColumn(name = "seccional_id")},
            inverseJoinColumns={@JoinColumn(name = "movilizador_id")})
    @JsonManagedReference("movilizador")
    private List<Elector> movilizador;

    @ManyToMany(mappedBy = "movilizador", fetch = FetchType.EAGER)
    @JsonManagedReference("movilizador")
    private List<Elector> seccional;

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Long getConsecutivoIFE() {
        return consecutivoIFE;
    }

    public void setConsecutivoIFE(Long consecutivoIFE) {
        this.consecutivoIFE = consecutivoIFE;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Distrito getDistrito() {
        return distrito;
    }

    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public PartidosPoliticos getPartidosPoliticos() {
        return partidosPoliticos;
    }

    public void setPartidosPoliticos(PartidosPoliticos partidosPoliticos) {
        this.partidosPoliticos = partidosPoliticos;
    }

    public Elector getResponsable() {
        return responsable;
    }

    public void setResponsable(Elector responsable) {
        this.responsable = responsable;
    }

    public List<Elector> getMovilizador() {
        return movilizador;
    }

    public void setMovilizador(List<Elector> movilizador) {
        this.movilizador = movilizador;
    }

    public List<Elector> getSeccional() {
        return seccional;
    }

    public void setSeccional(List<Elector> seccional) {
        this.seccional = seccional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Elector elector = (Elector) o;

        if (id != null ? !id.equals(elector.id) : elector.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Elector{" +
                "id=" + id +
                ", nombre='" + nombre + "'" +
                ", direccion='" + direccion + "'" +
                ", edad='" + edad + "'" +
                ", genero='" + genero + "'" +
                ", consecutivoIFE='" + consecutivoIFE + "'" +
                ", rol='" + rol + "'" +
                ", email='" + email + "'" +
                ", celular='" + celular + "'" +
                ", telefono='" + telefono + "'" +
                '}';
    }
}
