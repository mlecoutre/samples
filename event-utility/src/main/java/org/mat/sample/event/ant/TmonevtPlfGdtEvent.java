package org.mat.sample.event.ant;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the TMONEVT_PLF_GDT_EVENT database table.
 * 
 */
@Entity
@Table(name="TMONEVT_PLF_GDT_EVENT")
public class TmonevtPlfGdtEvent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TECH_ID")
	private int techId;

	@Column(name="APP_NAME")
	private String appName;

	@Column(name="CLE")
	private String cle;

	@Column(name="CURRENTSERVICENAME")
	private String currentservicename;

	@Column(name="CURRENTUTNAME")
	private String currentutname;

	@Column(name="DATETIME")
	private Timestamp datetime;

	@Column(name="FUNCTIONNAME")
	private String functionname;

	@Column(name="HOST_NAME")
	private String hostName;

	@Column(name="IDUM")
	private String idum;

    @Lob @Column(columnDefinition="NTEXT NULL")
	private String message;

	@Column(name="NUMAPG")
	private String numapg;

	@Column(name="NUMBCC")
	private String numbcc;

	@Column(name="NUMDE")
	private int numde;

	@Column(name="NUMGAMME")
	private int numgamme;

	@Column(name="NUMORDREAPL")
	private int numordreapl;

	@Column(name="NUMORDREPAL")
	private int numordrepal;

	@Column(name="NUMPAF")
	private int numpaf;

	@Column(name="NUMTIERS")
	private String numtiers;

	@Column(name="REFCOMDEMA")
	private String refcomdema;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="[UID]", columnDefinition="uniqueidentifier")
	private String uid;

	@Column(name="USRID")
	private String usrid;

	@Column(name="UTNAME")
	private String utname;

	@Column(name="VALEUR")
	private String valeur;

    public TmonevtPlfGdtEvent() {
    }

	public int getTechId() {
		return this.techId;
	}

	public void setTechId(int techId) {
		this.techId = techId;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getCle() {
		return this.cle;
	}

	public void setCle(String cle) {
		this.cle = cle;
	}

	public String getCurrentservicename() {
		return this.currentservicename;
	}

	public void setCurrentservicename(String currentservicename) {
		this.currentservicename = currentservicename;
	}

	public String getCurrentutname() {
		return this.currentutname;
	}

	public void setCurrentutname(String currentutname) {
		this.currentutname = currentutname;
	}

	public Timestamp getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}

	public String getFunctionname() {
		return this.functionname;
	}

	public void setFunctionname(String functionname) {
		this.functionname = functionname;
	}

	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIdum() {
		return this.idum;
	}

	public void setIdum(String idum) {
		this.idum = idum;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNumapg() {
		return this.numapg;
	}

	public void setNumapg(String numapg) {
		this.numapg = numapg;
	}

	public String getNumbcc() {
		return this.numbcc;
	}

	public void setNumbcc(String numbcc) {
		this.numbcc = numbcc;
	}

	public int getNumde() {
		return this.numde;
	}

	public void setNumde(int numde) {
		this.numde = numde;
	}

	public int getNumgamme() {
		return this.numgamme;
	}

	public void setNumgamme(int numgamme) {
		this.numgamme = numgamme;
	}

	public int getNumordreapl() {
		return this.numordreapl;
	}

	public void setNumordreapl(int numordreapl) {
		this.numordreapl = numordreapl;
	}

	public int getNumordrepal() {
		return this.numordrepal;
	}

	public void setNumordrepal(int numordrepal) {
		this.numordrepal = numordrepal;
	}

	public int getNumpaf() {
		return this.numpaf;
	}

	public void setNumpaf(int numpaf) {
		this.numpaf = numpaf;
	}

	public String getNumtiers() {
		return this.numtiers;
	}

	public void setNumtiers(String numtiers) {
		this.numtiers = numtiers;
	}

	public String getRefcomdema() {
		return this.refcomdema;
	}

	public void setRefcomdema(String refcomdema) {
		this.refcomdema = refcomdema;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsrid() {
		return this.usrid;
	}

	public void setUsrid(String usrid) {
		this.usrid = usrid;
	}

	public String getUtname() {
		return this.utname;
	}

	public void setUtname(String utname) {
		this.utname = utname;
	}

	public String getValeur() {
		return this.valeur;
	}

	public void setValeur(String valeur) {
		this.valeur = valeur;
	}

    @Override
    public String toString() {
        return "TmonevtPlfGdtEvent{" +
                "techId=" + techId +
                ", appName='" + appName + '\'' +
                ", cle='" + cle + '\'' +
                ", currentservicename='" + currentservicename + '\'' +
                ", currentutname='" + currentutname + '\'' +
                ", datetime=" + datetime +
                ", functionname='" + functionname + '\'' +
                ", hostName='" + hostName + '\'' +
                ", idum='" + idum + '\'' +
                ", message='" + message + '\'' +
                ", numapg='" + numapg + '\'' +
                ", numbcc='" + numbcc + '\'' +
                ", numde=" + numde +
                ", numgamme=" + numgamme +
                ", numordreapl=" + numordreapl +
                ", numordrepal=" + numordrepal +
                ", numpaf=" + numpaf +
                ", numtiers='" + numtiers + '\'' +
                ", refcomdema='" + refcomdema + '\'' +
                ", uid='" + uid + '\'' +
                ", usrid='" + usrid + '\'' +
                ", utname='" + utname + '\'' +
                ", valeur='" + valeur + '\'' +
                '}';
    }
}