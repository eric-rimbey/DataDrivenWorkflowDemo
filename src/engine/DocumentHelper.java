package engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class DocumentHelper {

	public final static Optional<String> getAttributeValue(String attribute, Element element) {
		String value = element.getAttribute(attribute);
		if (!value.isEmpty()) {
			return Optional.of(value);
		}
		return Optional.empty();
	}

	public final static Optional<String> getAttributeValue(String attribute, Node element) {
		if (Node.ELEMENT_NODE == element.getNodeType()) {
			return getAttributeValue(attribute, (Element) element);
		}
		return Optional.empty();
	}

	public final static List<Node> getChildElements(Node element) {
		ArrayList<Node> childList = new ArrayList<Node>();
		for (int i = 0; i < element.getChildNodes().getLength(); i++) {
			Node child = element.getChildNodes().item(i);
			if (Node.ELEMENT_NODE == child.getNodeType()) {
				childList.add(child);
			}
		}
		return childList;
	}

	public final static List<Node> getElementContainers(Element rootElement) {
		ArrayList<Node> elementList = new ArrayList<Node>();
		// we don't want to find all nodes named "elements", only the immediate
		// children of the root so named
		for (Node child : getChildElements(rootElement)) {
			if ("elements".equals(getElementName(child))) {
				elementList.add(child);
			}
		}
		return elementList;
	}

	public final static String getElementName(Node child) {
		return child.getNodeName();
	}

	public static List<Node> getPropertyContainers(Element rootElement) {
		ArrayList<Node> propertiesList = new ArrayList<Node>();
		// we don't want to find all nodes named "properties", only the immediate
		// children of the root so named
		for (Node child : getChildElements(rootElement)) {
			if ("properties".equals(getElementName(child))) {
				propertiesList.add(child);
			}
		}
		return propertiesList;
	}

	public static List<Node> getChildElementsByName(Node element, String name) {
		ArrayList<Node> childList = new ArrayList<Node>();
		for (int i = 0; i < element.getChildNodes().getLength(); i++) {
			Node child = element.getChildNodes().item(i);
			if (Node.ELEMENT_NODE == child.getNodeType() && name.equals(name)) {
				childList.add(child);
			}
		}
		return childList;
	}

	public static List<Node> getRulesContainers(Element modelRoot) {
		ArrayList<Node> propertiesList = new ArrayList<Node>();
		// we don't want to find all nodes named "rules", only the immediate
		// children of the root so named
		for (Node child : getChildElements(modelRoot)) {
			if ("rules".equals(getElementName(child))) {
				propertiesList.add(child);
			}
		}
		return propertiesList;
	}

}