package workflow;

import java.util.HashMap;

import workflow.component.RemNullComponent;
import workflow.component.RemComponent;

public class WorkflowComponentMap {

	public void add(RemComponent component) {
		put(component.getIdentfier(), component);
		for (RemComponent child : component.children()) {
			add(child);
		}
	}

	public RemComponent get(String key) {
		// System.out.println("==========");
		// System.out.println("searching for "+key+ " in map");
//		for (String key1 : map.keySet()) {
			// System.out.print(key1);System.out.print(" = ");System.out.println(map.get(key1));
//		}
		// System.out.println("==========");
		return (map.containsKey(key)) ? map.get(key) : nullComponent;
	}

	public void put(String key, RemComponent value) {
		// System.out.print(key);System.out.print(" set to ");System.out.println(value);
		map.put(key, value);
	}

	HashMap<String, RemComponent> map = new HashMap<String, RemComponent>();

	private RemComponent nullComponent = new RemNullComponent();

}
