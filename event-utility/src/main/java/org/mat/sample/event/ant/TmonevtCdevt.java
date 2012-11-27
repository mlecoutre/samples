package org.mat.sample.event.ant;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the TMONEVT_CDEVT database table.
 * 
 */
@Entity
@Table(name="TMONEVT_CDEVT")
public class TmonevtCdevt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CDEVT")
	private int cdevt;

	@Column(name="PARSER_CLASS")
	private String parserClass;

	@Column(name="TABLE_NAME")
	private String tableName;

	@Column(name="TYPE_EVT")
	private String typeEvt;

	//bi-directional many-to-one association to TmonevtMapping
	@OneToMany(mappedBy="tmonevtCdevt")
	private Set<TmonevtMapping> tmonevtMappings;

    public TmonevtCdevt() {
    }

	public int getCdevt() {
		return this.cdevt;
	}

	public void setCdevt(int cdevt) {
		this.cdevt = cdevt;
	}

	public String getParserClass() {
		return this.parserClass;
	}

	public void setParserClass(String parserClass) {
		this.parserClass = parserClass;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTypeEvt() {
		return this.typeEvt;
	}

	public void setTypeEvt(String typeEvt) {
		this.typeEvt = typeEvt;
	}

	public Set<TmonevtMapping> getTmonevtMappings() {
		return this.tmonevtMappings;
	}

	public void setTmonevtMappings(Set<TmonevtMapping> tmonevtMappings) {
		this.tmonevtMappings = tmonevtMappings;
	}

    @Override
    public String toString() {
        return "TmonevtCdevt{" +
                "cdevt=" + cdevt +
                ", parserClass='" + parserClass + '\'' +
                ", tableName='" + tableName + '\'' +
                ", typeEvt='" + typeEvt + '\'' +
                '}';
    }
}