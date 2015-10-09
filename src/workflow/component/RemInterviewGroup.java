package workflow.component;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RemInterviewGroup extends RemComponent {

	@Override
	public void addFocusListener(FocusListener listener) {
		// do nothing
	}

	@Override
	public void addSubComponent(RemComponent component) {
		if (!children().contains(component)) {
			component.asJComponent().setAlignmentX(0.5f);
			component.asJComponent().setAlignmentY(0.5f);
			children().add(component);
			if (1 == children().size()) {
				jCenterPanel.removeAll();
				jCenterPanel.add(children().get(0).asJComponent());
				jCenterPanel.repaint();
				jComponent.validate();
			}
		}
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

	private JPanel jCenterPanel;

	private JPanel jComponent;

	private JLabel jLabel;

	private JLabel jMessage;

	private JButton jNextButton;

	private JButton jPreviousButton;

	public RemInterviewGroup(String identifier) {
		super(identifier);
		jComponent = new JPanel();
		// GridBagLayout layout = new GridBagLayout();
		BoxLayout layout = new BoxLayout(jComponent, BoxLayout.X_AXIS);
		jComponent.setLayout(layout);
		jComponent.setAlignmentX(0.5f);
		jComponent.setAlignmentY(0.5f);

		jLabel = new JLabel();
		jLabel.setForeground(Color.RED);
		jComponent.add(jLabel);

		jCenterPanel = new JPanel();
		jCenterPanel.setAlignmentX(0.5f);
		jCenterPanel.setAlignmentY(0.5f);
		jComponent.add(jCenterPanel);

		JPanel buttonPanel = new JPanel();
		BoxLayout buttonLayout = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);
		buttonPanel.setLayout(buttonLayout);
		jPreviousButton = new JButton("Previous");
		jPreviousButton.setActionCommand("previous");
		jNextButton = new JButton("Next");
		jNextButton.setActionCommand("next");
		jMessage = new JLabel();
		jMessage.setVisible(false);
		buttonPanel.add(jMessage);

		ActionListener buttonHandler = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// System.out.println(e.getActionCommand());
				// System.out.println("previous is " +
				// Integer.valueOf(childrenIterator.previousIndex()).toString());
				// System.out.println("next is " +
				// Integer.valueOf(childrenIterator.nextIndex()).toString());
				switch (e.getActionCommand()) {
				case "previous":
					visibleIndex = Math.max(0, -1 + visibleIndex);
					jCenterPanel.removeAll();
					jCenterPanel.add(children().get(visibleIndex).asJComponent());
					break;
				default:
					visibleIndex = Math.min(-1 + children().size(), 1 + visibleIndex);
					jCenterPanel.removeAll();
					jCenterPanel.add(children().get(visibleIndex).asJComponent());
					break;
				}
				jComponent.validate();
				jCenterPanel.repaint();
			}

			private int visibleIndex = 0;
		};

		jPreviousButton.addActionListener(buttonHandler);
		jNextButton.addActionListener(buttonHandler);

		buttonPanel.add(jPreviousButton);
		buttonPanel.add(jNextButton);
		jComponent.add(buttonPanel);
	}

}
