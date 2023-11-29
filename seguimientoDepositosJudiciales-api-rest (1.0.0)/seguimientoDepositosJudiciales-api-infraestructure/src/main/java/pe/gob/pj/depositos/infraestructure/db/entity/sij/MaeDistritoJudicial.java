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




@EqualsAndHashCode(callSuper =  false)
@Data
@Entity
@Table(name = "MAE_DISTRITO_JUDICIAL", schema = ProjectConstants.Esquema.SIJ_002)
public class MaeDistritoJudicial extends Auditoria implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "c_distritoj")
    private String cDistritoJ;

    @Column(name = "x_nom_distritoj")
    private String xNomDistritoJ;

    @Column(name = "l_estado")
    private Boolean lEstado;

}

