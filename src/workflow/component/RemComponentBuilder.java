package workflow.component;

import java.awt.event.FocusListener;
import java.util.Stack;

import javax.swing.BorderFactory;

import org.w3c.dom.Node;

import workflow.Orientation;
import engine.DocumentHelper;

public enum RemComponentBuilder {
	ToggleBuilder {
		@Override
		public String elementType() {
			return "toggle";
		}

		@Override
		public RemComponent build(Node element, Orientation orientation, Stack<String> parentPath, FocusListener listener) {
			String elemId = DocumentHelper.getAttributeValue("elem", element).orElse("");
			Stack<String> path = new Stack<String>();
			path.addAll(parentPath);
			path.push(elemId);
			
			RemToggle toggle = new RemToggle(identifierFromPath(path));
			toggle.addFocusListener(listener);
			toggle.setLabel(DocumentHelper.getAttributeValue("label", element).orElse(""));
			return toggle;
		}
	},
	SelectBuilder {
		@Override
		public String elementType() {
			return "select";
		}

		@Override
		public RemComponent build(Node element, Orientation orientation, Stack<String> parentPath, FocusListener listener) {
			RemComponent select = TextFieldBuilder.build(element, orientation, parentPath, listener);
			select.setMessage("placeholder for selection / radio");
			return select;
		}
	},
	CurrencyBuilder {
		@Override
		public String elementType() {
			return "currency";
		}

		@Override
		public RemComponent build(Node element, Orientation orientation, Stack<String> parentPath, FocusListener listener) {
			RemComponent currency = TextFieldBuilder.build(element, orientation, parentPath, listener);
			currency.setMessage("placeholder for currency: " + currency.getIdentfier());
			return currency;
		}
	},
	InterviewBuilder {
		@Override
		public String elementType() {
			return "interview";
		}

		@Override
		public RemComponent build(Node element, Orientation orientation, Stack<String> parentPath, FocusListener listener) {
			String groupId = DocumentHelper.getAttributeValue("elem", element).orElse("");
			Stack<String> path = new Stack<String>();
			path.addAll(parentPath);
			path.push(groupId);
			
			RemInterviewGroup group = new RemInterviewGroup(identifierFromPath(path));
			group.setLabel(DocumentHelper.getAttributeValue("label", element).orElse(""));
			Orientation childOrientation = orientation.orthogonalOrientation();
			for (Node childNode : DocumentHelper.getChildElements(element)) {
				RemComponent childComponent = RemComponentBuilder.buildComponent(childNode, childOrientation, path, listener);
				group.addSubComponent(childComponent);
			}
			return group;
		}
	},
	SubmitBuilder {
		@Override
		public String elementType() {
			return "submit";
		}

		@Override
		public RemComponent build(Node element, Orientation orientation, Stack<String> parentPath, FocusListener listener) {
			RemSubmitButton submit = new RemSubmitButton("submit");
			submit.setLabel("submit");
			return submit;
		}
	},
	TextAreaBuilder {
		@Override
		public String elementType() {
			return "multilinetext";
		}

		@Override
		public RemComponent build(Node element, Orientation orientation, Stack<String> parentPath, FocusListener listener) {
			String groupId = DocumentHelper.getAttributeValue("elem", element).orElse("");
			Stack<String> path = new Stack<String>();
			path.addAll(parentPath);
			path.push(groupId);
			
			RemTextArea text = new RemTextArea(identifierFromPath(path));
			text.addFocusListener(listener);
			text.setLabel(DocumentHelper.getAttributeValue("label", element).orElse(""));
			return text;
		}
	},
	TextFieldBuilder {
		@Override
		public String elementType() {
			return "text";
		}

		@Override
		public RemComponent build(Node element, Orientation orientation, Stack<String> parentPath, FocusListener listener) {
			String elemId = DocumentHelper.getAttributeValue("elem", element).orElse("");
			Stack<String> path = new Stack<String>();
			path.addAll(parentPath);
			path.push(elemId);
			
			RemTextField text = new RemTextField(identifierFromPath(path));
			text.addFocusListener(listener);
			text.setLabel(DocumentHelper.getAttributeValue("label", element).orElse(""));
			return text;
		}
	},
	GroupBuilder {
		@Override
		public String elementType() {
			return "group";
		}

		@Override
		public RemComponent build(Node element, Orientation orientation, Stack<String> parentPath, FocusListener listener) {
			String groupId = DocumentHelper.getAttributeValue("elem", element).orElse("");
			Stack<String> path = new Stack<String>();
			path.addAll(parentPath);
			path.push(groupId);
			
			RemGroup group = new RemGroup(identifierFromPath(path), orientation);
			group.setLabel(DocumentHelper.getAttributeValue("label", element).orElse(""));
			Orientation childOrientation = orientation.orthogonalOrientation();
			for (Node childNode : DocumentHelper.getChildElements(element)) {
				RemComponent childComponent = RemComponentBuilder.buildComponent(childNode, childOrientation, path, listener);
				group.addSubComponent(childComponent);
			}
			return group;
		}
	},
	UnknownComponentBuilder {
		@Override
		public String elementType() {
			return "unknown";
		}

		@Override
		public RemComponent build(Node element, Orientation orientation, Stack<String> parentPath, FocusListener listener) {
			return new RemNullComponent();
		}
	};

	public static RemComponent buildComponent(Node element, Orientation orientation, Stack<String> parentPath,
			FocusListener listener) {
		String elementName = DocumentHelper.getElementName(element);
		for (RemComponentBuilder builder : RemComponentBuilder.values()) {
			if (elementName.equals(builder.elementType())) {
				System.out.print("building component: ");System.out.println(elementName);
				RemComponent component = builder.build(element, orientation, parentPath, listener);
				return component;
			}
		}
		System.out.print("unknown component type: ");System.out.println(elementName);
		return UnknownComponentBuilder.build(element, orientation, parentPath, listener);
	}

	protected String identifierFromPath(Stack<String> path) {
		String id = String.join(".", path);
		id = id.replaceAll("\\.+", ".");
		return id ;
	}

	public abstract String elementType();

	public abstract RemComponent build(Node element, Orientation orientation, Stack<String> parentPath, FocusListener listener);

}
