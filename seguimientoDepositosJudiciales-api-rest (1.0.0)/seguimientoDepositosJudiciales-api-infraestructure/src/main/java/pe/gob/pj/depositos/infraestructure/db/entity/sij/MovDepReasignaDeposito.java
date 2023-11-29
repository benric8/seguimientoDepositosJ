package pe.gob.pj.depositos.infraestructure.db.entity.sij;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.pj.depositos.domain.utils.ProjectConstants;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "MOV_DEP_REASIGNA_DEPOSITO", schema = ProjectConstants.Esquema.SIJ_002)
public class MovDepReasignaDeposito implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "N_MOV_DEP_REA_DEP")
    private Integer nMovDepReaDep;

    @Column(name = "N_MOV_DEP_DEP_JUD")
    private Integer nMovDepDepJud;

    @Column(name = "N_MOV_DEP_ORD_PAGO")
    private Integer nMovDepOrdPago;

    @Column(name = "C_DEPOSITOJ")
    private String cDepositoJ;

    @Column(name = "N_CORRELATIVO")
    private Integer nCorrelativo;

    @Column(name = "N_UNICO")
    private Long nUnico;

    @Column(name = "N_INCIDENTE")
    private Integer nIncidente;

    @Column(name = "N_UNICO_OLD")
    private Long nUnicoOld;

    @Column(name = "N_INCIDENTE_OLD")
    private Integer nIncidenteOld;

    @Column(name = "C_USUARIO")
    private String cUsuario;

    @Column(name = "F_REGISTRO")
    private Date fRegistro;

    @Column(name = "C_ID_RED")
    private String cIdRed;

    @Column(name = "C_AUD_PC")
    private String cAudPc;

    @Column(name = "C_AUD_IP")
    private String cAudIp;

    @Column(name = "C_AUD_MCADDR")
    private String cAudMcAddr;

    @Column(name = "ID_DJE")
    private Long idDje;

    @Column(name = "C_NUM_RESOLUCION")
    private String cNumResolucion;

    @Column(name = "X_NOMBRE_ARCHIVO")
    private String xNombreArchivo;

    @Column(name = "X_RUTA_ARCHIVO")
    private String xRutaArchivo;

    @Column(name = "N_SERVICIO_FTP")
    private Integer nServicioFtp;

    @Column(name = "N_CORRELATIVO_FTP")
    private Integer nCorrelativoFtp;

    @Column(name = "F_AUD")
    private Date fAud;

    @Column(name = "B_AUD")
    private Boolean bAud;

    @Column(name = "C_AUD_UID")
    private String cAudUid;
    
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "N_MOV_DEP_DEP_JUD" , insertable = false, updatable = false)
    private MovDepositoJudicial movDepositoJudicial;
    
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "N_MOV_DEP_ORDEN_PAGO" , insertable = false, updatable = false )
    private MovDepOrdenPago movDepOrdenPago;
    

    
}
