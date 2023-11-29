package pe.gob.pj.depositos.infraestructure.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
@MappedSuperclass
public class Auditoria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Column(name="f_aud")
	private Date fAud;
	@Column(name="b_aud")
	private String cAud;
	@Column(name="c_aud_uid")
	private String cAudId;
	@Column(name="c_aud_uidred")
	private String cAudIdRed;
	@Column(name="c_aud_pc")
	private String cAudPc;
	@Column(name="n_aud_ip")
	private String cAudIp;
	@Column(name="c_aud_mcaddr")
	private String cAudMcAddr;
	
	

}
