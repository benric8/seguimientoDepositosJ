package pe.gob.pj.depositos.infraestructure.db.entity.sij;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.pj.depositos.domain.utils.ProjectConstants;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "MAE_CONTROL_SECUENCIA", schema = ProjectConstants.Esquema.SIJ_002)
public class MaeControlSecuencia implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
