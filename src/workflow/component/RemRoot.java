package workflow.component;

import java.awt.event.FocusListener;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import workflow.Orientation;

public class RemRoot extends RemComponent {

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

	public RemRoot(String identifier, Orientation orientation) {
		super(identifier);
		jComponent = new JPanel();
		jMessage = new JLabel();
		jComponent.add(jMessage);
		jMessage.setVisible(false);
		BoxLayout layout = new BoxLayout(jComponent, orientation.asBoxLayoutAxis());
		jComponent.setAlignmentX(0.5f);
		jComponent.setAlignmentY(0.5f);
		jComponent.setLayout(layout);
	}

	@Override
	public void setLabel(String textContent) {
		// TODO Auto-generated method stub

	}

}
