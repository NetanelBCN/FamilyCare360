package demo.app.entities;


import java.util.Date;
import java.util.Map;

import demo.app.objects.CreatedBy;
import demo.app.objects.Location;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "OBJECTS")
public class ObjectEntity {


    @Id private String objectID;
    private String type;
    private String alias;
    private Boolean active;
    private Date creationTimeStamp;
	@Transient
    private CreatedBy createdBy;
	@Transient
    private Map<String, Object> objectDetails;
	@Transient
	public Location location;


    public ObjectEntity() {
    }

    public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(Date creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    public Map<String, Object> getObjectDetails() {
        return objectDetails;
    }

    public void setObjectDetails(Map<String, Object> objectDetails) {
        this.objectDetails = objectDetails;
    }

    @Override
    public String toString() {
        return "ObjectEntity{" +
                "objectID='" + objectID + '\'' +
                ", type='" + type + '\'' +
                ", alias='" + alias + '\'' +
                ", location='" + location + '\'' +
                ", active=" + active +
                ", creationTimeStamp=" + creationTimeStamp +
                ", createdBy=" + createdBy +
                ", objectDetails=" + objectDetails +
                '}';
    }
}
