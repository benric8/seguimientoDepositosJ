package pe.gob.pj.depositos.domain.model.sij;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrdenPago implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cDepositoJ;
	private String cOrdenPago;
	private String codigoEstado;
	private String fechaEndoso;
	private String fechaCobro;
	
}
