package org.mat.sample.event.ant;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TMONEVT_MAPPING database table.
 * 
 */
@Entity
@Table(name="TMONEVT_MAPPING")
public class TmonevtMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TmonevtMappingPK id;

	@Column(name="COLUMN_NAME")
	private String columnName;

	@Column(name="PROPERTY_NAME")
	private String propertyName;

	//bi-directional many-to-one association to TmonevtCdevt
    @ManyToOne()
	@JoinColumn(name="CDEVT", updatable = false, insertable = false)
	private TmonevtCdevt tmonevtCdevt;

    public TmonevtMapping() {
    }

	public TmonevtMappingPK getId() {
		return this.id;
	}

	public void setId(TmonevtMappingPK id) {
		this.id = id;
	}
	
	public String getColumnName() {
		return this.columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public TmonevtCdevt getTmonevtCdevt() {
		return this.tmonevtCdevt;
	}

	public void setTmonevtCdevt(TmonevtCdevt tmonevtCdevt) {
		this.tmonevtCdevt = tmonevtCdevt;
	}

    @Override
    public String toString() {
        return "TmonevtMapping{" +
                "id=" + id +
                ", columnName='" + columnName + '\'' +
                ", propertyName='" + propertyName + '\'' +
                ", tmonevtCdevt=" + tmonevtCdevt +
                '}';
    }
}