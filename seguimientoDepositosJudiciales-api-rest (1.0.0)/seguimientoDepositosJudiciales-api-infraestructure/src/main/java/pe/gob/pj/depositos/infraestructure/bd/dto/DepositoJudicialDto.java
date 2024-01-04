package pe.gob.pj.depositos.infraestructure.bd.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DepositoJudicialDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codigoDeposito;
	private String motivoDeposito;
	private String estado;
	private Date fechaRegistro;
	private Double nSaldo;
}
