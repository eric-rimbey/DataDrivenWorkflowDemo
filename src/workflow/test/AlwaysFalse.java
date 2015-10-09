package workflow.test;



public class AlwaysFalse implements RemTest {

	@Override
	public boolean check() {
		return false;
	}

}
