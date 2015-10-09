package workflow;

import workflow.component.RemComponent;


public class ComponentPropertySource implements RemValueSource {

	private String function;
	private RemComponent component;

	public ComponentPropertySource(String function, RemComponent component) {
		this.function = function;
		this.component = component;
	}

	@Override
	public String getValue() {
		String value = "";
		switch (function) {
		case "textlength":
			String text = component.getText().orElse("");
			value = Integer.toString(text.length());
			break;
		case "value":
			value = component.getText().orElse("");
			break;
		default:
			value = "";
			break;
		}
		return value;
	}

}
