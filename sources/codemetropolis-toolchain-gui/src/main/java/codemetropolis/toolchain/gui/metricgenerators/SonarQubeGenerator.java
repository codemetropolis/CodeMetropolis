package codemetropolis.toolchain.gui.metricgenerators;

import codemetropolis.toolchain.converter.control.ConverterType;
import codemetropolis.toolchain.gui.CodeMetropolisGUI;
import codemetropolis.toolchain.gui.components.CMCheckBox;
import codemetropolis.toolchain.gui.components.CMLabel;
import codemetropolis.toolchain.gui.components.CMMetricPanel;
import codemetropolis.toolchain.gui.components.CMPasswordField;
import codemetropolis.toolchain.gui.components.CMTextField;
import codemetropolis.toolhchain.gui.beans.ExecutionOptions;

/**
 * Encapsulated functions for SonarQube code metrics.
 * @author szkabel
 *
 */
public class SonarQubeGenerator extends MetricGenerator {
	
	/**
	 * Constructor and registrate type of converter.
	 */
	public SonarQubeGenerator() {
		super(ConverterType.SONARQUBE,"SonarQube");
	}

	@Override
	public CMMetricPanel getGUIpanel(CodeMetropolisGUI gui) {		
		CMMetricPanel result = new CMMetricPanel(this,gui);
		result.setLayout(null);

	    CMLabel usernameLabel = new CMLabel("Username:");
	    usernameLabel.setBounds(5, 5, 80, 30);
	    CMTextField username = new CMTextField();
	    username.setBounds(90, 5, 370, 30);
	    result.add(usernameLabel);
	    result.add(username);

	    CMLabel passwordLabel = new CMLabel("Password:");
	    passwordLabel.setBounds(5, 40, 80, 30);
	    CMPasswordField password = new CMPasswordField();
	    password.setBounds(90, 40, 370, 30);
	    result.add(passwordLabel);
	    result.add(password);

	    CMLabel projectsLabel = new CMLabel("Projects:");
	    projectsLabel.setBounds(5, 75, 80, 30);
	    CMTextField projects = new CMTextField();
	    projects.setBounds(90, 75, 370, 30);
	    result.add(projectsLabel);
	    result.add(projects);

	    CMCheckBox splitDirs = new CMCheckBox();	    
	    splitDirs.setBounds(5, 110, 20, 30);
	    CMLabel splitDirsLabel = new CMLabel("Split dirs");
	    splitDirsLabel.setBounds(30, 110, 370, 30);
	    result.add(splitDirs);
	    result.add(splitDirsLabel);
	    
	    return result;
	}

	@Override
	public void execute(String dst, ExecutionOptions execOpt) {
		// TODO Auto-generated method stub

	}

}
