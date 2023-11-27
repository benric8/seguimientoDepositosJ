package pe.gob.pj.depositos.infraestructure.db.entity.sij;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "MAE_REC_CONTROL_SECUENCIA")
public class MaeRecControlSecuencia {

    @Id
    @Column(name = "n_secuencia")
    private Long nSecuencia;

    @Column(name = "N_MAE_REC_CTRL_SEC")
    private Long nMaeRecCtrlSec;

    @Column(name = "N_MAE_CTRL_SEC")
    private Long nMaeCtrlSec;

    @Column(name = "n_aud_ip")
    private String nAudIp;

    @Column(name = "l_bloqueo")
    private Boolean lBloqueo;

    @Column(name = "f_aud")
    private Date fAud;

    @Column(name = "c_tipo")
    private String cTipo;

    @Column(name = "c_aud_uidred")
    private String cAudUidRed;

    @Column(name = "c_aud_uid")
    private String cAudUid;

    @Column(name = "c_aud_pc")
    private String cAudPc;

    @Column(name = "c_aud_mcaddr")
    private String cAudMcAddr;

    @Column(name = "c_anho")
    private String cAnho;

    @Column(name = "b_aud")
    private Boolean bAud;

    // Getters y setters

    // Puedes agregar más campos y métodos según sea necesario
}
