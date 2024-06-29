package bright.api.alaya.utils;


public class EnumsUtility {

	public enum EnumObjectType {
		OFFICE("office"), MEMBER("member"), TEAM("team"),LISTING("listing");

		private final String value;
		EnumObjectType(String value) {
			this.value = value;
		}
     	public String getValue() {
			return this.value;
		}
	}

}
