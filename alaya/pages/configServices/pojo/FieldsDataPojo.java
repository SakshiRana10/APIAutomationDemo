package bright.api.alaya.pages.configServices.pojo;

import java.util.List;

public class FieldsDataPojo {

	private String className;

	private List<String> standardNamevalue;

	private String resource;
	private List<String> longValue;
	private List<String> shortValue;
	private List<Long> fieldId;
	private List<Long> helpIds;
	private List<String> helpText;
	public List<String> getHelpText() {
		return helpText;
	}
	public void setHelpText(List<String> helpText) {
		this.helpText = helpText;
	}
	private long businessViewId;
	private List<String> pickListMaxInput;
	private List<String> databaseName;
	private List<String> alignment;
	private List<String> useSeparator;
	
	private List<String> caseName;
	private List<String> MRIS_MinimumLength;
	private List<String> MRIS_MinimumQueryLength;
	private List<String> upperCaseFlag;
	private List<String> defaultSearchOrder;
	private List<String> booleanLabels;
	private List<String> maximumValue;

	private List<String> minimumValue;

	private List<String> dataPrecision;

	
	public List<Long> getHelpIds() {
		return helpIds;
	}
	public void setHelpIds(List<Long> helpIds) {
		this.helpIds = helpIds;
	}
	public List<String> getUpperCaseFlag() {
		return upperCaseFlag;
	}
	public void setUpperCaseFlag(List<String> upperCaseFlag) {
		this.upperCaseFlag = upperCaseFlag;
	}
	public List<String> getDefaultSearchOrder() {
		return defaultSearchOrder;
	}
	public void setDefaultSearchOrder(List<String> defaultSearchOrder) {
		this.defaultSearchOrder = defaultSearchOrder;
	}
	public List<String> getBooleanLabels() {
		return booleanLabels;
	}
	public void setBooleanLabels(List<String> booleanLabels) {
		this.booleanLabels = booleanLabels;
	}
	public List<String> getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(List<String> databaseName) {
		this.databaseName = databaseName;
	}
	public List<String> getAlignment() {
		return alignment;
	}
	public void setAlignment(List<String> alignment) {
		this.alignment = alignment;
	}
	public List<String> getUseSeparator() {
		return useSeparator;
	}
	public void setUseSeparator(List<String> useSeparator) {
		this.useSeparator = useSeparator;
	}
	public List<String> getCaseName() {
		return caseName;
	}
	public void setCaseName(List<String> caseName) {
		this.caseName = caseName;
	}
	public List<String> getMRIS_MinimumLength() {
		return MRIS_MinimumLength;
	}
	public void setMRIS_MinimumLength(List<String> mRIS_MinimumLength) {
		MRIS_MinimumLength = mRIS_MinimumLength;
	}
	public List<String> getMRIS_MinimumQueryLength() {
		return MRIS_MinimumQueryLength;
	}
	public void setMRIS_MinimumQueryLength(List<String> mRIS_MinimumQueryLength) {
		MRIS_MinimumQueryLength = mRIS_MinimumQueryLength;
	}
	

	public List<String> getStandardNamevalue() {
		return standardNamevalue;
	}
	public void setStandardNamevalue(List<String> standardNamevalue) {
		this.standardNamevalue = standardNamevalue;
	}
	public List<String> getPickListMaxInput() {
		return pickListMaxInput;
	}
	public void setPickListMaxInput(List<String> pickListMaxInput) {
		this.pickListMaxInput = pickListMaxInput;
	}
	public List<String> getMaximumValue() {
		return maximumValue;
	}
	public void setMaximumValue(List<String> maximumValue) {
		this.maximumValue = maximumValue;
	}
	public List<String> getMinimumValue() {
		return minimumValue;
	}
	public void setMinimumValue(List<String> minimumValue) {
		this.minimumValue = minimumValue;
	}
	public List<String> getPickListSystemName() {
		return pickListSystemName;
	}
	public void setPickListSystemName(List<String> pickListSystemName) {
		this.pickListSystemName = pickListSystemName;
	}

	public List<String> getDataPrecision() {
		return dataPrecision;
	}
	public void setDataPrecision(List<String> dataPrecision) {
		this.dataPrecision = dataPrecision;
	}
	private List<String> pickListSystemName;

	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	private List<String> name;
	public List<String> getName() {
		return name;
	}
	public void setName(List<String> name) {
		this.name = name;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public List<String> getLongValue() {
		return longValue;
	}
	public void setLongValue(List<String> string) {
		this.longValue = string;
	}
	public List<String> getShortValue() {
		return shortValue;
	}
	public void setShortValue(List<String> string) {
		this.shortValue = string;
	}
	public List<Long> getFieldId() {
		return fieldId;
	}
	public void setFieldId(List<Long> fieldId) {
		this.fieldId = fieldId;
	}
	public long getBusinessViewId() {
		return businessViewId;
	}
	public void setBusinessViewId(long businessViewId) {
		this.businessViewId = businessViewId;
	}

}
