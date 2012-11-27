package org.mat.sample.event.ant;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TMONEVT_MAPPING database table.
 * 
 */
@Embeddable
public class TmonevtMappingPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CDEVT")
	private int cdevt;

	@Column(name="MAP_ID")
	private int mapId;

    public TmonevtMappingPK() {
    }
	public int getCdevt() {
		return this.cdevt;
	}
	public void setCdevt(int cdevt) {
		this.cdevt = cdevt;
	}
	public int getMapId() {
		return this.mapId;
	}
	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TmonevtMappingPK)) {
			return false;
		}
		TmonevtMappingPK castOther = (TmonevtMappingPK)other;
		return 
			(this.cdevt == castOther.cdevt)
			&& (this.mapId == castOther.mapId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cdevt;
		hash = hash * prime + this.mapId;
		
		return hash;
    }
}