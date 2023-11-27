package pe.gob.pj.depositos.infraestructure.db.entity.sij;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "MAE_DEP_INSTITUCION")
public class MaeDepInstitucion {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "c_ruc")
    private String cRuc;

    @Column(name = "x_razon")
    private String xRazon;

    @Column(name = "n_cuenta")
    private String nCuenta;

    @Column(name = "l_activo")
    private Boolean lActivo;

    // Getters y setters

    // Puedes agregar más campos y métodos según sea necesario
}

