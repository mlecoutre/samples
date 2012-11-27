package org.mat.sample.event.ant;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the TMONEVT_HEADER database table.
 * 
 */
@Entity
@Table(name="TMONEVT_HEADER")
public class TmonevtHeader implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="[UID]")
	private String uid;

	@Column(name="APP_NAME")
	private String appName;

	@Column(name="HOST_NAME")
	private String hostName;

	@Column(name="TECH_CREATE")
	private Timestamp techCreate;

	@Column(name="USER_ID")
	private String userId;

	//bi-directional many-to-one association to TmonevtCdevt
    @ManyToOne
	@JoinColumn(name="CDEVT")
	private TmonevtCdevt tmonevtCdevt;

    public TmonevtHeader() {
    }

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Timestamp getTechCreate() {
		return this.techCreate;
	}

	public void setTechCreate(Timestamp techCreate) {
		this.techCreate = techCreate;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public TmonevtCdevt getTmonevtCdevt() {
		return this.tmonevtCdevt;
	}

	public void setTmonevtCdevt(TmonevtCdevt tmonevtCdevt) {
		this.tmonevtCdevt = tmonevtCdevt;
	}
	
}