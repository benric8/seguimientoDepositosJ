package pe.gob.pj.depositos.infraestructure.db.entity.sij;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pe.gob.pj.depositos.domain.utils.ProjectConstants;
import pe.gob.pj.depositos.infraestructure.db.entity.Auditoria;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Data
@Entity
@Table(name = "\"MAE_REC_MOTIVO_DEPOSITOJ\"" , schema = ProjectConstants.Esquema.SIJ_002)
public class MaeRecMotivoDeposito extends Auditoria {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "c_motivo")
    private String cMotivo;

    @Column(name = "x_desc_motivo")
    private String xDescMotivo;

    @Column(name = "l_activo")
    private boolean lActivo;

    @Column(name = "l_envio")
    private boolean lEnvio;

    @Column(name = "c_motivo_bn")
    private String cMotivoBn;

    
}
