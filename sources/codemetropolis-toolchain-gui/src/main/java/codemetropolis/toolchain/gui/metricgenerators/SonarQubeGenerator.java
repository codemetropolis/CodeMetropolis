package codemetropolis.toolchain.gui.metricgenerators;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import codemetropolis.toolchain.converter.control.ConverterType;
import codemetropolis.toolchain.gui.components.CMCheckBox;
import codemetropolis.toolchain.gui.components.CMLabel;
import codemetropolis.toolchain.gui.components.CMMetricPanel;
import codemetropolis.toolchain.gui.components.CMPasswordField;
import codemetropolis.toolchain.gui.components.CMTextField;
import codemetropolis.toolchain.gui.utils.GuiUtils;
import codemetropolis.toolhchain.gui.beans.ExecutionOptions;

/**
 * Metric generation settings panel for the SonarQube settings.
 *
 * @author Abel Szkalisity {@literal <SZAVAET.SZE>}
 */
public class SonarQubeGenerator extends CMMetricPanel {

  private static final long serialVersionUID = 1L;

  private CMTextField url;
  private CMTextField username;
  private CMPasswordField password;
  private CMTextField projects;
  private CMCheckBox splitDirs;

  /**
   * Instantiates a SonarQube metric panel.
   */
  public SonarQubeGenerator() {
    setTabTitle("SonarQube");
    setConverterType(ConverterType.SONARQUBE);

    setLayout(null);

    addUrlField();
    addAuthenticationFields();
    addProjectsField();
    addSplitDirsCheckbox();
  }

  /**
   * Adds the SonarQube url field to the panel.
   */
  public void addUrlField() {
    CMLabel label = new CMLabel("URL:", 5, 5, 80, 30);
    url = new CMTextField(90, 5, 370, 30);

    add(label);
    add(url);
  }

  /**
   * Adds the username and password fields to the panel.
   */
  public void addAuthenticationFields() {
    CMLabel usernameLabel = new CMLabel("Username:", 5, 40, 80, 30);
    username = new CMTextField(90, 40, 145, 30);
    CMLabel passwordLabel = new CMLabel("Password:", 240, 40, 80, 30);
    password = new CMPasswordField(315, 40, 145, 30);

    add(usernameLabel);
    add(username);
    add(passwordLabel);
    add(password);
  }

  /**
   * Adds the projects field to the panel.
   */
  public void addProjectsField() {
    CMLabel label = new CMLabel("Projects:", 5, 75, 80, 30);
    projects = new CMTextField(90, 75, 370, 30);

    add(label);
    add(projects);
  }

  /**
   * Adds the split dirs checkbox to the panel.
   */
  public void addSplitDirsCheckbox() {
    splitDirs = new CMCheckBox(5, 110, 20, 30);
    CMLabel label = new CMLabel("Split dirs", 30, 110, 370, 30);

    add(splitDirs);
    add(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void fillFields(ExecutionOptions executionOptions) {
    Map<String, Object> params = executionOptions.getMetricGenerationParams();
    params.put("url", url.getText());
    params.put("username", username.getText());
    params.put("password", password.getPassword());
    params.put("projects", projects.getText());
    params.put("splitDirs", splitDirs.isSelected());

    executionOptions.setConverterType(converterType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validateFields(ExecutionOptions executionOptions) {
    Map<String, Object> params = executionOptions.getMetricGenerationParams();

    try {
      new URL(params.get("url").toString());
    } catch (MalformedURLException e) {
      GuiUtils.showError("Invalid Sonar URL!");
      return false;
    }

    return true;
  }

}
