package pe.gob.pj.depositos.infraestructure.db.entity.sij;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "MAE_DISTRITO_JUDICIAL")
public class MaeDistritoJudicial {

    @Id
    @Column(name = "c_distritoj")
    private String cDistritoJ;

    @Column(name = "x_nom_distritoj")
    private String xNomDistritoJ;

    @Column(name = "l_estado")
    private Boolean lEstado;

    @Column(name = "f_aud")
    private Date fAud;

    @Column(name = "b_aud")
    private Boolean bAud;

    @Column(name = "c_aud_uid")
    private String cAudUid;

    @Column(name = "c_aud_uidred")
    private String cAudUidRed;

    @Column(name = "c_aud_pc")
    private String cAudPc;

    @Column(name = "n_aud_ip")
    private String nAudIp;

    @Column(name = "c_aud_mcaddr")
    private String cAudMcAddr;

    // Getters y setters

    // Puedes agregar más campos y métodos según sea necesario
}

