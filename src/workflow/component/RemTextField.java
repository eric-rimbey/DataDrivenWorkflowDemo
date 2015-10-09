package workflow.component;

import java.awt.Dimension;
import java.awt.event.FocusListener;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class RemTextField extends RemComponent {
	@Override
	public void addFocusListener(FocusListener listener) {
		jText.addFocusListener(listener);
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
	public void setMessage(String message) {
		jMessage.setText(message);
		jMessage.setVisible(true);
	}

	@Override
	public void setText(String message) {
		jText.setText(message);
	}

	private JTextField jText;
	private JPanel jComponent;
	private JLabel jMessage;
	private JLabel jLabel;

	public RemTextField(String identifier) {
		super(identifier);
		jComponent = new JPanel();
		BoxLayout layout = new BoxLayout(jComponent, BoxLayout.Y_AXIS);
		jComponent.setLayout(layout);
		jComponent.setAlignmentX(0.5f);
		jComponent.setAlignmentY(0.5f);
		
		jText = new JTextField();
		jMessage = new JLabel();
		jLabel = new JLabel();
		jLabel.setAlignmentX(0.0f);
		jLabel.setAlignmentY(0.0f);
		
		jText.setPreferredSize(new Dimension(200, 20));
		jText.setMaximumSize(new Dimension(300, 20));
		jText.setMinimumSize(new Dimension(50, 20));
		jText.setAlignmentX(0.0f);
		jText.setAlignmentY(1.0f);
		jMessage.setVisible(false);

		jComponent.add(jLabel);
		jComponent.add(jMessage);
		jComponent.add(jText);
	}

	@Override
	public void setLabel(String label) {
		jLabel.setText(label.trim());
	}

}
