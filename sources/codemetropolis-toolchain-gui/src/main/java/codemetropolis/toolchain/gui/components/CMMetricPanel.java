package codemetropolis.toolchain.gui.components;

import javax.swing.JPanel;

import codemetropolis.toolchain.converter.control.ConverterType;
import codemetropolis.toolhchain.gui.beans.ExecutionOptions;

/**
 * Extension to JPanel, as we need to store to which metric generator (SourceMeter, SonarQube) this panel provides
 * options for. Can also validate the fields and fill them into a gievn {@link ExecutionOptions} instance.
 *
 * @author Abel Szkalisity {@literal <SZAVAET.SZE>}
 */
public abstract class CMMetricPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  protected String tabTitle;
  protected ConverterType converterType;

  /**
   * Fills the given {@link ExecutionOptions} instance with data from the panel.
   *
   * @param executionOptions The target {@link ExecutionOptions} instance.
   */
  public abstract void fillFields(ExecutionOptions executionOptions);

  /**
   * Validates the given {@link ExecutionOptions} instance.
   *
   * @param executionOptions The instance to validate.
   * @return True, if the options related to this panel are existent and valid, false otherwise.
   */
  public abstract boolean validateFields(ExecutionOptions executionOptions);

  public String getTabTitle() {
    return tabTitle;
  }

  public ConverterType getConverterType() {
    return converterType;
  }

  public void setTabTitle(String tabTitle) {
    this.tabTitle = tabTitle;
  }

  public void setConverterType(ConverterType converterType) {
    this.converterType = converterType;
  }

}
