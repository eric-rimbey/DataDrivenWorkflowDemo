package workflow.rule;

import java.util.ArrayList;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import workflow.ComponentPropertySource;
import workflow.ConstantSource;
import workflow.ModelPropertySource;
import workflow.NullAction;
import workflow.RemAction;
import workflow.RemValueSource;
import workflow.WorkflowComponentMap;
import workflow.component.RemComponent;
import workflow.test.AlwaysFalse;
import workflow.test.AlwaysTrue;
import workflow.test.AreEqual;
import workflow.test.NoneEmpty;
import workflow.test.RemTest;

public enum WorkflowRuleBuilder {
	MessageIfBuilder {
		@Override
		public WorkflowRule build(Node ruleNode, WorkflowComponentMap componentMap, Map<String, String> propertyMap) {
			RemAction successHandler = new NullAction();
			RemAction failureHandler = new NullAction();
			RemTest check = new AlwaysFalse();
			ArrayList<RemValueSource> values = new ArrayList<RemValueSource>();
			for (int i = 0; i < ruleNode.getChildNodes().getLength(); i++) {
				Node child = ruleNode.getChildNodes().item(i);

				switch (child.getNodeName()) {
				case "falsemessage":
					Node failTarget = child.getAttributes().getNamedItem("target");
					if (null != failTarget) {
						String message = child.getTextContent();
						failureHandler = new RemAction() {
							@Override
							public void execute() {
								componentMap.get(failTarget.getNodeValue()).setMessage(message);
							}
						};
					}
					break;
				case "truemessage":
					Node successTarget = child.getAttributes().getNamedItem("target");
					if (null != successTarget) {
						String message = child.getTextContent();
						successHandler = new RemAction() {
							@Override
							public void execute() {
								componentMap.get(successTarget.getNodeValue()).setMessage(message);
							}
						};
					}
					break;
				case "areequal":
					NodeList operands = child.getChildNodes();
					if (1 >= operands.getLength()) {
					} else {
						for (int o = 0; o < operands.getLength(); o++) {
							Node operand = operands.item(o);
							switch (operand.getNodeName()) {
							case "value":
								values.add(new ConstantSource(operand.getTextContent()));
								break;
							case "nodeproperty":
								values.add(new ComponentPropertySource(operand.getAttributes().getNamedItem("name").getTextContent(),
										componentMap.get(operand.getTextContent())));
								break;
							case "modelproperty":
								values.add(new ModelPropertySource(operand.getAttributes().getNamedItem("name").getTextContent(),
										propertyMap.get(operand.getTextContent())));
								break;
							default:
								break;
							}
						}
					}
					check = new AreEqual(values);
					break;

				default:
					break;
				}
			}
			return new MessageIf(check, successHandler, failureHandler);
		}

		@Override
		public String tagName() {
			return "messageif";
		}
	},
	NullWorkflowRuleBuilder {
		@Override
		public WorkflowRule build(Node ruleNode, WorkflowComponentMap componentMap, Map<String, String> propertyMap) {
			return new NullWorkflowRule();
		}

		@Override
		public String tagName() {
			return "null rule";
		}
	},

	ShowIfBuilder {
		@Override
		public WorkflowRule build(Node ruleNode, WorkflowComponentMap componentMap, Map<String, String> propertyMap) {
			RemTest check = new AlwaysTrue();
			// System.out.println(ruleNode.getAttributes().getLength());
			ruleNode.getAttributes().getNamedItem("target").getTextContent();
			// System.out.println(ruleNode.getAttributes().getNamedItem("target").getTextContent());
			RemComponent target = componentMap.get(ruleNode.getAttributes().getNamedItem("target").getNodeValue());
			ArrayList<RemValueSource> values = new ArrayList<RemValueSource>();
			for (int i = 0; i < ruleNode.getChildNodes().getLength(); i++) {
				Node child = ruleNode.getChildNodes().item(i);

				NodeList operands;
				switch (child.getNodeName()) {
				case "areequal":
					operands = child.getChildNodes();
					if (1 >= operands.getLength()) {
					} else {
						for (int o = 0; o < operands.getLength(); o++) {
							Node operand = operands.item(o);
							switch (operand.getNodeName()) {
							case "value":
								values.add(new ConstantSource(operand.getTextContent()));
								break;
							case "nodeproperty":
								values.add(new ComponentPropertySource(operand.getAttributes().getNamedItem("name").getTextContent(),
										componentMap.get(operand.getTextContent())));
								break;
							case "modelproperty":
								values.add(new ModelPropertySource(operand.getAttributes().getNamedItem("name").getTextContent(),
										propertyMap.get(operand.getTextContent())));
								break;
							default:
								break;
							}
						}
					}
					check = new AreEqual(values);
					break;
				case "noneempty":
					operands = child.getChildNodes();
					if (1 >= operands.getLength()) {
					} else {
						for (int o = 0; o < operands.getLength(); o++) {
							Node operand = operands.item(o);
							switch (operand.getNodeName()) {
							case "value":
								values.add(new ConstantSource(operand.getTextContent()));
								break;
							case "nodeproperty":
								values.add(new ComponentPropertySource(operand.getAttributes().getNamedItem("name").getTextContent(),
										componentMap.get(operand.getTextContent())));
								break;
							case "modelproperty":
								values.add(new ModelPropertySource(operand.getAttributes().getNamedItem("name").getTextContent(),
										propertyMap.get(operand.getTextContent())));
								break;
							default:
								break;
							}
						}
					}
					check = new NoneEmpty(values);
					break;

				default:
					break;
				}
			}
			return new CollectIf(check, target);
		}

		@Override
		public String tagName() {
			return "collectif";
		}
	};

	public static WorkflowRule buildRule(Node ruleNode, WorkflowComponentMap componentMap, Map<String, String> propertyMap) {
		WorkflowRuleBuilder ruleBuilder = getRuleBuilder(ruleNode.getNodeName());
		return ruleBuilder.build(ruleNode, componentMap, propertyMap);
	}

	private static WorkflowRuleBuilder getRuleBuilder(String tagName) {
		for (WorkflowRuleBuilder ruleBuilder : WorkflowRuleBuilder.values()) {
			if (ruleBuilder.tagName() == tagName) {
				System.out.println("Building rule: " + tagName);
				return ruleBuilder;
			}
		}
		System.out.println("Unknown rule: " + tagName);
		return WorkflowRuleBuilder.NullWorkflowRuleBuilder;
	}

	public abstract WorkflowRule build(Node ruleNode, WorkflowComponentMap componentMap, Map<String, String> propertyMap);

	public abstract String tagName();

}