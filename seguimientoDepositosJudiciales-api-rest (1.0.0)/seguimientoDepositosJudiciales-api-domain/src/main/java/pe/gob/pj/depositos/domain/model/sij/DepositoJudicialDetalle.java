package pe.gob.pj.depositos.domain.model.sij;

import java.io.Serializable;
import java.util.List;

import lombok.Data;


@Data
public class DepositoJudicialDetalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private String codigoDeposito;
	 private String estado;
	 private String fechaRegistro;
	 private List<DepositoEstado> depositoEstados;
}
