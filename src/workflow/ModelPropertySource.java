package workflow;

public class ModelPropertySource implements RemValueSource {

	private String function;
	private String baseValue;

	public ModelPropertySource(String function, String baseValue) {
//		System.out.println(function);
//		System.out.println(baseValue);
		this.function = function;
		this.baseValue = baseValue;
	}

	@Override
	public String getValue() {
		String value = "";
		switch (function) {
		case "textlength":
			value = Integer.toString(baseValue.length());
			break;
		case "value":
			value = baseValue;
			break;
		default:
			value = "";
			break;
		}
		return value;
	}

}
