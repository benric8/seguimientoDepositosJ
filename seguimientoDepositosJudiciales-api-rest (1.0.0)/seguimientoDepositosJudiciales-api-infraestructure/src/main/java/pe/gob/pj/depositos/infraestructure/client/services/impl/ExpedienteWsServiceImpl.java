package pe.gob.pj.depositos.infraestructure.client.services.impl;

import java.net.URL;


import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;


import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import pe.gob.pj.cliente.consulta.expedientes.ws.AuditoriaType;
import pe.gob.pj.cliente.consulta.expedientes.ws.RequestDetalleExpedienteSupremaType;
import pe.gob.pj.cliente.consulta.expedientes.ws.RequestDetalleExpedienteType;
import pe.gob.pj.cliente.consulta.expedientes.ws.RequestListarExpedientesSupremaType;
import pe.gob.pj.cliente.consulta.expedientes.ws.RequestListarExpedientesType;
import pe.gob.pj.cliente.consulta.expedientes.ws.ResponseDetalleExpedienteSupremaType;
import pe.gob.pj.cliente.consulta.expedientes.ws.ResponseDetalleExpedienteType;
import pe.gob.pj.cliente.consulta.expedientes.ws.ResponseListarExpedientesSupremaType;
import pe.gob.pj.cliente.consulta.expedientes.ws.ResponseListarExpedientesType;
import pe.gob.pj.cliente.consulta.expedientes.ws.ResponseObtenerConfiguracionInstanciaXDistritoType;
import pe.gob.pj.cliente.consulta.expedientes.ws.WSConsultaExpediente;
import pe.gob.pj.cliente.consulta.expedientes.ws.WSConsultaExpediente_Service;
import pe.gob.pj.cliente.consulta.expedientes.ws.handler.HeaderHandlerResolver;
import pe.gob.pj.depositos.domain.exceptions.ErrorException;
import pe.gob.pj.depositos.domain.utils.ProjectConstants;
import pe.gob.pj.depositos.domain.utils.ProjectProperties;
import pe.gob.pj.depositos.domain.utils.ProjectUtils;
import pe.gob.pj.depositos.infraestructure.client.dtos.ExpedienteDto;
import pe.gob.pj.depositos.infraestructure.client.services.ExpedienteWsService;

@Log4j2
@Service("expedienteWsService")
public class ExpedienteWsServiceImpl implements ExpedienteWsService{
	
		
		
	public static WSConsultaExpediente abrirConexion() throws Exception {
		WSConsultaExpediente servicePort = null;
		try {
			HeaderHandlerResolver headerHandlerResolver = null;
			URL url;
			url = new URL(ProjectProperties.getInstance().getConsultaExpedienteEndpoint());
			QName qname = new QName("http://pj.gob.pe/WSConsultaExpediente/", "WSConsultaExpediente");
			javax.xml.ws.Service servicio = new WSConsultaExpediente_Service(url, qname);

			String usuario = ProjectProperties.getInstance().getUsuarioWSExpediente();
			String clave = ProjectProperties.getInstance().getClaveWSExpediente();

			String entidad = ProjectProperties.getInstance().getEntidadWSExpediente();
			String tipo_documento = ProjectProperties.getInstance().getTipoDocumentoWSExpediente();
			headerHandlerResolver = getHeader(entidad, tipo_documento, "", "", usuario, clave);
			
			if (headerHandlerResolver != null) {
				servicio.setHandlerResolver(headerHandlerResolver);
				servicePort = servicio.getPort(WSConsultaExpediente.class);
				((BindingProvider) servicePort).getRequestContext().put("javax.xml.ws.client.connectionTimeout", "30000");
				((BindingProvider) servicePort).getRequestContext().put("javax.xml.ws.client.receiveTimeout", "30000");
			}
		} catch (Exception e) {
			log.error(" Error Conexion" ,e.getMessage(),e);
			throw e;
		}
		return servicePort;
	}
	
	public static HeaderHandlerResolver getHeader(String idEntidad, String idTipoDocumento, String ipAcceso, String ipPublica, String username, String password) {
		HeaderHandlerResolver headerHandlerResolver = new HeaderHandlerResolver(idEntidad, idTipoDocumento, ipAcceso, ipPublica, username, password);
		return headerHandlerResolver;
	}

	public static AuditoriaType getAuditoria() {

		AuditoriaType auditoria = new AuditoriaType();
		auditoria.setIpPc(ProjectUtils.getIp());
		auditoria.setMacAddressPc(ProjectUtils.getMac());
		auditoria.setPcName(ProjectUtils.getPc());
		return auditoria;

	}

	@Override
	public ResponseListarExpedientesType listarExpedientes(String nroExpediente) throws Exception {
		ResponseListarExpedientesType response= new ResponseListarExpedientesType();
		try {
			
			WSConsultaExpediente consultaExpServicio = abrirConexion();
	
			RequestListarExpedientesType parameters = new RequestListarExpedientesType();
			parameters.setCodigoSistema(ProjectProperties.getInstance().getCodigoSistemaWSExpediente());
			parameters.setTipoConsulta(ProjectProperties.getInstance().getTipoConsultaWSExpediente());
			parameters.setCodigoExpediente(nroExpediente);
		
			response = consultaExpServicio.listarExpedientes(getAuditoria(), parameters);
			

		}catch(Exception ex){
			/*
			 * response =null; ex.printStackTrace(); log.error(ex.getMessage(),ex);
			 */
			throw new ErrorException(ProjectConstants.C_E013, ProjectConstants.X_ERROR + ProjectConstants.Proceso.CONSULTA_CEJ_UNICO + 
					ProjectConstants.X_E013 + ProjectProperties.getInstance().getConsultaExpedienteEndpoint(),ex.getMessage(),ex);
		}
		return response;
	}

