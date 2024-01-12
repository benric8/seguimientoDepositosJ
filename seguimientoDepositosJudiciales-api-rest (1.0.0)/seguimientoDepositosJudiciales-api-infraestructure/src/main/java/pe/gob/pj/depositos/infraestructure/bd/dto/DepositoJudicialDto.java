package pe.gob.pj.depositos.infraestructure.bd.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import pe.gob.pj.depositos.infraestructure.db.entity.sij.MovDepOrdenPago;

@AllArgsConstructor
@Data
public class DepositoJudicialDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codigoDeposito;
	private String motivoDeposito;
	private String estado;
	private Date fechaRegistro;
	private Double nSaldo;
	private Date fechaPresentacion;
	private List<MovDepOrdenPago> ordenesPago;
	
	
}
