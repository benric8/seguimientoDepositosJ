package pe.gob.pj.depositos.infraestructure.db.repository.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
//import pe.gob.pj.depositos.domain.model.sij.DepositoEstado;
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
		log.info("{} NUMERO EXPEDIENTE", numeroExpediente);
		try {
			StringBuilder stringQuery = new StringBuilder("SELECT mdj FROM MovDepositoJudicial mdj ");
			stringQuery.append("JOIN mdj.ordenesPago op ");
			stringQuery.append("where mdj.xNumExp=: "+MovDepositoJudicial.DJ_EXPEDIENTE);
			stringQuery.append("AND op.cEstado != 'X'");
			stringQuery.append("ORDER BY mdj.fRegistro DESC");
			
			DepositoJudicialDetalle depositoJudicialDetalle = new DepositoJudicialDetalle();
			List<DepositoEstado> depositosEstado = new ArrayList<DepositoEstado>();
			TypedQuery<MovDepositoJudicial> query = this.sfSij.getCurrentSession().createQuery(stringQuery.toString(),MovDepositoJudicial.class);
			query.setParameter(MovDepositoJudicial.DJ_EXPEDIENTE, numeroExpediente);
			
			query.getResultStream().forEach(depositoJudicial->{
				
				if(depositoJudicial!=null && depositoJudicial.getXNumExp() != null ) {
					depositoJudicialDetalle.setCodigoDeposito(depositoJudicial.getCDepositoJ());
					depositoJudicialDetalle.setEstado(depositoJudicial.getCEstado()!=ProjectConstants.ESTADO_DJ_C?ProjectConstants.ESTADO_DJ_PENDIENTE:ProjectConstants.ESTADO_DJ_COBRADO);
					depositoJudicialDetalle.setFechaRegistro(ProjectUtils.convertDateToString(depositoJudicial.getFRegistro(), ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM_SS_SSS));
					if(!depositoJudicial.getOrdenesPago().isEmpty()) {
						depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_D,ProjectConstants.DESCRIPCION_ESTADO_DJ_D,depositoJudicialDetalle.getFechaRegistro(),"A","1"));
						depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_P,ProjectConstants.DESCRIPCION_ESTADO_DJ_P,ProjectUtils.convertDateToString(depositoJudicial.getFPresentacion(), ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM_SS_SSS ),"A","1"));
						if(depositoJudicial.getOrdenesPago().size()==1 && depositoJudicial.getOrdenesPago().get(0).getCEstado()=="C") {
							depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C,ProjectConstants.DESCRIPCION_ESTADO_DJ_C,ProjectUtils.convertDateToString(depositoJudicial.getOrdenesPago().get(0).getFCobroBn(),ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM_SS_SSS),"A","1"));
						}else {
							depositoJudicial.getOrdenesPago().stream().forEach(ordenPago->{
								depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_Q,ProjectConstants.DESCRIPCION_ESTADO_DJ_Q,ProjectUtils.convertDateToString(ordenPago.getFCobroBn(),ProjectConstants.FORMATO_FECHA_DD_MM_YYYY_HH_MM_SS_SSS),"B","1"));
							});
							Collections.sort(depositosEstado,Comparator.comparing(DepositoEstado::getFechaOperacion));
							if(depositoJudicial.getCEstado()==ProjectConstants.ESTADO_DJ_C) {
								depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C,ProjectConstants.DESCRIPCION_ESTADO_DJ_C,depositosEstado.get(depositosEstado.size()-1).getFechaOperacion(),"A","1"));
							}else{
								depositosEstado.add(new DepositoEstado(ProjectConstants.ESTADO_DJ_C,ProjectConstants.DESCRIPCION_ESTADO_DJ_C,null,"A","0"));
							}
						}
						
						depositoJudicialDetalle.setDeppositoEstados(depositosEstado);
					}else {
						depositoJudicialDetalle.setDeppositoEstados(ProjectUtils.crearEstadosD(depositoJudicialDetalle.getFechaRegistro()));
					}
				}
			});
			depositoJudicialDetalles.add(depositoJudicialDetalle);
			log.info("{} Datos de deposito cargados.", cuo);
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
