package bright.api.alaya.pages.configServices.pojo;

import java.util.List;



import com.google.gson.annotations.SerializedName;


public class PickListItemsPojo {


    @SerializedName("businessViewsDisplayValues")
    private List<Object> mBusinessViewsDisplayValues;
    @SerializedName("description")
    private Object mDescription;
    @SerializedName("displayValue")
    private String mDisplayValue;
    @SerializedName("displayValueLong")
    private String mDisplayValueLong;
    @SerializedName("isDeprecated")
    private Boolean mIsDeprecated;
    @SerializedName("itemValue")
    private String mItemValue;
    @SerializedName("sortOrder")
    private Long mSortOrder;
    @SerializedName("visibleFlag")
    private Boolean mVisibleFlag;

    public List<Object> getBusinessViewsDisplayValues() {
        return mBusinessViewsDisplayValues;
    }

    public void setBusinessViewsDisplayValues(List<Object> businessViewsDisplayValues) {
        mBusinessViewsDisplayValues = businessViewsDisplayValues;
    }

    public Object getDescription() {
        return mDescription;
    }

    public void setDescription(Object description) {
        mDescription = description;
    }

    public String getDisplayValue() {
        return mDisplayValue;
    }

    public void setDisplayValue(String displayValue) {
        mDisplayValue = displayValue;
    }

    public String getDisplayValueLong() {
        return mDisplayValueLong;
    }

    public void setDisplayValueLong(String displayValueLong) {
        mDisplayValueLong = displayValueLong;
    }

    public Boolean getIsDeprecated() {
        return mIsDeprecated;
    }

    public void setIsDeprecated(Boolean isDeprecated) {
        mIsDeprecated = isDeprecated;
    }

    public String getItemValue() {
        return mItemValue;
    }

    public void setItemValue(String itemValue) {
        mItemValue = itemValue;
    }

    public Long getSortOrder() {
        return mSortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        mSortOrder = sortOrder;
    }

    public Boolean getVisibleFlag() {
        return mVisibleFlag;
    }

    public void setVisibleFlag(Boolean visibleFlag) {
        mVisibleFlag = visibleFlag;
    }

}


