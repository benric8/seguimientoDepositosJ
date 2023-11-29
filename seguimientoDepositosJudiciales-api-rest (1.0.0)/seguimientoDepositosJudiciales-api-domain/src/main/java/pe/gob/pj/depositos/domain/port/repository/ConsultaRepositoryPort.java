package pe.gob.pj.depositos.domain.port.repository;

import java.util.List;

import pe.gob.pj.depositos.domain.model.sij.DepositoJudicialDetalle;

public interface ConsultaRepositoryPort {
	public List<DepositoJudicialDetalle> consultarDepositos(String cuo, String numeroExpediente) throws Exception;
}
