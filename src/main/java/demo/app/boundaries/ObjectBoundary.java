package demo.app.boundaries;

import demo.app.entities.ObjectEntity;
import demo.app.objects.CreatedBy;
import demo.app.objects.Location;
import demo.app.objects.ObjectId;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class ObjectBoundary {

    private ObjectId objectID;
    private String type;
    private String alias;
    private Location location;
    private Boolean active;
    private Date creationTimeStamp;
    private CreatedBy createdBy;
    private Map<String, Object> objectDetails;

    public ObjectBoundary() {
    }

    public ObjectBoundary(ObjectEntity objectEntity) {
        String[] splitId = objectEntity.getObjectID().split("_");
        this.objectID = new ObjectId();
        this.objectID.setId(splitId[0]);
        this.objectID.setSuperApp(splitId[1]);
        setObjectDetails(objectEntity.getObjectDetails());
        setActive(objectEntity.getActive());
        setCreationTimeStamp(objectEntity.getCreationTimeStamp());
        setCreatedBy(objectEntity.getCreatedBy());
        setType(objectEntity.getType());
        setAlias(objectEntity.getAlias());
        setLocation(objectEntity.getLocation());
    }

    public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ObjectId getObjectID() {
        return objectID;
    }

    public void setObjectID(ObjectId objectID) {
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

    public Boolean getActive() {
        return active;
    }

    public Map<String, Object> getObjectDetails() {
        return objectDetails;
    }

    public void setObjectDetails(Map<String, Object> objectDetails) {
        this.objectDetails = objectDetails;
    }

    public ObjectEntity toEntity() {
        ObjectEntity objectEntity = new ObjectEntity();

        objectEntity.setObjectID(this.getObjectID().getId() + "_" + this.getObjectID().getSuperApp());
        objectEntity.setType(this.getType());
        objectEntity.setCreationTimeStamp(this.getCreationTimeStamp());
        objectEntity.setCreatedBy(this.getCreatedBy());
        objectEntity.setActive(this.getActive() == null || this.getActive());
        objectEntity.setAlias(this.getAlias() == null ? "demo instance" : this.getAlias());
        objectEntity.setObjectDetails(this.getObjectDetails() == null ? new HashMap<>() : this.getObjectDetails());
        objectEntity.setLocation(this.location);


        return objectEntity;
    }

    @Override
    public String toString() {
        return "ObjectBoundary{" +
                "objectID=" + objectID +
                ", type='" + type + '\'' +
                ", alias='" + alias + '\'' +
                ", loction='" + location + '\'' +
                ", active=" + active +
                ", creationTimeStamp=" + creationTimeStamp +
                ", createdBy=" + createdBy +
                ", objectDetails=" + objectDetails +
                '}';
    }
}
