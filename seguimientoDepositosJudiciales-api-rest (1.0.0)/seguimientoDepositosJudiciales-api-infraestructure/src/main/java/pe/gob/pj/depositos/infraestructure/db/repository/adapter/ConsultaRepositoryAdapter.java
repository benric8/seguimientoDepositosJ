package pe.gob.pj.depositos.infraestructure.db.repository.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Query;
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
import pe.gob.pj.depositos.domain.model.sij.DepositoJudicial;
import pe.gob.pj.depositos.domain.model.sij.OrdenPago;
import pe.gob.pj.depositos.domain.port.repository.ConsultaRepositoryPort;
import pe.gob.pj.depositos.domain.utils.ProjectConstants;
import pe.gob.pj.depositos.domain.utils.ProjectUtils;
import pe.gob.pj.depositos.infraestructure.db.entity.sij.MaeRecMotivoDeposito;
import pe.gob.pj.depositos.infraestructure.db.entity.sij.MovDepOrdenPago;
import pe.gob.pj.depositos.infraestructure.db.entity.sij.MovDepositoJudicial;

@Slf4j
@Component("consultaRepositoryPort")
public class ConsultaRepositoryAdapter implements ConsultaRepositoryPort, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("sessionSij002")
	private SessionFactory sfSij;

	

	
	@Override
	public List<DepositoJudicial> consultarDeposito(String cuo, String numeroExpediente) throws Exception { 
		List<DepositoJudicial> depositosJudiciales = new ArrayList<DepositoJudicial>();
		
		log.info("{} INICIO CONSULTA DEPOSITOS", cuo); 
		try { 
		StringBuilder stringQuery = new StringBuilder("SELECT DISTINCT mdj FROM MovDepositoJudicial mdj"); 
		stringQuery.append(" LEFT JOIN mdj.ordenesPago op"); 
		stringQuery.append(" where mdj.xNumExp=: " +MovDepositoJudicial.DJ_EXPEDIENTE);
		stringQuery.append(" ORDER BY mdj.fRegistro DESC");
		
		TypedQuery<MovDepositoJudicial> query = this.sfSij.getCurrentSession().createQuery(stringQuery.toString(), MovDepositoJudicial.class);
		query.setParameter(MovDepositoJudicial.DJ_EXPEDIENTE, numeroExpediente);
		
		query.getResultStream().forEach(depJudicial -> {
			if (depJudicial != null && depJudicial.getCDepositoJ() != null) {
				DepositoJudicial depositoJudicial = new DepositoJudicial();
				depositoJudicial.setCodigoDeposito(depJudicial.getCDepositoJ());
				log.info("EXTRAEMOS EL MOTIVO DEL DEPOSITO DE LA TABLA MAE_REC_MOTIVO_DEPOSITO {}",depJudicial.getCMotivo());
				TypedQuery<String> motivo= this.sfSij.getCurrentSession().createNamedQuery(MaeRecMotivoDeposito.FIND_MOTIVO_BY_CODIGO, String.class);
				motivo.setParameter(MaeRecMotivoDeposito.C_MOTIVO,depJudicial.getCMotivo());
				String descripcioinMotivo = motivo.getSingleResult();
				log.info("valor recuperado {}",descripcioinMotivo);
				depositoJudicial.setMotivoDeposito(ProjectUtils.isNullOrEmpty(descripcioinMotivo)?"":descripcioinMotivo);
				log.info("FIN DE LA EXTRACCION DEL MOTIVO DE DEPOSITO");
				depositoJudicial.setEstado(depJudicial.getCEstado());
				depositoJudicial.setDescripcionEstado(ProjectUtils.obtenerEstadoActual(
						depJudicial.getCEstado(), depJudicial.getNSaldo()));
				depositoJudicial.setFechaRegistro(ProjectUtils.convertDateToString(
						depJudicial.getFRegistro(),ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM));
				
				List<DepositoEstado> depositosEstado = new ArrayList<DepositoEstado>();
				if(depJudicial.getCEstado().equals(ProjectConstants.ESTADO_DJ_D)) {
					depositoJudicial.setDepositoEstados(ProjectUtils.crearEstadosD(depositoJudicial.getFechaRegistro()));
				}else{
					depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_D,ProjectConstants.DESCRIPCION_ESTADO_DJ_D,
							depositoJudicial.getFechaRegistro(), "A","1"));
					depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_P,ProjectConstants.DESCRIPCION_ESTADO_DJ_P,
							ProjectUtils.convertDateToString(depJudicial.getFPresentacion(),
									ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM), "A", "1"));
					
					if (!depJudicial.getOrdenesPago().isEmpty()) {
						
						List<MovDepOrdenPago> ordenesPagoCobradas = depJudicial.getOrdenesPago().stream() 
								.filter(ordenPago -> ordenPago.getCEstado().equals(ProjectConstants.ESTADO_OP_C)).collect(Collectors.toList());
						log.info("ORDENAMOS LAS ORDENES DE PAGO");
						Collections.sort(ordenesPagoCobradas,Comparator.comparing(MovDepOrdenPago::getFCobroBn));
						log.info("AGREGAMOS LAS ORDENES COMO ESTADOS DE DEPOSITO");
						
						if(!ordenesPagoCobradas.isEmpty()) {
							
							if (depJudicial.getCEstado().equals(ProjectConstants.ESTADO_DJ_C)|| depJudicial.getNSaldo()==0) {
								depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C, ProjectConstants.DESCRIPCION_ESTADO_DJ_C,
										ProjectUtils.convertDateToString(ordenesPagoCobradas.get(ordenesPagoCobradas.size()== 0 ? 0 :ordenesPagoCobradas.size()-1).getFCobroBn(),
												ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM), "A","1")); 
							}else { 
								depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_Q,ProjectConstants.DESCRIPCION_ESTADO_DJ_Q, 
										ProjectUtils.convertDateToString(ordenesPagoCobradas.get(ordenesPagoCobradas.size()== 0 ? 0 :ordenesPagoCobradas.size()-1).getFCobroBn(),
												ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM), "B", "1"));
								depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C, ProjectConstants.DESCRIPCION_ESTADO_DJ_C, null, "A", "0")); 
							}
							
						}else {
							depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C, ProjectConstants.DESCRIPCION_ESTADO_DJ_C, null, "A", "0")); 
						}
					} else {
						depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C, ProjectConstants.DESCRIPCION_ESTADO_DJ_C, null, "A", "0")); 
					}
					depositoJudicial.setDepositoEstados(depositosEstado);					  
				} 
				depositosJudiciales.add(depositoJudicial); }
		});
		
		} catch (SQLGrammarException | IllegalArgumentException |
				ConstraintViolationException | DataIntegrityViolationException e) { throw new
			ErrorDaoException(ProjectConstants.C_ERROR_EJECUCION_SENTENCIA,
					ProjectConstants.X_ERROR +
					ProjectConstants.Proceso.CONSULTA_DEPOSITO_JUDICIAL +
					ProjectConstants.X_ERROR_EJECUCION_SENTENCIA, getJNDI(), e.getMessage(),
					e.getCause()); }
		
		return depositosJudiciales;
		
	}
	
	@Override
	public List<OrdenPago> consultarOrdenesPago(String cuo, String codigoDeposito) throws Exception {
		List<OrdenPago> ordenesPago = new ArrayList<OrdenPago>();
		
		log.info("{} INICIO CONSULTA ORDENES DE PAGO", cuo); 
		try { 
			StringBuilder stringQuery = new StringBuilder("SELECT DISTINCT mop FROM MovDepOrdenPago mop"); 
			stringQuery.append(" where mop.cDepositoJ =: " +MovDepOrdenPago.OP_CDEPOSITOJ);
			stringQuery.append(" AND mop.cEstado = 'C' ");
			stringQuery.append(" ORDER BY mop.fCobroBn DESC");
		
			TypedQuery<MovDepOrdenPago> query = this.sfSij.getCurrentSession().createQuery(stringQuery.toString(), MovDepOrdenPago.class);
			query.setParameter(MovDepOrdenPago.OP_CDEPOSITOJ, codigoDeposito);
			query.getResultStream().forEach(ordenPago -> {
				if (ordenPago != null && ordenPago.getCDepositoJ() != null) {
					OrdenPago ordPago = new OrdenPago();
					ordPago.setCDepositoJ(ordenPago.getCDepositoJ());
					ordPago.setCEstado(ordenPago.getCEstado());
					ordPago.setCOrdenPago(ordenPago.getCOrdenPago());
					ordPago.setFAutorizaPri(ProjectUtils.convertDateToString(ordenPago.getFAutorizaPri(),ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM));
					ordPago.setFCobroBn(ProjectUtils.convertDateToString(ordenPago.getFCobroBn(),ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM));
					ordenesPago.add(ordPago);
				}
			});
		
		} catch (SQLGrammarException | IllegalArgumentException |
				ConstraintViolationException | DataIntegrityViolationException e) { throw new
			ErrorDaoException(ProjectConstants.C_ERROR_EJECUCION_SENTENCIA,
					ProjectConstants.X_ERROR +
					ProjectConstants.Proceso.CONSULTA_ORDEN_PAGO +
					ProjectConstants.X_ERROR_EJECUCION_SENTENCIA, getJNDI(), e.getMessage(),
					e.getCause()); }
		
		return ordenesPago;
		
	}

	public String getJNDI() throws Exception {
		return (SessionFactoryUtils.getDataSource(sfSij)).getConnection().getMetaData().getURL();
		
	}
}
