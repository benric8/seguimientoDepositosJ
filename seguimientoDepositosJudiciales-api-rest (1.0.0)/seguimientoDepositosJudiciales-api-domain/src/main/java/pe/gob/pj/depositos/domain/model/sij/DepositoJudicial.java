package pe.gob.pj.depositos.domain.model.sij;

import java.io.Serializable;

import lombok.Data;

@Data
public class DepositoJudicial implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoDeposito;
	private String motivoDeposito;
	private String estado;
	private String descripcionEstado;
	private String fechaRegistro;
}
