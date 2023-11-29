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
@Table(name = "MAE_DEP_INSTITUCION", schema = ProjectConstants.Esquema.SIJ_002)
public class MaeDepInstitucion implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