	@Override
	public ResponseDetalleExpedienteType detalleExpediente(ExpedienteDto expedienteDto) throws Exception {
		ResponseDetalleExpedienteType response= new ResponseDetalleExpedienteType();
		try {
			WSConsultaExpediente consultaExpServicio = abrirConexion();
			RequestDetalleExpedienteType detalle = new RequestDetalleExpedienteType();
			detalle.setCodigoSistema(ProjectProperties.getInstance().getCodigoSistemaWSExpediente());
			detalle.setCodigoConexion(expedienteDto.getCodConexion());
			detalle.setNumeroUnico(expedienteDto.getNumeroUnico()+"");
			detalle.setNumeroIncidente(expedienteDto.getNumeroIncidente()+"");
			detalle.setDni(ProjectConstants.ESTADO_ACTIVO);
	
			response = consultaExpServicio.detalleExpediente(getAuditoria(), detalle);
		}catch(Exception ex){
			throw new ErrorException(ProjectConstants.C_E013, ProjectConstants.X_ERROR + ProjectConstants.Proceso.CONSULTA_CEJ_UNICO + 
					ProjectConstants.X_E013 + ProjectProperties.getInstance().getConsultaExpedienteEndpoint(),ex.getMessage(),ex);
		}
		return response;
	}

	@Override
	public ResponseObtenerConfiguracionInstanciaXDistritoType configuracionInstanciaXDistrito() throws Exception {
		ResponseObtenerConfiguracionInstanciaXDistritoType response= new ResponseObtenerConfiguracionInstanciaXDistritoType();
		
		try {

			WSConsultaExpediente consultaExpServicio = abrirConexion();
			response = consultaExpServicio.obtenerConfiguracionInstanciaXDistrito(getAuditoria());
		
		}catch(Exception ex){
			throw new ErrorException(ProjectConstants.C_E013, ProjectConstants.X_ERROR + ProjectConstants.Proceso.CONSULTA_CEJ_UNICO + 
					ProjectConstants.X_E013 + ProjectProperties.getInstance().getConsultaExpedienteEndpoint(),ex.getMessage(),ex);
		}
		
		return response;
	}

	@Override
	public ResponseListarExpedientesSupremaType listarExpedientesSuprema(String distrito, String provincia,
			String organoJurisdiccional, String anio, String nro) throws Exception {
			ResponseListarExpedientesSupremaType response= new ResponseListarExpedientesSupremaType();
		
		try {
			
			
			WSConsultaExpediente consultaExpServicio = abrirConexion();

			RequestListarExpedientesSupremaType lista = new RequestListarExpedientesSupremaType();
			lista.setCodigoSistema(ProjectProperties.getInstance().getCodigoSistemaWSExpediente());
			lista.setCodigoDistritoJudicial(distrito);
			lista.setCodigoProvincia(provincia);
			lista.setCodigoOrganoJurisdiccional(organoJurisdiccional);
			lista.setNumeroExpediente(nro);
			lista.setAnioExpediente(anio);
			
			response = consultaExpServicio.listarExpedientesSuprema(getAuditoria(), lista);	
			
		}catch(Exception ex){
			throw new ErrorException(ProjectConstants.C_E013, ProjectConstants.X_ERROR + ProjectConstants.Proceso.CONSULTA_CEJ_UNICO + 
					ProjectConstants.X_E013 + ProjectProperties.getInstance().getConsultaExpedienteEndpoint(),ex.getMessage(),ex);
		}
		
		
		return response;
	}

	@Override
	public ResponseDetalleExpedienteSupremaType detalleExpedienteSuprema(ExpedienteDto expedienteDto)
			throws Exception {
		ResponseDetalleExpedienteSupremaType response= new ResponseDetalleExpedienteSupremaType();
		try {
			WSConsultaExpediente consultaExpServicio = abrirConexion();
			RequestDetalleExpedienteSupremaType detalle = new RequestDetalleExpedienteSupremaType();
			detalle.setCodigoSistema(ProjectProperties.getInstance().getCodigoSistemaWSExpediente());
			detalle.setCodigoConexion(expedienteDto.getCodConexion());
			detalle.setNumeroUnico(expedienteDto.getNumeroUnico()+"");
			detalle.setNumeroIncidente(expedienteDto.getNumeroIncidente()+"");
			detalle.setDni(ProjectConstants.ESTADO_ACTIVO);
	
			response = consultaExpServicio.detalleExpedienteSuprema(getAuditoria(), detalle);
		}catch(Exception ex){
			throw new ErrorException(ProjectConstants.C_E013, ProjectConstants.X_ERROR + ProjectConstants.Proceso.CONSULTA_CEJ_UNICO + 
					ProjectConstants.X_E013 + ProjectProperties.getInstance().getConsultaExpedienteEndpoint(),ex.getMessage(),ex);
		}
		return response;
	}

	

	
	
}
