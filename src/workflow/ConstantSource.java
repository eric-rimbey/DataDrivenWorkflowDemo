package workflow;

public class ConstantSource implements RemValueSource {

	private String value;

	public ConstantSource(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

}
