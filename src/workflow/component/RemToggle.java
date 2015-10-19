package workflow.component;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusListener;
import java.util.Optional;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RemToggle extends RemComponent {

	private JComponent jComponent;
	private JLabel jLabel;
	private JLabel jMessage;
	private JCheckBox jToggle;

	public RemToggle(String identifier) {
		super(identifier);
		
		jComponent = new JPanel();
		jLabel = new JLabel();
		jMessage = new JLabel();
		jToggle = new JCheckBox();
		
		jComponent.add(jLabel);
		jComponent.add(jMessage);
		jComponent.add(jToggle);
	}

	@Override
	public void addFocusListener(FocusListener listener) {
		jToggle.addFocusListener(listener);
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
	public void setLabel(String label) {
		jLabel.setText(label);
	}

	@Override
	public void setMessage(String message) {
		jMessage.setText(message);
	}

	@Override
	public void setText(String text) {
		// do nothing
	}

}
