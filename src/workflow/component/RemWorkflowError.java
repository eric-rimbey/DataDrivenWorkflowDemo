package workflow.component;

import java.awt.event.FocusListener;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class RemWorkflowError extends RemComponent {

	@Override
	public void addFocusListener(FocusListener listener) {
		// do nothing
	}

	@Override
	public JComponent asJComponent() {
		return jComponent;
	}

	@Override
	public Optional<String> getText() {
		return Optional.of(jComponent.getText());
	}

	@Override
	public void setMessage(String message) {
		// do nothing
	}

	@Override
	public void setText(String text) {
		// do nothing
	}

	private JLabel jComponent;

	public RemWorkflowError(String identifier, String string) {
		super(identifier);
		jComponent = new JLabel(string);
	}

	@Override
	public void setLabel(String textContent) {
		// TODO Auto-generated method stub
		
	}

}
