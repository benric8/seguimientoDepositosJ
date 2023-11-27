package pe.gob.pj.depositos.infraestructure.db.entity.sij;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

@Entity
@Table(name = "MAE_CONTROL_SECUENCIA")
public class MaeControlSecuencia {

    @Id
    @Column(name = "N_MAE_CTRL_SEC")
    private Long nMaeCtrlSec;

    @Column(name = "c_anho")
    private String cAnho;

    @Column(name = "c_tipo")
    private String cTipo;

    @Column(name = "n_secuencia")
    private Long nSecuencia;

    @Column(name = "l_bloqueo")
    private Boolean lBloqueo;

    // Getters y setters

    // Puedes agregar más campos y métodos según sea necesario
}
