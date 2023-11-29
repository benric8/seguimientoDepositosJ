package pe.gob.pj.depositos.infraestructure.db.entity.sij;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import pe.gob.pj.depositos.domain.utils.ProjectConstants;
import pe.gob.pj.depositos.infraestructure.db.entity.Auditoria;

import java.io.Serializable;


@EqualsAndHashCode(callSuper=false)
@Data
@Entity
@Table(name = "MAE_REC_CONTROL_SECUENCIA", schema = ProjectConstants.Esquema.SIJ_002)
public class MaeRecControlSecuencia extends Auditoria implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "n_secuencia")
    private Long nSecuencia;

    @Column(name = "N_MAE_REC_CTRL_SEC")
    private Long nMaeRecCtrlSec;

    @Column(name = "N_MAE_CTRL_SEC")
    private Long nMaeCtrlSec;

    @Column(name = "l_bloqueo")
    private Boolean lBloqueo;

    @Column(name = "c_tipo")
    private String cTipo;

    @Column(name = "c_anho")
    private String cAnho;

}
