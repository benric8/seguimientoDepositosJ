package pe.gob.pj.depositos.infraestructure.db.entity.sij;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "MAE_DEP_ESTADO")
public class MaeDepEstado {

    @Id
    @Column(name = "N_MAE_DEP_ESTADO")
    private Long nMaeDepEstado;

    @Column(name = "C_DOCUMENTO")
    private String cDocumento;

    @Column(name = "C_ESTADO")
    private String cEstado;

    @Column(name = "X_DESCRIPCION")
    private String xDescripcion;

    // Getters y setters

    // Puedes agregar más campos y métodos según sea necesario
}
