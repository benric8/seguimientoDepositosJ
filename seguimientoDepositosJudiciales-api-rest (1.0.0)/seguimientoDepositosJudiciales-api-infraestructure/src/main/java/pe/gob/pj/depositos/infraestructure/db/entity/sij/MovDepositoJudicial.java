package pe.gob.pj.depositos.infraestructure.db.entity.sij;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.pj.depositos.domain.utils.ProjectConstants;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "MOV_DEP_DEPOSITO_JUDICIAL", schema = ProjectConstants.Esquema.SIJ_002)
public class MovDepositoJudicial {

    @Id
    @Column(name = "ID_DJE")
    private Long idDje;

    @Column(name = "X_NUM_EXP")
    private String xNumExp;

    @Column(name = "X_NOMBRES")
    private String xNombres;

    @Column(name = "X_APE_PATERNO")
    private String xApePaterno;

    @Column(name = "X_APE_MATERNO")
    private String xApeMaterno;

    @Column(name = "REC_COD_LOTE")
    private String recCodLote;

    @Column(name = "N_UNICO_OLD")
    private Long nUnicoOld;

    @Column(name = "N_UNICO")
    private Long nUnico;

    @Column(name = "N_SALDO")
    private Double nSaldo;

    @Column(name = "N_MOV_DEP_DEP_JUD")
    private Integer nMovDepDepJud;

    @Column(name = "N_MONTO")
    private Double nMonto;

    @Column(name = "N_INCIDENTE_OLD")
    private Integer nIncidenteOld;

    @Column(name = "N_INCIDENTE")
    private Integer nIncidente;

    @Column(name = "n_aud_ip")
    private String nAudIp;

    @Column(name = "F_REGISTRO")
    private Date fRegistro;

    @Column(name = "F_REASIGNACION")
    private Date fReasignacion;

    @Column(name = "F_PRESENTACION")
    private Date fPresentacion;

    @Column(name = "F_EXTORNO_BN")
    private Date fExtornoBn;

    @Column(name = "F_EMISION")
    private Date fEmision;

    @Column(name = "f_aud")
    private Date fAud;

    @Column(name = "F_ACTUALIZACION")
    private Date fActualizacion;

    @Column(name = "F_ACEPTA_PAGO")
    private Date fAceptaPago;

    @Column(name = "COD_APP")
    private String codApp;

    @Column(name = "C_TRANSACCION_BN")
    private String cTransaccionBn;

    @Column(name = "C_TIPO_PERSONA")
    private String cTipoPersona;

    @Column(name = "C_TIPO_PAGO")
    private String cTipoPago;

    @Column(name = "C_TIPO_DOCUMENTO")
    private String cTipoDocumento;

    @Column(name = "C_TIPO_DEPOSITO")
    private String cTipoDeposito;

    @Column(name = "C_SERVICIO")
    private String cServicio;

    @Column(name = "C_REASIGNADO")
    private String cReasignado;

    @Column(name = "C_PROVINCIA")
    private String cProvincia;

    @Column(name = "C_NUM_DOCUMENTO")
    private String cNumDocumento;

    @Column(name = "C_MOTIVO")
    private String cMotivo;

    @Column(name = "C_MONEDA")
    private String cMoneda;

    @Column(name = "C_INSTANCIA")
    private String cInstancia;

    @Column(name = "C_ESTADO")
    private String cEstado;

    @Column(name = "C_DISTRITO")
    private String cDistrito;

    @Column(name = "C_DEPOSITOJ")
    private String cDepositoJ;

    
}
