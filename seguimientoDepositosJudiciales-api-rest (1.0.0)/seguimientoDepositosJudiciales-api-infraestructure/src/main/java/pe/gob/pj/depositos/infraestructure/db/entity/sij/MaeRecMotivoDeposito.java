package pe.gob.pj.depositos.infraestructure.db.entity.sij;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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

@NamedQueries(value= {
	
		@NamedQuery(name=MaeRecMotivoDeposito.FIND_MOTIVO_BY_CODIGO, query = "SELECT m.xDescMotivo FROM MaeRecMotivoDeposito m WHERE m.cMotivo= :cMotivo")
})
public class MaeRecMotivoDeposito extends Auditoria {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String FIND_MOTIVO_BY_CODIGO = "MaeRecMotivoDeposito.findMotivoByCodigo";
	public static final String C_MOTIVO = "cMotivo";
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
