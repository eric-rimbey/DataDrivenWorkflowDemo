package workflow.test;

import java.util.List;

import workflow.RemValueSource;

public class NoneEmpty implements RemTest {

	private List<RemValueSource> values;

	public NoneEmpty(List<RemValueSource> values) {
		super();
		this.values = values;
	}

	@Override
	public boolean check() {
		boolean check = true;
		for (RemValueSource source : values) {
			check &= (0 < source.getValue().length());
		}
		return check;
	}

}
