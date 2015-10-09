package workflow.test;



public class AlwaysTrue implements RemTest {

	@Override
	public boolean check() {
		return true;
	}

}
