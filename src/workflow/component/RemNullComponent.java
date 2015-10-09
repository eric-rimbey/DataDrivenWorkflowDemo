package workflow.component;

import java.awt.event.FocusListener;
import java.util.Optional;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class RemNullComponent extends RemComponent {

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
		return Optional.empty();
	}

	@Override
	public void setMessage(String message) {
		// do nothing
	}

	@Override
	public void setText(String text) {
		// do nothing
	}

	private JComponent jComponent = new JLabel("hidden");

	public RemNullComponent() {
		super("");
//		jComponent.setVisible(false);
	}

	@Override
	public void setLabel(String textContent) {
		// TODO Auto-generated method stub
		
	}

}
