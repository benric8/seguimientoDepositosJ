package pe.gob.pj.depositos.infraestructure.rest.adapter;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.depositos.domain.exceptions.ErrorDaoException;
import pe.gob.pj.depositos.domain.exceptions.ErrorException;
import pe.gob.pj.depositos.domain.model.sij.DepositoJudicialDetalle;
import pe.gob.pj.depositos.domain.port.usecase.ConsultaUseCasePort;
import pe.gob.pj.depositos.domain.utils.CaptchaUtils;
import pe.gob.pj.depositos.domain.utils.ProjectConstants;
import pe.gob.pj.depositos.domain.utils.ProjectUtils;
import pe.gob.pj.depositos.infraestructure.rest.request.ConsultaDepositoJudicialRequest;
import pe.gob.pj.depositos.infraestructure.rest.response.GlobalResponse;

@Slf4j
@RestController
@RequestMapping(value = "consulta")
public class ConsultaDJController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("consultaUseCasePort")
	private ConsultaUseCasePort consultaUC;

	@PostMapping(value = "depositosJudiciales", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GlobalResponse> consultarDeposito(
			@RequestAttribute(name = ProjectConstants.AUD_CUO) String cuo,
			@RequestAttribute(name = ProjectConstants.AUD_IP) String ipRemota,
			@Validated @RequestBody ConsultaDepositoJudicialRequest captcha) {

		log.info("{} INICIAMOS LA CONSULTA DEPOSITOS");
		log.info("{} captcha", captcha.getFormatoExpediente());
		log.info("{} INICIAMOS LA CONSULTA DEPOSITOS", captcha.getAplicaCaptcha());
		GlobalResponse res = new GlobalResponse();
		res.setCodigoOperacion(cuo.substring(1, cuo.length() - 1));
		try {
			try {

				if (!captcha.getAplicaCaptcha().equalsIgnoreCase(ProjectConstants.ESTADO_ACTIVO_S)
						|| (captcha.getAplicaCaptcha().equalsIgnoreCase(ProjectConstants.ESTADO_ACTIVO_S)
								&& !ProjectUtils.isNullOrEmpty(captcha.getTokenCaptcha()))) {
					if (!captcha.getAplicaCaptcha().equalsIgnoreCase(ProjectConstants.ESTADO_ACTIVO_S)
							|| CaptchaUtils.validCaptcha(captcha.getTokenCaptcha(), ipRemota, cuo)) {

						List<DepositoJudicialDetalle> lista = consultaUC.consultarDepositos(cuo,
								captcha.getFormatoExpediente());
						res.setCodigo(ProjectConstants.C_EXITO);
						res.setDescripcion(ProjectConstants.X_EXITO);
						res.setData(lista);
					} else {
						log.error(
								"{} Dstos de validación captcha -> indicador de validación: {}, token captcha: {} y la ip de la petición",
								cuo, captcha.getAplicaCaptcha(), captcha.getTokenCaptcha(), ipRemota);
						throw new ErrorException(ProjectConstants.C_ERROR_CAPTCHA,
								ProjectConstants.X_ERROR + ProjectConstants.Proceso.CONSULTA_DEPOSITO_JUDICIAL
										+ ProjectConstants.X_ERROR_CAPTCHA);
					}
				} else {
					log.error(
							"{} Dstos de validación captcha -> indicador de validación: {}, token captcha: {} y la ip de la petición",
							cuo, captcha.getAplicaCaptcha(), captcha.getTokenCaptcha(), ipRemota);
					throw new ErrorException(ProjectConstants.C_ERROR_CAPTCHA, ProjectConstants.X_ERROR
							+ ProjectConstants.Proceso.CONSULTA_DEPOSITO_JUDICIAL + ProjectConstants.X_ERROR_CAPTCHA);
				}
			} catch (ErrorDaoException | ErrorException e) {
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				throw new ErrorException(ProjectConstants.C_ERROR_INESPERADO, ProjectConstants.X_ERROR
						+ ProjectConstants.Proceso.CONSULTA_DEPOSITO_JUDICIAL + ProjectConstants.X_ERROR_INESPERADO,
						e.getMessage(), e.getCause());
			}
		} catch (ErrorException e) {
			e.printStackTrace();
			res.setCodigo(e.getCodigo());
			res.setDescripcion(e.getDescripcion());
			log.error("{} Error al consultar depositos judicales, Descripción: {}", cuo, e.getDescripcion());
			log.error("{} Error al consultar depositos judicales, Detalle => ERROR: {} | CAUSA {}", cuo, e.getMessage(),
					ProjectUtils.obtenerCausaException(e));
		} catch (ErrorDaoException e) {
			e.printStackTrace();
			res.setCodigo(e.getCodigo());
			res.setDescripcion(e.getDescripcion());
			log.error("{} Error al consultar depositos judicales, Descripción: {}", cuo, e.getDescripcion());
			log.error("{} Error al consultar depositos judicales, ERROR SP: {} ERROR DB: {}", cuo,
					e.getDescripcionErrorSP() != null ? e.getDescripcionErrorSP() : "",
					e.getDescripcionErrorDB() != null ? e.getDescripcionErrorDB() : "");
			log.error("{} Error al consultar depositos judicales, Detalle => ERROR: {} | CAUSA {}", cuo, e.getMessage(),
					ProjectUtils.obtenerCausaException(e));
		} catch (Exception e) {
			e.printStackTrace();
			res.setCodigo(ProjectConstants.C_ERROR_INESPERADO);
			res.setDescripcion(ProjectConstants.X_ERROR + ProjectConstants.Proceso.CONSULTA_DEPOSITO_JUDICIAL
					+ ProjectConstants.X_ERROR_INESPERADO);
			log.error("{} Error al consultar depositos judicales, Descripción : {}", cuo, res.getDescripcion());
			log.error("{} Error al consultar depositos judicales, Detalle => ERROR: {} | CAUSA {}", cuo, e.getMessage(),
					ProjectUtils.obtenerCausaException(e));
		}

		return new ResponseEntity<GlobalResponse>(res, HttpStatus.OK);

	}

}
