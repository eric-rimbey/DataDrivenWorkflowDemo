package engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import workflow.WorkflowModel;

public class Demo {

	public static void main(String[] args) {
		JFrame window = createDemoWindow();
		window.add(createDemoChooser(), BorderLayout.NORTH);
		window.add(createModelDisplay(), BorderLayout.WEST);
		window.add(createDoWorkflowButton(), BorderLayout.SOUTH);

		workflowContainer = createWorkflowContainer();
		window.add(workflowContainer, BorderLayout.CENTER);

		DemoWorkflow selection = (DemoWorkflow) workflowChooser.getSelectedItem();
		modelDisplay.setText(selection.xmlAsString());

		WorkflowEngine demo = new WorkflowEngine(selection.model());
		demo.buildInto(workflowContainer);
		demo.applyRules();

		window.pack();
		window.setVisible(true);
	}

	private static JComboBox<DemoWorkflow> createDemoChooser() {
		workflowChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DemoWorkflow selection = (DemoWorkflow) workflowChooser.getSelectedItem();
				modelDisplay.setText(selection.xmlAsString());
			}
		});
		return workflowChooser;
	}

	private static JFrame createDemoWindow() {
		JFrame window = new JFrame("Workflow Demo");
		window.setMinimumSize(new Dimension(800, 700));
		return window;
	};

	private static JButton createDoWorkflowButton() {
		JButton doWorkflow = new JButton("Run Workflow");
		doWorkflow.setActionCommand("doworkflow");
		doWorkflow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("doworkflow" == e.getActionCommand()) {
					WorkflowEngine demo = new WorkflowEngine(new WorkflowModel(modelDisplay.getText()));
					workflowContainer.removeAll();
					demo.buildInto(workflowContainer);
					demo.applyRules();
					workflowContainer.validate();
					workflowContainer.repaint();
				}
			}
		});
		return doWorkflow;
	}

	private static JPanel createModelDisplay() {
		JPanel xmlPanel = new JPanel();
		xmlPanel.setLayout(new BoxLayout(xmlPanel, BoxLayout.Y_AXIS));
		xmlPanel.setBackground(Color.YELLOW);
		xmlPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		JScrollPane pane = new JScrollPane(modelDisplay);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pane.setPreferredSize(new Dimension(400, 100));

		xmlPanel.add(pane);
		modelDisplay.setLineWrap(false);

		return xmlPanel;
	}

	private static JPanel createWorkflowContainer() {
		JPanel flowPanel = new JPanel();
		flowPanel.setLayout(new BoxLayout(flowPanel, BoxLayout.Y_AXIS));
		flowPanel.setBackground(Color.CYAN);
		flowPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		flowPanel.setPreferredSize(new Dimension(800, 500));
		flowPanel.setMinimumSize(new Dimension(800, 500));
		return flowPanel;
	}

	private static JTextArea modelDisplay = new JTextArea();

	private static JComboBox<DemoWorkflow> workflowChooser = new JComboBox<DemoWorkflow>(DemoWorkflow.values());

	private static JPanel workflowContainer = new JPanel();

	public enum DemoWorkflow {
		DemoTransaction {
			@Override
			public String xmlAsString() {
				String string = 
						String.join(
								"\n", 
								"<model>",
								"<properties></properties>",
								"<elements elem='txn'>",
								"  <interview>",
								"    <group elem='step1'>",
								"      <currency elem='sendamount' displayprecision='0.01' unit='USD' label='AMOUNT TO SEND'></currency>",
								"      <select elem='speed' label='SELECT DELIVERY SPEED'>",
								"        <item elem='express'>",
								"          <group>",
								"            <label>EXPRESS</label>",
								"            <label elem='expresspromise' format='Funds transferred %s %s'></label>",
								"            <modelproperty name='value'>rate.express</modelproperty>", 
								"          </group>",
								"        </item>",
								"        <item elem='basic'>",
								"          <group>",
								"            <label>BASIC</label>",
								"            <label elem='basicpromise' format='Funds transferred %s %s'></label>",
								"            <modelproperty name='value'>rate.express</modelproperty>", 
								"          </group>",
								"        </item>",
								"        <item elem='basic'></item>",
								"      </select>",
								"      <select elem='receiverbank' label='SELECT BANK' size='6'>",
								"        <item>first bank</item>",
								"        <item>second bank</item>",
								"      </select>",
								"    </group>",
								"    <group elem='step2'>",
								"      <text elem='recipientaccount' label=\"RECIPIENT'S BANK ACCOUNT\"></text>",
								"      <group elem='recipientname' label=\"RECIPIENT'S FULL NAME\">",
								"        <group>",
								"          <text elem='first' label='FIRST'></text>",
								"          <text elem='last' label='LAST'></text>",
								"        </group>",
								"      </group>",
								"      <group elem='recipientaddress' label=\"RECIPIENT'S ADDRESS\">",
								"        <group>",
								"          <text elem='street' label='STREET'></text>",
								"          <text elem='city' label='CITY'></text>",
								"          <select elem='state' label='STATE' size='1'></select>",
								"        </group>",
								"      </group>",
								"      <group elem='recipientphone' label=\"RECIPIENT'S PHONE\">",
								"        <group>",
								"          <text elem='phone' label='PHONE'></text>",
								"          <toggle elem='donotsms' label=\"Don't SMS Recipient\"></toggle>",
								"        </group>",
								"      </group>",
								"      <group label='REASON FOR SENDING'>",
								"          <toggle elem='reason' label='I am sending to friends or family'></toggle>",
								"      </group>",
								"    </group>",
								"    <group label='ABOUT YOURSELF'>",
								"      <group elem='sendername' label='YOUR FULL NAME'>",
								"        <group>",
								"          <text elem='first' label='FIRST'></text>",
								"          <text elem='last' label='LAST'></text>",
								"        </group>",
								"      </group>",
								"      <group elem='senderaddress' label='YOUR HOME ADDRESS'>",
								"        <group>",
								"          <text elem='street' label='STREET'></text>",
								"          <text elem='city' label='CITY'></text>",
								"          <select elem='state' label='STATE' size='1'></select>",
								"          <text elem='zip' label='ZIP'></text>",
								"        </group>",
								"      </group>",
								"      <group elem='senderphone' label='YOUR MOBILE PHONE'>",
								"        <text elem='phone' label='PHONE'></text>",
								"      </group>",
								"      <group>",
								"      <group elem='senderbirth' label='YOUR DATE OF BIRTH'>",
								"        <group>",
								"        <select elem='month' label='MONTH'></select>",
								"        <select elem='day' label='DAY'></select>",
								"        <select elem='year' label='YEAR'></select>",
								"        </group>",
								"      </group>",
								"      </group>",
								"      <group>",
								"        <text elem='ssn' label='LAST FOUR DIGITS OF YOUR SSN'></text>",
								"        <toggle elem='donthavessn' label=\"I don't have an SSN\"></toggle>",
								"      </group>",
								"      <group label='PAYMENT METHOD'>",
								"      <group>",
								"        <tbd></tbd>",
								"      </group>",
								"      </group>",
								"    </group>",
								"    <group>",
								"      <preview></preview>",
								"      <submit></submit>",
								"    </group>",
								"  </interview>",
								"</elements>",
								"<rules>",
								"  <update target='.txn.step1.speed.express.expresspromise' source='?service?'></update>",
								"  <update target='.txn.step1.speed.basic.basicpromise' source='?service?'></update>",
								"  <messageif>",
								"    <areordered>",
								"      <nodeproperty name='value'>.txn.step1.sendamount</nodeproperty>", 
								"      <value>1000</value>",
								"    </areordered>", 
								"    <truemessage target='.txn.step1.sendamount'>$3.99 fee</truemessage>", 
								"    <falsemessage target='.txn.step1.sendamount'>No fees</falsemessage>", 
								"  </messageif>",
								"</rules>",
								"</model>"
								);
				return string ;
			}
		},
		DemoEmpty {
			@Override
			public String xmlAsString() {
				String string = 
						String.join(
								"\n", 
								"<model>",
								"<rules></rules>",
								"<properties></properties>",
								"<elements></elements>",
								"</model>"
								);
				return string ;
			}
		},
		DemoSmall {
			public String xmlAsString() {
				String string = 
						String.join(
								"\n", 
								"<model>", 
								"  <properties>",
								"    <property key='receiving.country'>Mexico</property>", "    <property key='test'>Test</property>",
								"  </properties>", 
								"  <elements>", 
								"    <group elem='name' label='Name'>",
								"      <text elem='first' label='First'></text>", 
								"      <text elem='last' label='Last'></text>",
								"    </group>",
								"    <text elem='secondlast' label='Second Last Name'></text>",
								"    <interview elem='address' label='Adress'>",
								"      <text elem='street' label='Street'></text>", 
								"      <text elem='city' label='City'></text>", 
								"      <text elem='state' label='State'></text>", 
								"      <text elem='zip' label='Zip'></text>", 
								"    </interview>",
								"    <multilinetext elem='note' label='Notes'></multilinetext>", 
								"    <submit>Submit</submit>",
								"  </elements>", "  <rules>", 
								"    <messageif>", 
								"      <areequal>",
								"        <nodeproperty name='textlength'>.name.first</nodeproperty>", 
								"        <value>5</value>",
								"      </areequal>",
								"      <falsemessage target='.name.first'>that name is invalid</falsemessage>",
								"      <truemessage target='.name.first'>okie dokie</truemessage>", 
								"    </messageif>", 
								"    <messageif>",
								"      <areequal>", 
								"        <nodeproperty name='value'>.name.last</nodeproperty>",
								"        <value>santa</value>", 
								"      </areequal>",
								"      <truemessage target='.name.last'>Merry Christmas!</truemessage>", 
								"    </messageif>",
								"    <collectif target='.secondlast'>", 
								"      <areequal>",
								"        <modelproperty name='value'>receiving.country</modelproperty>", 
								"        <value>Mexico</value>",
								"      </areequal>", 
								"    </collectif>", 
								"    <collectif target='submit'>", 
								"      <noneempty>",
								"        <nodeproperty name='value'>.name.last</nodeproperty>",
								"        <nodeproperty name='value'>.name.first</nodeproperty>",
								"        <nodeproperty name='value'>.secondlast</nodeproperty>", 
								"      </noneempty>",
								"    </collectif>", 
								"  </rules>", 
								"</model>");
				return string;
			}

		};

		public WorkflowModel model() {
			return new WorkflowModel(xmlAsString());
		}

		public abstract String xmlAsString();
	}

}
