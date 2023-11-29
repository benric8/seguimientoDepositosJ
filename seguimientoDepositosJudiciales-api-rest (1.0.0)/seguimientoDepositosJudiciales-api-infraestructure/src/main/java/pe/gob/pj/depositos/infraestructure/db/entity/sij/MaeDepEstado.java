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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "MAE_DEP_ESTADO", schema = ProjectConstants.Esquema.SIJ_002)
public class MaeDepEstado implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
