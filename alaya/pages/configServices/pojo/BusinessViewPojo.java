package bright.api.alaya.pages.configServices.pojo;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;



public class BusinessViewPojo {
	@SerializedName("configType")
    private String configType;

	
	@SerializedName("description")
    private Object description;
	
	@SerializedName("resource")
    private Object resource;
	
    public Object getResource() {
		return resource;
	}
	public void setResource(Object resource) {
		this.resource = resource;
	}
	@SerializedName("displayName")
    private String displayName;
    @SerializedName("externalVisibleFlag")
    private Boolean externalVisibleFlag;
    @SerializedName("id")
    private long id;
    @SerializedName("internalDescription")
    private String internalDescription;
    @SerializedName("shortDescription")
    private String shortDescription;
    @SerializedName("systemName")
    private String systemName;
    @SerializedName("fields")
    private Map<String, FieldsPojo> fields;
    private Boolean isRetsEnabled;
    
	public String getConfigType() {
		return configType;
	}
	public void setConfigType(String configType) {
		this.configType = configType;
	}
	public Object getDescription() {
		return description;
	}
	public void setDescription(Object description) {
		this.description = description;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Boolean getExternalVisibleFlag() {
		return externalVisibleFlag;
	}
	public void setExternalVisibleFlag(Boolean externalVisibleFlag) {
		this.externalVisibleFlag = externalVisibleFlag;
	}
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInternalDescription() {
		return internalDescription;
	}
	public void setInternalDescription(String internalDescription) {
		this.internalDescription = internalDescription;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	 public boolean getIsRetsEnabled() {
	    return isRetsEnabled;
	}
	 public void setIsRetsEnabled(boolean isRetsEnabled) {
	    this.isRetsEnabled = isRetsEnabled;
	}
	public Map<String, FieldsPojo> getFields() {
		return fields;
	}
	public void setFields(Map<String, FieldsPojo> fields) {
		this.fields = fields;
	}
	public void setDisplayName(List<String> displayName) {
		// TODO Auto-generated method stub
		
	}
	
}
