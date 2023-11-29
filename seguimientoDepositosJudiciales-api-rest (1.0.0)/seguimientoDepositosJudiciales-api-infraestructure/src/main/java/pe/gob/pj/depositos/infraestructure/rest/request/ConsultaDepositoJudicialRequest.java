package pe.gob.pj.depositos.infraestructure.rest.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import pe.gob.pj.depositos.domain.utils.ProjectConstants;

@Data
public class ConsultaDepositoJudicialRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Pattern(regexp = ProjectConstants.PATTERN_FORMATO_EXPEDIENTE, message = "El parámetro formato expediente solo permite el formato.")
	@NotNull(message = "El parámetro formato expediente no puede tener un valor nulo.")
	@NotBlank(message = "El parámetro formato expediente no puede tener un valor vacio.")
	@JsonProperty("formatoExpediente")
	private String formatoExpediente;
	
	

	@Pattern(regexp = ProjectConstants.PATTERN_S_N, message = "El parámetro aplicaCaptcha tiene un formato no válido [S|N].")
	@NotNull(message = "El parámetro aplicaCaptcha no puede ser nulo.")
	@NotNull(message = "El parámetro formato expediente no puede tener un valor nulo.")
	@JsonProperty("aplicaCaptcha")
	private String aplicaCaptcha;

	// @NotNull(message = "El captcha no puede ser null")
	// @NotBlank(message = "El captcha tiene un valor incorrecto")
	@JsonProperty("tokenCaptcha")
	private String tokenCaptcha;
}
