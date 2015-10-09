package workflow.component;

import java.awt.event.FocusListener;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class RemTextArea extends RemComponent {

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
		return Optional.of(jText.getText());
	}

	@Override
	public void setText(String text) {
		jText.setText(text);
	}

	private JPanel jComponent;
	private JLabel jMessage;
	private JTextArea jText;
	private JLabel jLabel;

	@Override
	public void setMessage(String message) {
		jMessage.setText(message);
		jMessage.setVisible(true);
	}

	public RemTextArea(String identifier) {
		super(identifier);
		jComponent = new JPanel();
		BoxLayout layout = new BoxLayout(jComponent, BoxLayout.Y_AXIS);
		jComponent.setLayout(layout);
		
		jText = new JTextArea();
		jText.setLineWrap(true);
		jText.setWrapStyleWord(true);
		jMessage = new JLabel();
		jLabel = new JLabel();

		jMessage.setVisible(false);

		jComponent.add(jLabel);
		jComponent.add(jMessage);
		jComponent.add(jText);
	}

	@Override
	public void setLabel(String text) {
		jLabel.setText(text.trim());
	}

}
