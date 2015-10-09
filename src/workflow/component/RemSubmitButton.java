package workflow.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class RemSubmitButton extends RemComponent {

	@Override
	public void show() {
		jButton.setEnabled(true);
	}

	@Override
	public void hide() {
		jButton.setEnabled(false);
	}

	private JButton jButton;

	public RemSubmitButton(String identifier) {
		super(identifier);
		jButton = new JButton("Submit");
		jButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "hello");
			}
		});
	}

	@Override
	public void addFocusListener(FocusListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public JComponent asJComponent() {
		return jButton;
	}

	@Override
	public Optional<String> getText() {
		return Optional.empty();
	}

	@Override
	public void setLabel(String textContent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMessage(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub

	}

}
