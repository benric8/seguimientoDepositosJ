package pe.gob.pj.depositos.infraestructure.db.entity.sij;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import pe.gob.pj.depositos.domain.utils.ProjectConstants;
import pe.gob.pj.depositos.infraestructure.db.entity.Auditoria;

@EqualsAndHashCode(callSuper=false)
@Data
@Entity
@Table(name = "MAE_REC_DEPENDENCIA", schema = ProjectConstants.Esquema.SIJ_002)
public class MaeRecDependencia extends Auditoria implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "c_dependencia")
    private String cDependencia;

    @Column(name = "cod_dependencia_sup")
    private String codDependenciaSup;

    @Column(name = "x_desc_dependencia")
    private String xDescDependencia;

    @Column(name = "l_tipo_depen")
    private Boolean lTipoDepen;

    @Column(name = "c_distrito_judicial")
    private String cDistritoJudicial;

    @Column(name = "c_sede")
    private String cSede;

    @Column(name = "c_org_jurisd")
    private String cOrgJurisd;

    @Column(name = "c_especialidad")
    private String cEspecialidad;

    @Column(name = "c_instancia")
    private String cInstancia;

    @Column(name = "l_activo")
    private Boolean lActivo;

    @Column(name = "c_ubigeo")
    private String cUbigeo;

    @Column(name = "l_envio")
    private Boolean lEnvio;

    @Column(name = "c_provincia")
    private String cProvincia;

    @Column(name = "c_eq_instancia")
    private String cEqInstancia;

   
}

