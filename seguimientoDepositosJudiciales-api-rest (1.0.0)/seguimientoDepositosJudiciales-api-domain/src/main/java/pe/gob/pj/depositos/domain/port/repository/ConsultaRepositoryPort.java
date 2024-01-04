package pe.gob.pj.depositos.domain.port.repository;

import java.util.List;

import pe.gob.pj.depositos.domain.model.sij.DepositoJudicial;
import pe.gob.pj.depositos.domain.model.sij.DepositoJudicialDetalle;
import pe.gob.pj.depositos.domain.model.sij.OrdenPago;

public interface ConsultaRepositoryPort {
	public List<DepositoJudicialDetalle> consultarDepositos(String cuo, String numeroExpediente) throws Exception;
	public List<DepositoJudicial> consultarDeposito(String cuo, String numeroExpediente) throws Exception;
	public List<OrdenPago> consultarOrdenesPago(String cuo, String codigoDeposito) throws Exception;
}
