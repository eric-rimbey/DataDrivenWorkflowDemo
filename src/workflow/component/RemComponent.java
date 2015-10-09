package workflow.component;

import java.awt.Color;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;

import workflow.Orientation;

public abstract class RemComponent {

	public abstract void addFocusListener(FocusListener listener);

	private ArrayList<RemComponent> children = new ArrayList<RemComponent>();
	private String identifier;

	public RemComponent(String identifier) {
		this.identifier = identifier;
	}

	public void addSubComponent(RemComponent component) {
		if (!children.contains(component)) {
			asJComponent().add(component.asJComponent());
			children.add(component);
		}
	}

	public void addTo(JPanel panel) {
		panel.add(this.asJComponent());
	}

	public abstract JComponent asJComponent();

	public abstract Optional<String> getText();

	public void setBackground(Color color) {
		asJComponent().setBackground(color);
	}

	public void setBorder(Border border) {
		asJComponent().setBorder(border);
	}

	public abstract void setLabel(String label);

	public abstract void setMessage(String message);

	public void setOrientation(Orientation orientation) {
		asJComponent().setLayout(new BoxLayout(asJComponent(), orientation.asBoxLayoutAxis()));
	}

	public abstract void setText(String text);

	public void show() {
		asJComponent().setVisible(true);
	}

	public void hide() {
		asJComponent().setVisible(false);
	}

	public String getIdentfier() {
		return identifier;
	}

	public ArrayList<RemComponent> children() {
		return children;
	}

}
