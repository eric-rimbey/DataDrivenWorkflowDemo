package workflow.test;

import java.util.List;

import workflow.RemValueSource;

public class AreEqual implements RemTest {

	private List<RemValueSource> values;

	public AreEqual(List<RemValueSource> values) {
		this.values = values;
	}

	@Override
	public boolean check() {
		if (1 >= values.size()) {
			return true;
		} else {
			String value = values.get(0).getValue();
			boolean check = true;
			for (RemValueSource source : values) {
				check &= (source.getValue().equals(value));
			}
			return check;
		}
	}


}
