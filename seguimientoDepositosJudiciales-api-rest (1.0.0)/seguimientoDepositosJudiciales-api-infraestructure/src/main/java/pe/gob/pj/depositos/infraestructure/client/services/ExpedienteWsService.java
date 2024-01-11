package pe.gob.pj.depositos.infraestructure.client.services;



import pe.gob.pj.cliente.consulta.expedientes.ws.ResponseDetalleExpedienteSupremaType;
import pe.gob.pj.cliente.consulta.expedientes.ws.ResponseDetalleExpedienteType;
import pe.gob.pj.cliente.consulta.expedientes.ws.ResponseListarExpedientesSupremaType;
import pe.gob.pj.cliente.consulta.expedientes.ws.ResponseListarExpedientesType;
import pe.gob.pj.cliente.consulta.expedientes.ws.ResponseObtenerConfiguracionInstanciaXDistritoType;
import pe.gob.pj.depositos.infraestructure.client.dtos.ExpedienteDto;


public interface ExpedienteWsService {
	public ResponseListarExpedientesType listarExpedientes( String nroExpediente) throws Exception;
	
	public ResponseDetalleExpedienteType detalleExpediente(ExpedienteDto expedienteBean) throws Exception;
	
	public ResponseObtenerConfiguracionInstanciaXDistritoType configuracionInstanciaXDistrito() throws Exception;
	
	public ResponseListarExpedientesSupremaType listarExpedientesSuprema(String distrito, String provincia,String organoJurisdiccional,String anio, String nro) throws Exception;

	public ResponseDetalleExpedienteSupremaType detalleExpedienteSuprema(ExpedienteDto expedienteBean) throws Exception;
	
	
}
