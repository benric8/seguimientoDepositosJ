package pe.gob.pj.depositos.domain.port.usecase;

import java.util.List;

import pe.gob.pj.depositos.domain.model.sij.DepositoJudicial;
import pe.gob.pj.depositos.domain.model.sij.DepositoJudicialDetalle;

public interface ConsultaUseCasePort {
	public List<DepositoJudicialDetalle> consultarDepositos(String cuo, String numeroExpediente ) throws Exception;
	public List<DepositoJudicial> consultarDeposito(String cuo, String numeroExpediente ) throws Exception;
}
