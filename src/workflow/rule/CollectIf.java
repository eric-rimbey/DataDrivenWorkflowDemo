package workflow.rule;

import workflow.component.RemComponent;
import workflow.test.RemTest;

public class CollectIf extends WorkflowRule {

	private RemTest check;
	private RemComponent target;

	public CollectIf(RemTest check, RemComponent target) {
		this.check = check;
		this.target = target;
	}

	@Override
	public void apply() {
		if (check.check()) {
			target.show();
		} else {
			target.hide();
		}
	}

}
