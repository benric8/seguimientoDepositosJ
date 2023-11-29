package pe.gob.pj.depositos.domain.model.sij;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @ AllArgsConstructor
@Data
public class DepositoEstado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String descripcionEstado;
	private String fechaOperacion;
	private String nivel;
	private String activo;

}
