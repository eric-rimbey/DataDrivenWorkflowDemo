package workflow.component;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.FocusListener;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import workflow.Orientation;

public class RemGroup extends RemComponent {

	@Override
	public void addFocusListener(FocusListener listener) {
		jComponent.addFocusListener(listener);
	}

	@Override
	public JComponent asJComponent() {
		return jComponent;
	}

	@Override
	public Optional<String> getText() {
		return Optional.empty();
	}

	private JLabel jLabel;

	@Override
	public void setLabel(String label) {
		jLabel.setText(label);
		jLabel.setVisible(true);
	}

	@Override
	public void setMessage(String message) {
		jMessage.setText(message);
		jMessage.setVisible(true);
	}

	@Override
	public void setText(String text) {
		// do nothing
	}

	private JPanel jComponent;

	private JLabel jMessage;

	public RemGroup(String identifier, Orientation orientation) {
		super(identifier);
		jComponent = new JPanel();
		BoxLayout layout = new BoxLayout(jComponent, orientation.asBoxLayoutAxis());
		jComponent.setLayout(layout);
		jComponent.setAlignmentX(0.5f);
		jComponent.setAlignmentY(0.0f);
		jComponent.setBorder(BorderFactory.createEtchedBorder());
		jLabel = new JLabel();
		jComponent.add(jLabel);
		jMessage = new JLabel();
		jComponent.add(jMessage);
		jMessage.setVisible(false);
	}

}
