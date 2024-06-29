package bright.api.alaya.pages.configServices.pojo;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class FieldsPojo {

	private boolean  isRetsEnabled;

	public boolean isRetsEnabled() {
		return isRetsEnabled;
	}
	public void setRetsEnabled(boolean isRetsEnabled) {
		this.isRetsEnabled = isRetsEnabled;
	}

	private String resource;

	private long businessViewId;


	private String  className;
	
	private boolean  externalVisibleFlag;

	public boolean isExternalVisibleFlag() {
		return externalVisibleFlag;
	}
	public void setExternalVisibleFlag(boolean externalVisibleFlag) {
		this.externalVisibleFlag = externalVisibleFlag;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public long getBusinessViewId() {
		return businessViewId;
	}
	public void setBusinessViewId(long businessViewId) {
		this.businessViewId = businessViewId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@SerializedName("allowFutureDateFlag")
	private Boolean mAllowFutureDateFlag;

	@SerializedName("autoPopulatedFlag")
	private Boolean mAutoPopulatedFlag;
	@SerializedName("availableFlag")
	private Boolean mAvailableFlag;
	@SerializedName("copyOnCloneFlag")
	private Boolean mCopyOnCloneFlag;
	@SerializedName("currencyFlag")
	private Boolean mCurrencyFlag;
	@SerializedName("dataPrecision")
	private int mDataPrecision;
	@SerializedName("dataScale")
	private Object mDataScale;
	@SerializedName("dataTypeDisplayName")
	private String mDataTypeDisplayName;
	@SerializedName("dataTypeId")
	private Long mDataTypeId;
	@SerializedName("dataTypeName")
	private String mDataTypeName;
	@SerializedName("dataTypeSystemName")
	private String mDataTypeSystemName;
	@SerializedName("datatypeDeprecated")
	private Boolean mDatatypeDeprecated;
	@SerializedName("defaultValue")
	private Object mDefaultValue;
	@SerializedName("displayHeight")
	private Object mDisplayHeight;
	@SerializedName("displayLength")
	private Object mDisplayLength;
	@SerializedName("displayOrder")
	private Long mDisplayOrder;
	@SerializedName("enterableFlag")
	private Boolean mEnterableFlag;
	@SerializedName("falseLabel")
	private Object mFalseLabel;
	@SerializedName("fieldDisplayName")
	private String mFieldDisplayName;
	@SerializedName("fieldId")
	private Long mFieldId;
	@SerializedName("fieldName")
	private String mFieldName;
	@SerializedName("fieldSystemName")
	private String mFieldSystemName;
	@SerializedName("groupSetDisplayOrder")
	private Long mGroupSetDisplayOrder;
	@SerializedName("helpDetails")
	private Object mHelpDetails;
	@SerializedName("helpText")
	private String mHelpText;
	@SerializedName("internalFieldFlag")
	private Boolean mInternalFieldFlag;
	@SerializedName("isDeprecated")
	private Boolean mIsDeprecated;
	@SerializedName("isMandatory")
	private Boolean mIsMandatory;
	@SerializedName("jsonDataType")
	private String mJsonDataType;
	@SerializedName("jsonFormat")
	private String mJsonFormat;
	@SerializedName("longName")
	private String mLongName;
	@SerializedName("mandatoryForAutoPopFlag")
	private Boolean mMandatoryForAutoPopFlag;
	@SerializedName("maximumLength")
	private Object mMaximumLength;
	@SerializedName("maximumValue")
	private String mMaximumValue;
	@SerializedName("minimumLength")
	private String mMinimumLength;
	@SerializedName("minimumValue")
	private String mMinimumValue;
	@SerializedName("multiValuedFlag")
	private Boolean mMultiValuedFlag;
	@SerializedName("name")
	private String mName;
	@SerializedName("nullValue")
	private Object mNullValue;
	@SerializedName("nullableFlag")
	private Boolean mNullableFlag;
	@SerializedName("pickListDisplayName")
	private Object mPickListDisplayName;
	@SerializedName("pickListId")
	private String pickListId;
	@SerializedName("pickListItems")
	private List<String> pickListItems;
	@SerializedName("pickListMaxInput")
	private Object mPickListMaxInput;
	@SerializedName("pickListSystemName")
	private String mPickListSystemName;
	@SerializedName("shortName")
	private String mShortName;
	@SerializedName("standardName")
	private String mStandardName;
	@SerializedName("trueLabel")
	private Object mTrueLabel;
	@SerializedName("updatableFlag")
	private Boolean mUpdatableFlag;
	@SerializedName("upperCaseFlag")
	private Boolean mUpperCaseFlag;
	@SerializedName("virtualFlag")
	private Boolean mVirtualFlag;
	@SerializedName("visibleFlag")
	private Boolean mVisibleFlag;
	@SerializedName("xmlTag")
	private String mXmlTag;
	private List<String>ruleUsageIds;

	public List<String> getRuleUsageIds() {
		return ruleUsageIds;
	}
	public void setRuleUsageIds(List<String> ruleUsageIds) {
		this.ruleUsageIds = ruleUsageIds;
	}
	public Boolean getmAllowFutureDateFlag() {
		return mAllowFutureDateFlag;
	}
	public void setmAllowFutureDateFlag(Boolean mAllowFutureDateFlag) {
		this.mAllowFutureDateFlag = mAllowFutureDateFlag;
	}
	public Boolean getmAutoPopulatedFlag() {
		return mAutoPopulatedFlag;
	}
	public void setmAutoPopulatedFlag(Boolean mAutoPopulatedFlag) {
		this.mAutoPopulatedFlag = mAutoPopulatedFlag;
	}
	public Boolean getmAvailableFlag() {
		return mAvailableFlag;
	}
	public void setmAvailableFlag(Boolean mAvailableFlag) {
		this.mAvailableFlag = mAvailableFlag;
	}
	public Boolean getmCopyOnCloneFlag() {
		return mCopyOnCloneFlag;
	}
	public void setmCopyOnCloneFlag(Boolean mCopyOnCloneFlag) {
		this.mCopyOnCloneFlag = mCopyOnCloneFlag;
	}
	public Boolean getmCurrencyFlag() {
		return mCurrencyFlag;
	}
	public void setmCurrencyFlag(Boolean mCurrencyFlag) {
		this.mCurrencyFlag = mCurrencyFlag;
	}
	public int getmDataPrecision() {
		return mDataPrecision;
	}
	public void setmDataPrecision(int mDataPrecision) {
		this.mDataPrecision = mDataPrecision;
	}
	public Object getmDataScale() {
		return mDataScale;
	}
	public void setmDataScale(Object mDataScale) {
		this.mDataScale = mDataScale;
	}
	public String getmDataTypeDisplayName() {
		return mDataTypeDisplayName;
	}
	public void setmDataTypeDisplayName(String mDataTypeDisplayName) {
		this.mDataTypeDisplayName = mDataTypeDisplayName;
	}
	public Long getmDataTypeId() {
		return mDataTypeId;
	}
	public void setmDataTypeId(Long mDataTypeId) {
		this.mDataTypeId = mDataTypeId;
	}
	public String getmDataTypeName() {
		return mDataTypeName;
	}
	public void setmDataTypeName(String mDataTypeName) {
		this.mDataTypeName = mDataTypeName;
	}
	public String getmDataTypeSystemName() {
		return mDataTypeSystemName;
	}
	public void setmDataTypeSystemName(String mDataTypeSystemName) {
		this.mDataTypeSystemName = mDataTypeSystemName;
	}
	public Boolean getmDatatypeDeprecated() {
		return mDatatypeDeprecated;
	}
	public void setmDatatypeDeprecated(Boolean mDatatypeDeprecated) {
		this.mDatatypeDeprecated = mDatatypeDeprecated;
	}
	public Object getmDefaultValue() {
		return mDefaultValue;
	}
	public void setmDefaultValue(Object mDefaultValue) {
		this.mDefaultValue = mDefaultValue;
	}
	public Object getmDisplayHeight() {
		return mDisplayHeight;
	}
	public void setmDisplayHeight(Object mDisplayHeight) {
		this.mDisplayHeight = mDisplayHeight;
	}
	public Object getmDisplayLength() {
		return mDisplayLength;
	}
	public void setmDisplayLength(Object mDisplayLength) {
		this.mDisplayLength = mDisplayLength;
	}
	public Long getmDisplayOrder() {
		return mDisplayOrder;
	}
	public void setmDisplayOrder(Long mDisplayOrder) {
		this.mDisplayOrder = mDisplayOrder;
	}
	public Boolean getmEnterableFlag() {
		return mEnterableFlag;
	}
	public void setmEnterableFlag(Boolean mEnterableFlag) {
		this.mEnterableFlag = mEnterableFlag;
	}
	public Object getmFalseLabel() {
		return mFalseLabel;
	}
	public void setmFalseLabel(Object mFalseLabel) {
		this.mFalseLabel = mFalseLabel;
	}
	public String getmFieldDisplayName() {
		return mFieldDisplayName;
	}
	public void setmFieldDisplayName(String mFieldDisplayName) {
		this.mFieldDisplayName = mFieldDisplayName;
	}
	public Long getmFieldId() {
		return mFieldId;
	}
	public void setmFieldId(Long mFieldId) {
		this.mFieldId = mFieldId;
	}
	public String getmFieldName() {
		return mFieldName;
	}
	public void setmFieldName(String mFieldName) {
		this.mFieldName = mFieldName;
	}
	public String getmFieldSystemName() {
		return mFieldSystemName;
	}
	public void setmFieldSystemName(String mFieldSystemName) {
		this.mFieldSystemName = mFieldSystemName;
	}
	public Long getmGroupSetDisplayOrder() {
		return mGroupSetDisplayOrder;
	}
	public void setmGroupSetDisplayOrder(Long mGroupSetDisplayOrder) {
		this.mGroupSetDisplayOrder = mGroupSetDisplayOrder;
	}
	public Object getmHelpDetails() {
		return mHelpDetails;
	}
	public void setmHelpDetails(Object mHelpDetails) {
		this.mHelpDetails = mHelpDetails;
	}
	public String getmHelpText() {
		return mHelpText;
	}
	public void setmHelpText(String mHelpText) {
		this.mHelpText = mHelpText;
	}
	public Boolean getmInternalFieldFlag() {
		return mInternalFieldFlag;
	}
	public void setmInternalFieldFlag(Boolean mInternalFieldFlag) {
		this.mInternalFieldFlag = mInternalFieldFlag;
	}
	public Boolean getmIsDeprecated() {
		return mIsDeprecated;
	}
	public void setmIsDeprecated(Boolean mIsDeprecated) {
		this.mIsDeprecated = mIsDeprecated;
	}
	public Boolean getmIsMandatory() {
		return mIsMandatory;
	}
	public void setmIsMandatory(Boolean mIsMandatory) {
		this.mIsMandatory = mIsMandatory;
	}
	public String getmJsonDataType() {
		return mJsonDataType;
	}
	public void setmJsonDataType(String mJsonDataType) {
		this.mJsonDataType = mJsonDataType;
	}
	public String getmJsonFormat() {
		return mJsonFormat;
	}
	public void setmJsonFormat(String mJsonFormat) {
		this.mJsonFormat = mJsonFormat;
	}
	public String getmLongName() {
		return mLongName;
	}
	public void setmLongName(String mLongName) {
		this.mLongName = mLongName;
	}
	public Boolean getmMandatoryForAutoPopFlag() {
		return mMandatoryForAutoPopFlag;
	}
	public void setmMandatoryForAutoPopFlag(Boolean mMandatoryForAutoPopFlag) {
		this.mMandatoryForAutoPopFlag = mMandatoryForAutoPopFlag;
	}
	public Object getmMaximumLength() {
		return mMaximumLength;
	}
	public void setmMaximumLength(Object mMaximumLength) {
		this.mMaximumLength = mMaximumLength;
	}
	public String getmMaximumValue() {
		return mMaximumValue;
	}
	public void setmMaximumValue(String mMaximumValue) {
		this.mMaximumValue = mMaximumValue;
	}
	public Object getmMinimumLength() {
		return mMinimumLength;
	}
	public void setmMinimumLength(String mMinimumLength) {
		this.mMinimumLength = mMinimumLength;
	}
	public String getmMinimumValue() {
		return mMinimumValue;
	}
	public void setmMinimumValue(String mMinimumValue) {
		this.mMinimumValue = mMinimumValue;
	}
	public Boolean getmMultiValuedFlag() {
		return mMultiValuedFlag;
	}
	public void setmMultiValuedFlag(Boolean mMultiValuedFlag) {
		this.mMultiValuedFlag = mMultiValuedFlag;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public Object getmNullValue() {
		return mNullValue;
	}
	public void setmNullValue(Object mNullValue) {
		this.mNullValue = mNullValue;
	}
	public Boolean getmNullableFlag() {
		return mNullableFlag;
	}
	public void setmNullableFlag(Boolean mNullableFlag) {
		this.mNullableFlag = mNullableFlag;
	}
	public Object getmPickListDisplayName() {
		return mPickListDisplayName;
	}
	public void setmPickListDisplayName(Object mPickListDisplayName) {
		this.mPickListDisplayName = mPickListDisplayName;
	}
	public String getPickListId() {
		return pickListId;
	}
	public void setPickListId(String pickListId) {
		this.pickListId = pickListId;
	}
	public List<String> getPickListItems() {
		return pickListItems;
	}
	public void setPickListItems(List<String> pickListItems) {
		this.pickListItems = pickListItems;
	}
	public Object getmPickListMaxInput() {
		return mPickListMaxInput;
	}
	public void setmPickListMaxInput(Object mPickListMaxInput) {
		this.mPickListMaxInput = mPickListMaxInput;
	}
	public String getmPickListSystemName() {
		return mPickListSystemName;
	}
	public void setmPickListSystemName(String mPickListSystemName) {
		this.mPickListSystemName = mPickListSystemName;
	}
	public String getmShortName() {
		return mShortName;
	}
	public void setmShortName(String mShortName) {
		this.mShortName = mShortName;
	}
	public String getmStandardName() {
		return mStandardName;
	}
	public void setmStandardName(String mStandardName) {
		this.mStandardName = mStandardName;
	}
	public Object getmTrueLabel() {
		return mTrueLabel;
	}
	public void setmTrueLabel(Object mTrueLabel) {
		this.mTrueLabel = mTrueLabel;
	}
	public Boolean getmUpdatableFlag() {
		return mUpdatableFlag;
	}
	public void setmUpdatableFlag(Boolean mUpdatableFlag) {
		this.mUpdatableFlag = mUpdatableFlag;
	}
	public Boolean getmUpperCaseFlag() {
		return mUpperCaseFlag;
	}
	public void setmUpperCaseFlag(Boolean mUpperCaseFlag) {
		this.mUpperCaseFlag = mUpperCaseFlag;
	}
	public Boolean getmVirtualFlag() {
		return mVirtualFlag;
	}
	public void setmVirtualFlag(Boolean mVirtualFlag) {
		this.mVirtualFlag = mVirtualFlag;
	}
	public Boolean getmVisibleFlag() {
		return mVisibleFlag;
	}
	public void setmVisibleFlag(Boolean mVisibleFlag) {
		this.mVisibleFlag = mVisibleFlag;
	}
	public String getmXmlTag() {
		return mXmlTag;
	}
	public void setmXmlTag(String mXmlTag) {
		this.mXmlTag = mXmlTag;
	}
}