package pe.gob.pj.depositos.domain.port.usecase;

import java.util.List;

import pe.gob.pj.depositos.domain.model.sij.DepositoJudicial;
import pe.gob.pj.depositos.domain.model.sij.OrdenPago;

public interface ConsultaUseCasePort {
	public List<OrdenPago> consultarOrdenesPago(String cuo, String codigoDeposito ) throws Exception;
	public List<DepositoJudicial> consultarDeposito(String cuo, String numeroExpediente ) throws Exception;
}
