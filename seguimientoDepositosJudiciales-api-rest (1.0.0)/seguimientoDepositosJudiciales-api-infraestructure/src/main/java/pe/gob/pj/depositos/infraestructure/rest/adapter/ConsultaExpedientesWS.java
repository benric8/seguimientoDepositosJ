package pe.gob.pj.depositos.infraestructure.rest.adapter;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.cliente.consulta.expedientes.ws.ResponseDetalleExpedienteType;
import pe.gob.pj.cliente.consulta.expedientes.ws.ResponseListarExpedientesType;
import pe.gob.pj.depositos.domain.exceptions.ErrorException;
import pe.gob.pj.depositos.domain.utils.ProjectConstants;
import pe.gob.pj.depositos.domain.utils.ProjectUtils;
import pe.gob.pj.depositos.infraestructure.client.dtos.ExpedienteDto;
import pe.gob.pj.depositos.infraestructure.client.services.ExpedienteWsService;
import pe.gob.pj.depositos.infraestructure.rest.response.GlobalResponse;

@Slf4j
@RestController
@RequestMapping(value="consultaWS", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ConsultaExpedientesWS implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	@Qualifier("expedienteWsService")
	private ExpedienteWsService expedienteWS;
	
	@GetMapping(value="expedientes")
	public ResponseEntity<GlobalResponse> buscarExpediente(@RequestAttribute(name=ProjectConstants.AUD_CUO) String cuo,
			@NotBlank(message = "El parámetro numeroDocumentoIdentidad no puede tener un valor vacio.")
			@RequestParam(name="xformato", required = true) String xFormato){
		
		GlobalResponse res = new GlobalResponse();
		ExpedienteDto expedienteEncontrado =new ExpedienteDto();
		res.setCodigoOperacion(cuo.substring(1, cuo.length()-1));
		log.info("INICIAMOS EL PROCESO DE CONSULTA AL WEB SERVICE CONSULTA EXPEDIENTE");
		try {
			ResponseListarExpedientesType responseExpediente = expedienteWS.listarExpedientes(xFormato);
			log.info("OBTENIENDO LOS DATOS NECESARIO PARA LA CONSULTA DETALLE EXPEDIENTE DEL WSEXPEDIENTE {} ",xFormato);
			if(responseExpediente.getMensaje()!=null&&responseExpediente.getListaExpedientes()==null) {
				log.info("LA CONEXION CON EL METODO FUE EXITOSA PERO NO SE ENCONTRO EL EXPEDIENTE {}",responseExpediente.getMensaje().getDescripcion());
				res.setCodigo(ProjectConstants.C_EXITO);
				res.setDescripcion(ProjectConstants.X_EXITO);
				res.setData(null);
			}
			else if(responseExpediente.getListaExpedientes()!=null) {
				expedienteEncontrado.setCodConexion(responseExpediente.getListaExpedientes().getDatosExpediente().get(0).getCodigoConexion());
				expedienteEncontrado.setNumeroUnico(Long.parseLong(responseExpediente.getListaExpedientes().getDatosExpediente().get(0).getNumeroUnico()));
				expedienteEncontrado.setNumeroIncidente(Long.parseLong(responseExpediente.getListaExpedientes().getDatosExpediente().get(0).getNumeroIncidente()));
				log.info("CONEXION EXITOSA LOS DATOS OBTENIDOS FUERON codConexion {} NumeroUnico {} NumeroIncidente {}",expedienteEncontrado.getCodConexion(),expedienteEncontrado.getNumeroUnico(),expedienteEncontrado.getNumeroIncidente());
				log.info("INICIAMOS LA CONSULTA AL METODO DETALLE EXPEDIENTE DEL SWEXPEDIENTE");
				ResponseDetalleExpedienteType responseDetalleExpediente = expedienteWS.detalleExpediente(expedienteEncontrado);
				if(responseDetalleExpediente.getExpedienteDetalle()!=null) {
					expedienteEncontrado.setNumeroExpediente(responseDetalleExpediente.getExpedienteDetalle().getCodigoExpediente());
					expedienteEncontrado.setFecha(responseDetalleExpediente.getExpedienteDetalle().getFechaIngresoExpediente());
					expedienteEncontrado.setDescripcionSede(responseDetalleExpediente.getExpedienteDetalle().getDescripcionSede());
					expedienteEncontrado.setNombreProvincia(responseDetalleExpediente.getExpedienteDetalle().getDescripcionProvincia());
					expedienteEncontrado.setJuez(responseDetalleExpediente.getExpedienteDetalle().getNombreJuez());
					expedienteEncontrado.setNombreJuzgado(responseDetalleExpediente.getExpedienteDetalle().getNombreJuzgado());
					expedienteEncontrado.setEspecialista(responseDetalleExpediente.getExpedienteDetalle().getNombreEspecialista());
					expedienteEncontrado.setNombreEspecialidad(responseDetalleExpediente.getExpedienteDetalle().getNombreEspecialidad());
					expedienteEncontrado.setProceso(responseDetalleExpediente.getExpedienteDetalle().getProceso());
					expedienteEncontrado.setMateria(responseDetalleExpediente.getExpedienteDetalle().getMateria());
					expedienteEncontrado.setNombreDistrito(responseDetalleExpediente.getExpedienteDetalle().getNombreDistrito());
					expedienteEncontrado.setNombreInstancia(responseDetalleExpediente.getExpedienteDetalle().getNombreInstancia());
					log.info("FIN DE LA CONSULTA DEL METODO DETALLE EXPEDIENTE DEL SWEXPEDIENTE");
					res.setCodigo(ProjectConstants.C_EXITO);
					res.setDescripcion(ProjectConstants.X_EXITO);
					res.setData(expedienteEncontrado);
				}else if (responseDetalleExpediente.getMensaje()!=null && responseDetalleExpediente.getExpedienteDetalle()==null) {
					log.info("LA CONEXION CON EL METODO FUE EXITOSA PERO NO SE ENCONTRO DETALLE DEL EXPEDIENTE {}",responseDetalleExpediente.getMensaje().getDescripcion());
					res.setCodigo(ProjectConstants.C_EXITO);
					res.setDescripcion(ProjectConstants.X_EXITO);
					res.setData(null);
				}
			}
				
		} catch(ErrorException e) {
			res.setCodigo(e.getCodigo());
		    res.setDescripcion(e.getDescripcion());
		    log.error("{} {} | {} | {} | {} | {} | {}", cuo, ProjectConstants.X_TRAZA_LOG, res.getCodigo(), res.getDescripcion(), ProjectUtils.getClassMethodLineException(e), e.getMessage(), ProjectUtils.obtenerCausaException(e));
		
		}catch(Exception e) {
			ErrorException ee = new ErrorException(ProjectConstants.C_E000, 
			        ProjectConstants.X_ERROR + ProjectConstants.Proceso.CONSULTA_CEJ_UNICO + ProjectConstants.X_E000,
			        e.getMessage(), e.getCause());
			res.setCodigo(ee.getCodigo());
		    res.setDescripcion(ee.getDescripcion());
		    log.error("{} {} | {} | {} | {} | {} | {}", cuo, ProjectConstants.X_TRAZA_LOG, res.getCodigo(), res.getDescripcion(), ProjectUtils.getClassMethodLineException(e), e.getMessage(), ProjectUtils.obtenerCausaException(e));
		
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
}
