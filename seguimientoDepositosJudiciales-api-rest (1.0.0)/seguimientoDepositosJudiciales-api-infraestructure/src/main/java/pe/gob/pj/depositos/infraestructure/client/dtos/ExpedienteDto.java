package pe.gob.pj.depositos.infraestructure.client.dtos;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ExpedienteDto implements Serializable{
	private static final long serialVersionUID = 1L;

    public String numeroExpediente;
    public String fecha;
    public Date fechaIngreso;
	private String codigoOrganoJurisdiccional;
	private String nombreOrganoJurisdiccional;
    private String codigoInstancia;
    private String nombreInstancia;
    private String codigoProvincia;
    private String nombreProvincia;
    private String codigoMensaje;
    private String nombreJuzgado;

    
    private String codigoVisualizacion;
    private Long numeroIncidente;
    private Long numeroUnico;
    
    private String juez;
    private String especialista;
    private String proceso;
    private String materia;
    private String nombreDistrito;
    private String codigoDistrito;
    private String codConexion;
    
    private String direccion;
    private String dniMagistrado;
    private String nombresMagistrado;
    private String apellidoPMagistrado;
    private String apellidoMMagistrado;   
  
    
    private String longitud; 
    private String latitud;

    private String codigoEspecialidad;
    private String nombreEspecialidad;
    private String indicadorCorporativo;
    
    private String estadoDependencia; //estado del órgano jurisdiccional
    private Long codigoDependencia;
    private String nombreDependencia;
    private String nombreDependenciaProvincia;
    private String nombreDependenciaDistrito;   
    
    
    private String codigoSede;
    private String descripcionSede;
    
}
