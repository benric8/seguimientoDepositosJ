package pe.gob.pj.depositos.usecases.adapter;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import pe.gob.pj.depositos.domain.model.sij.DepositoJudicial;
import pe.gob.pj.depositos.domain.model.sij.OrdenPago;
import pe.gob.pj.depositos.domain.port.repository.ConsultaRepositoryPort;
import pe.gob.pj.depositos.domain.port.usecase.ConsultaUseCasePort;

@Slf4j
@Service("consultaUseCasePort")
public class ConsultaUseCaseAdapter implements ConsultaUseCasePort, Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("consultaRepositoryPort")
	private ConsultaRepositoryPort repo;

	
	
	
	@Override
	@Transactional(transactionManager = "txManagerSij", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public List<DepositoJudicial> consultarDeposito(String cuo, String numeroExpediente) throws Exception {
		log.info("{} INICIO_SERVICE CONSULTA DEPOSITOS", cuo);
		List<DepositoJudicial> lista = repo.consultarDeposito(cuo, numeroExpediente);
		log.info("{} FIN_SERVICE CONSULTA DEPOSITOS", cuo);
		return lista;
	}
	


	@Override
	@Transactional(transactionManager = "txManagerSij", propagation = Propagation.REQUIRES_NEW, readOnly = true, rollbackFor = { Exception.class, SQLException.class})
	public List<OrdenPago> consultarOrdenesPago(String cuo, String codigoDeposito) throws Exception {
		log.info("{} INICIO_SERVICE CONSULTA ORDENES PAGO", cuo);
		List<OrdenPago> lista = repo.consultarOrdenesPago(cuo, codigoDeposito);
		log.info("{} FIN_SERVICE CONSULTA ORDENES PAGO", cuo);
		return lista;
	}


	




	

}
