package bright.api.alaya.pages.configServices.pojo;

import java.util.List;

public class PickListPojo {
	 private String lookup;
	    private List<String> pickListItems;
	    private String resource;
	    private List<String> longValue;
	    private List<String> shortValue;
	    private String pickListId;
	    private long businessViewId;
	    private boolean externalVisibleFlag;
	    private boolean isRetsEnabled;
	    private String systemName;
	    
	    public boolean getIsRetsEnabled() {
	    	return isRetsEnabled;
	    }
	    
	    public void setIsRetsEnabled(boolean isRetsEnabled) {
	    	this.isRetsEnabled = isRetsEnabled;
	    }
	    
	    public String getSystemName() {
	    	return systemName;
	    }
	    
	    public void setSystemName(String systemName) {
	    	this.systemName = systemName;
	    }

	    public boolean getExternalVisibleFlag() {
			return externalVisibleFlag;
		}

		public void setExternalVisibleFlag(boolean externalVisibleFlag) {
			this.externalVisibleFlag = externalVisibleFlag;
		}

		public int getPickListSize() {
	        return pickListSize;
	    }

	    private int pickListSize;

	    public List<String> getLongValue() {
	        return longValue;
	    }

	    public void setLongValue(List<String> longValue) {
	        this.longValue = longValue;
	    }

	    public List<String> getShortValue() {
	        return shortValue;
	    }

	    public void setShortValue(List<String> shortValue) {
	        this.shortValue = shortValue;
	    }

	    public String getLookup() {
	        return lookup;
	    }

	    public void setLookup(String lookup) {
	        this.lookup = lookup;
	    }

	    public List<String> getPickListItems() {
	        return pickListItems;
	    }

	    public void setPickListItems(List<String> pickListItems) {
	        this.pickListItems = pickListItems;
	        this.pickListSize = pickListItems.size();
	    }

	    public String getResource() {
	        return resource;
	    }

	    public void setResource(String resource) {
	        this.resource = resource;
	    }

	    @Override
	    public String toString() {
	        return "PickListDataTO{" +
	                "lookup='" + lookup + '\'' +
	                ", resource='" + resource + '\'' +
	                ", pickListItems=" + pickListItems + ",totalPicklistCount=" + pickListSize +
	                ", longValue=" + longValue +
	                ", shortValue=" + shortValue +
	                ", pickListId=" + pickListId +
	                ", businessViewId=" + businessViewId +
	                '}';
	    }

	    public String getPickListId() {
	        return pickListId;
	    }

	    public void setPickListId(String pickListId) {
	        this.pickListId = pickListId;
	    }

	    public long getBusinessViewId() {
	        return businessViewId;
	    }

	    public void setBusinessViewId(long businessViewId) {
	        this.businessViewId = businessViewId;
	    }
}
