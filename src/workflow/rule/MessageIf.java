package workflow.rule;

import workflow.RemAction;
import workflow.test.RemTest;

public class MessageIf extends WorkflowRule {

	private RemTest test;

	@Override
	public void apply() {
		if (test.check()) {
			successHandler.execute();
		} else {
			failureHandler.execute();
		}
	}

	private RemAction failureHandler;
	private RemAction successHandler;
	
	public MessageIf(RemTest test, RemAction successHandler, RemAction failureHandler) {
		this.test = test;
		this.successHandler = successHandler;
		this.failureHandler = failureHandler;
	}

}
