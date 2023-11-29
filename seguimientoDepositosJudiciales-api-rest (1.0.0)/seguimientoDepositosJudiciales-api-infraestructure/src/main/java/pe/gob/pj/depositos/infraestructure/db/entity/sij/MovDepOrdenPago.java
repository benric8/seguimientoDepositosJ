package pe.gob.pj.depositos.infraestructure.db.entity.sij;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pe.gob.pj.depositos.domain.utils.ProjectConstants;
import pe.gob.pj.depositos.infraestructure.db.entity.Auditoria;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Data
@Entity
@Table(name = "\"MOV_DEP_ORDEN_PAGO\"", schema = ProjectConstants.Esquema.SIJ_002)
public class MovDepOrdenPago extends Auditoria implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "\"N_MOV_DEP_ORD_PAGO\"")
	private Integer nMovDepOrdPago;
	
    @Column(name = "\"ID_DJE\"")
    private Long idDje;

    @Column(name = "\"X_OBSERVACION\"")
    private String xObservacion;

    @Column(name = "\"X_NOMBRES\"")
    private String xNombres;

    @Column(name = "\"X_JURIDICA_RUC\"")
    private String xJuridicaRuc;

    @Column(name = "\"X_JURIDICA_RAZON\"")
    private String xJuridicaRazon;

    @Column(name = "\"X_APE_PATERNO\"")
    private String xApePaterno;

    @Column(name = "\"X_APE_MATERNO\"")
    private String xApeMaterno;

    @Column(name = "\"N_UNICO\"")
    private Long nUnico;

    @Column(name = "\"N_PARTE_SECUENCIA\"")
    private Integer nParteSecuencia;

    @Column(name = "\"N_MOV_DEP_DEP_JUD\"")
    private Integer nMovDepDepJud;

    @Column(name = "\"N_MONTO\"")
    private Double nMonto;

    @Column(name = "\"N_INCIDENTE\"")
    private Integer nIncidente;

    @Column(name = "\"L_PAGO_PERITO\"")
    private Boolean lPagoPerito;

    @Column(name = "\"F_EXTORNO_BN\"")
    private Date fExtornoBn;

    @Column(name = "\"F_COBRO_BN\"")
    private Date fCobroBn;

    @Column(name = "\"F_AUTORIZA_SEC\"")
    private Date fAutorizaSec;

    @Column(name = "\"F_AUTORIZA_PRI\"")
    private Date fAutorizaPri;

    @Column(name = "\"F_ANULA\"")
    private Date fAnula;

    @Column(name = "\"C_USU_AUTORIZA_SEC\"")
    private String cUsuAutorizaSec;

    @Column(name = "\"C_USU_AUTORIZA_PRI\"")
    private String cUsuAutorizaPri;

    @Column(name = "\"C_USU_ANULA\"")
    private String cUsuAnula;

    @Column(name = "\"C_TRANSACCION_BN\"")
    private String cTransaccionBn;

    @Column(name = "\"C_TRANS_EXTORNO_BN\"")
    private String cTransExtornoBn;

    @Column(name = "\"C_TIPO_PERSONA\"")
    private String cTipoPersona;

    @Column(name = "\"C_TIPO_DOCUMENTO\"")
    private String cTipoDocumento;

    @Column(name = "\"C_ORDEN_PAGO\"")
    private String cOrdenPago;

    @Column(name = "\"C_NUM_DOCUMENTO\"")
    private String cNumDocumento;

    @Column(name = "\"C_NUM_CUENTA\"")
    private String cNumCuenta;

    @Column(name = "\"C_MONEDA\"")
    private String cMoneda;

    @Column(name = "\"C_ESTADO\"")
    private String cEstado;

    @Column(name = "\"C_DEPOSITOJ\"")
    private String cDepositoJ;

    @Column(name = "\"C_COBRO_AUTOMATICO\"")
    private String cCobroAutomatico;

    @Column(name = "\"C_AGENCIA_BN\"")
    private String cAgenciaBn;

    @Column(name = "\"C_AGEN_EXTORNO_BN\"")
    private String cAgenciaExtornoBn;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "\"N_MOV_DEP_DEP_JUD\"",insertable = false, updatable = false)
    private MovDepositoJudicial movDepositoJudicial;
    
    @OneToMany(mappedBy = "movDepOrdenPago")
    private List<MovDepReasignaDeposito> reasignacionesDeposito;

}

