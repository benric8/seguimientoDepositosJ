package pe.gob.pj.depositos.infraestructure.db.repository.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;

import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.depositos.domain.exceptions.ErrorDaoException;
import pe.gob.pj.depositos.domain.model.sij.DepositoEstado;
import pe.gob.pj.depositos.domain.model.sij.DepositoJudicialDetalle;
import pe.gob.pj.depositos.domain.port.repository.ConsultaRepositoryPort;
import pe.gob.pj.depositos.domain.utils.ProjectConstants;
import pe.gob.pj.depositos.domain.utils.ProjectUtils;
import pe.gob.pj.depositos.infraestructure.db.entity.sij.MovDepOrdenPago;
import pe.gob.pj.depositos.infraestructure.db.entity.sij.MovDepositoJudicial;

@Slf4j
@Component("consultaRepositoryPort")
public class ConsultaRepositoryAdapter implements ConsultaRepositoryPort, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("sessionSij002")
	private SessionFactory sfSij;

	@Override
	public List<DepositoJudicialDetalle> consultarDepositos(String cuo, String numeroExpediente) throws Exception {
		List<DepositoJudicialDetalle> depositoJudicialDetalles = new ArrayList<DepositoJudicialDetalle>();
		
		log.info("{} INICIO CONSULTA DEPOSITOS", cuo);
		try {
			StringBuilder stringQuery = new StringBuilder("SELECT DISTINCT mdj FROM MovDepositoJudicial mdj");
			stringQuery.append(" LEFT JOIN mdj.ordenesPago op");
			stringQuery.append(" where mdj.xNumExp=: "+MovDepositoJudicial.DJ_EXPEDIENTE);
			stringQuery.append(" ORDER BY mdj.fRegistro DESC");
			
			TypedQuery<MovDepositoJudicial> query = this.sfSij.getCurrentSession().createQuery(stringQuery.toString(),MovDepositoJudicial.class);
			query.setParameter(MovDepositoJudicial.DJ_EXPEDIENTE, numeroExpediente);
			
			query.getResultStream().forEach(depositoJudicial->{
				
				if(depositoJudicial!=null && depositoJudicial.getXNumExp() != null ) {
					DepositoJudicialDetalle depositoJudicialDetalle = new DepositoJudicialDetalle();
					
					depositoJudicialDetalle.setCodigoDeposito(depositoJudicial.getCDepositoJ());
					log.info( "{} Codigo de Deposito {}", cuo,depositoJudicialDetalle.getCodigoDeposito());
					depositoJudicialDetalle.setEstado(ProjectUtils.obtenerEstadoActual(depositoJudicial.getCEstado(),depositoJudicial.getNSaldo()));
					log.info( "{} Estado de Deposito {}", cuo,depositoJudicialDetalle.getEstado());
					depositoJudicialDetalle.setFechaRegistro(ProjectUtils.convertDateToString(depositoJudicial.getFRegistro(), ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM));
					log.info( "{} Fecha de Deposito {}", cuo,depositoJudicialDetalle.getFechaRegistro());
					log.info("{} reconociendo si tine ordenes de pago", !depositoJudicial.getOrdenesPago().isEmpty());
					List<DepositoEstado> depositosEstado = new ArrayList<DepositoEstado>();
					if(!depositoJudicial.getOrdenesPago().isEmpty()) {
						depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_D,ProjectConstants.DESCRIPCION_ESTADO_DJ_D,depositoJudicialDetalle.getFechaRegistro(),"A","1"));
						depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_P,ProjectConstants.DESCRIPCION_ESTADO_DJ_P,ProjectUtils.convertDateToString(depositoJudicial.getFPresentacion(), ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM ),"A","1"));
						
						if(depositoJudicial.getOrdenesPago().size()==1) {
							log.info("VALIDAMOS UNA SOLA ORDEN DE PAGO ");
							if(depositoJudicial.getNSaldo()==0) {
								if(depositoJudicial.getOrdenesPago().get(0).getCEstado().equals(ProjectConstants.ESTADO_OP_C)) {
									depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C,ProjectConstants.DESCRIPCION_ESTADO_DJ_C,ProjectUtils.convertDateToString(depositoJudicial.getOrdenesPago().get(0).getFCobroBn(),ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM),"A","1"));								
								}else if (depositoJudicial.getOrdenesPago().get(0).getCEstado().equals(ProjectConstants.ESTADO_OP_F)) {
									depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C,ProjectConstants.DESCRIPCION_ESTADO_DJ_C,null,"A","0"));
								}
							}else {
								if(depositoJudicial.getOrdenesPago().get(0).getCEstado().equals(ProjectConstants.ESTADO_OP_C)) {
									depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_Q,ProjectConstants.DESCRIPCION_ESTADO_DJ_Q,ProjectUtils.convertDateToString(depositoJudicial.getOrdenesPago().get(0).getFCobroBn(),ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM),"B","1"));
									depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C,ProjectConstants.DESCRIPCION_ESTADO_DJ_C,null,"A","0"));
								}else {
									depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C,ProjectConstants.DESCRIPCION_ESTADO_DJ_C,null,"A","0"));
								}
							}
						}else {
							log.info("VALIDAMOS LAS ORDENES DE PAGO {}",depositoJudicial.getOrdenesPago().size() );
							log.info("FILTRAMOS LAS ORDENES DE PAGO LOS ESTADOS SOLO COBRADOS ");
							List<MovDepOrdenPago> ordenesPagoCobradas = depositoJudicial.getOrdenesPago().stream().filter(ordenPago->ordenPago.getCEstado().equals(ProjectConstants.ESTADO_OP_C)).collect(Collectors.toList());;
							log.info("ORDENAMOS LAS ORDENES DE PAGO");
							Collections.sort(ordenesPagoCobradas,Comparator.comparing(MovDepOrdenPago::getFCobroBn));
							log.info("AGREGAMOS LAS ORDENES COMO ESTADOS DE DEPOSITO");
							ordenesPagoCobradas.stream().forEach(ordenPago->depositosEstado.add(new
							 DepositoEstado(ProjectConstants.ESTADO_DJ_Q,ProjectConstants.
							 DESCRIPCION_ESTADO_DJ_Q,ProjectUtils.convertDateToString(ordenPago.
							 getFCobroBn(),ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM),"B","1")));
	
							if(depositoJudicial.getCEstado().equals(ProjectConstants.ESTADO_DJ_C)) {
								depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C,ProjectConstants.DESCRIPCION_ESTADO_DJ_C,depositosEstado.get(depositosEstado.size()-1).getFechaOperacion(),"A","1"));
							}else{
								depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C,ProjectConstants.DESCRIPCION_ESTADO_DJ_C,null,"A","0"));
							}
						}
						
						depositoJudicialDetalle.setDepositoEstados(depositosEstado);
					}else {
						log.info("{} obteniendo estado cuando no exiten ordenes de pago {}" ,cuo,depositoJudicial.getOrdenesPago().isEmpty() );
						if(depositoJudicial.getCEstado().equals(ProjectConstants.ESTADO_DJ_D)) {
							log.info("{} Creando estados de deposito para un deposito con estado D {}",cuo ,depositoJudicial.getCEstado() );							
							depositoJudicialDetalle.setDepositoEstados(ProjectUtils.crearEstadosD(depositoJudicialDetalle.getFechaRegistro()));							
						}else if(depositoJudicial.getCEstado().equals(ProjectConstants.ESTADO_DJ_E)){
							log.info("{} Creando estados de deposito para un deposito con estado E {}" ,cuo,depositoJudicial.getCEstado() );							
							depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_D,ProjectConstants.DESCRIPCION_ESTADO_DJ_D,depositoJudicialDetalle.getFechaRegistro(),"A","1"));
							depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_P,ProjectConstants.DESCRIPCION_ESTADO_DJ_P,depositoJudicial.getFPresentacion()!=null?ProjectUtils.convertDateToString(depositoJudicial.getFPresentacion(), ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM ):null,"A",depositoJudicial.getFPresentacion()!=null?"1":"0"));
							depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_E,ProjectConstants.DESCRIPCION_ESTADO_DJ_E,ProjectUtils.convertDateToString(depositoJudicial.getFExtornoBn(), ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM ),"B","1"));
							depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C,ProjectConstants.DESCRIPCION_ESTADO_DJ_C,null,"A","0"));
							depositoJudicialDetalle.setDepositoEstados(depositosEstado);													
						}else {
							log.info(" {} Creando estados de deposito para un deposito con estado P {}" ,cuo,depositoJudicial.getCEstado() );							
							depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_D,ProjectConstants.DESCRIPCION_ESTADO_DJ_D,depositoJudicialDetalle.getFechaRegistro(),"A","1"));
							depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_P,ProjectConstants.DESCRIPCION_ESTADO_DJ_P,ProjectUtils.convertDateToString(depositoJudicial.getFPresentacion(), ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM ),"A","1"));
							depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C,ProjectConstants.DESCRIPCION_ESTADO_DJ_C,null,"A","0"));
							depositoJudicialDetalle.setDepositoEstados(depositosEstado);
							
						}
					}
					
					depositoJudicialDetalles.add(depositoJudicialDetalle);
					log.info("{} Datos de deposito cargados.", cuo);
				}
			});
		} catch (SQLGrammarException | IllegalArgumentException | ConstraintViolationException | DataIntegrityViolationException e) {
			throw new ErrorDaoException(ProjectConstants.C_ERROR_EJECUCION_SENTENCIA, 
					ProjectConstants.X_ERROR+ProjectConstants.Proceso.CONSULTA_DEPOSITO_JUDICIAL+ProjectConstants.X_ERROR_EJECUCION_SENTENCIA, 
					getJNDI(), 
					e.getMessage(), 
					e.getCause());
		}
		
		log.info("{} FIN_DAO CONSULTA DEPOSITOS", cuo);
		return depositoJudicialDetalles;
	}
	
	public String getJNDI() throws Exception{
		return (SessionFactoryUtils.getDataSource(sfSij)).getConnection().getMetaData().getURL();
	}

}
