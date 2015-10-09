package engine;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import workflow.Orientation;
import workflow.WorkflowComponentMap;
import workflow.WorkflowModel;
import workflow.component.RemComponent;
import workflow.component.RemComponentBuilder;
import workflow.component.RemRoot;
import workflow.rule.WorkflowRule;
import workflow.rule.WorkflowRuleBuilder;

public class WorkflowEngine implements FocusListener {

	public void applyRules() {
		for (WorkflowRule rule : rules) {
			rule.apply();
		}
	}

	public void buildInto(JPanel workflowContainer) {
		workflowContainer.removeAll();
		rootComponent.addTo(workflowContainer);
	}

	@Override
	public void focusGained(FocusEvent e) {
		// do nothing
	}

	@Override
	public void focusLost(FocusEvent e) {
		applyRules();
	}

	private RemRoot createComponents(Element modelRoot) {
		String rootIdentifier = DocumentHelper.getAttributeValue("elem", modelRoot).orElse("");
		Orientation initialOrientation = Orientation.Vertical;
		RemRoot rootComponent = new RemRoot(rootIdentifier, initialOrientation);
		Stack<String> parentPath = new Stack<String>();
		parentPath.push(rootIdentifier);
		FocusListener listener = this;

		List<Node> elementContainers = DocumentHelper.getElementContainers(modelRoot);
		for (Node elementContainer : elementContainers) {
			String containerIdentifier = DocumentHelper.getAttributeValue("elem", elementContainer).orElse("");
			parentPath.push(containerIdentifier);
			List<Node> elements = DocumentHelper.getChildElements(elementContainer);
			for (Node element : elements) {
				RemComponent component = RemComponentBuilder.buildComponent(element,
						initialOrientation.orthogonalOrientation(), parentPath, listener);
				rootComponent.addSubComponent(component);
				componentMap.add(component);
			}
			parentPath.pop();
		}
		return rootComponent;
	}

	private Optional<Element> createDocumentTree() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
			Document dom = db.parse(model.asXmlStream());
			return Optional.of(dom.getDocumentElement());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			// } catch (SAXException | IOException e) {
			// e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	private Map<String, String> createProperties(Element modelRoot) {
		HashMap<String, String> propertyMap = new HashMap<String, String>();
		List<Node> propertyContainers = DocumentHelper.getPropertyContainers(modelRoot);
		for (Node propertyContainer : propertyContainers) {
			List<Node> properties = DocumentHelper.getChildElementsByName(propertyContainer, "property");
			for (Node property : properties) {
				Optional<String> key = DocumentHelper.getAttributeValue("key", property);
				if (key.isPresent()) {
					propertyMap.put(key.get(), property.getTextContent());
				}
			}
		}
		return propertyMap;
	}

	private List<WorkflowRule> createRules(Element modelRoot) {
		ArrayList<WorkflowRule> rulesList = new ArrayList<WorkflowRule>();
		List<Node> rulesContainers = DocumentHelper.getRulesContainers(modelRoot);
		for (Node ruleContainer : rulesContainers) {
			List<Node> rules = DocumentHelper.getChildElementsByName(ruleContainer, "rule");
			for (Node rule : rules) {
				rulesList.add(WorkflowRuleBuilder.buildRule(rule, componentMap, propertyMap));
			}
		}
		return rulesList;
	}

	private void inflateModel() {
		componentMap = new WorkflowComponentMap();
		propertyMap = new HashMap<String, String>();
		rules = new ArrayList<WorkflowRule>();

		modelDocumentTree = createDocumentTree();

		if (modelDocumentTree.isPresent()) {
			Element modelRoot = modelDocumentTree.get();
			propertyMap = createProperties(modelRoot);
			rootComponent = createComponents(modelRoot);
			rules = createRules(modelRoot);
		}
	}

	private WorkflowComponentMap componentMap;

	private WorkflowModel model;

	private Optional<Element> modelDocumentTree;

	private Map<String, String> propertyMap;

	private RemRoot rootComponent;

	private List<WorkflowRule> rules;

	public WorkflowEngine(WorkflowModel model) {
		this.model = model;
		inflateModel();
	}

}
