package workflow;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class WorkflowModel {

	private String xml;

	public WorkflowModel(String xml) {
		this.xml = xml;
	}

	public String asString() {
		return xml;
	};
	
	public InputStream asXmlStream() {
		return new ByteArrayInputStream(asString().getBytes());
	};

}
