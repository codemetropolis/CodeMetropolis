package codemetropolis.toolchain.gui.beans;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import codemetropolis.toolchain.converter.control.ConverterType;
import codemetropolis.toolchain.placing.layout.LayoutAlgorithm;

/**
 * Contains the parameters required for running the CodeMetropolis toolchain on a given project.
 *
 * @author Abel Szkalisity {@literal <SZAVAET.SZE>}
 */
public class ExecutionOptions {

  // General
  private String projectName;

  // Converter tool
  private ConverterType converterType;
  private Map<String, Object> metricGenerationParams;

  // Mapping tool
  private File mappingXml;
  private float scale;
  private boolean validate;

  // Placing tool
  private LayoutAlgorithm layoutAlgorithm;
  private boolean showMap;

  // Rendering tool
  private File minecraftRoot;

  /**
   * Constructs an {@link ExecutionOptions} instance with default values.
   */
  public ExecutionOptions() {
    this.converterType = ConverterType.SOURCEMETER;
    this.metricGenerationParams = new HashMap<String, Object>();
    this.scale = 1.0f;
    this.validate = false;
    this.layoutAlgorithm = LayoutAlgorithm.PACK;
    this.showMap = false;
  }

  public String getProjectName() {
    return projectName;
  }

  public ConverterType getConverterType() {
    return converterType;
  }

  public Map<String, Object> getMetricGenerationParams() {
    return metricGenerationParams;
  }

  public File getMappingXml() {
    return mappingXml;
  }

  public float getScale() {
    return scale;
  }

  public boolean isValidate() {
    return validate;
  }

  public LayoutAlgorithm getLayoutAlgorithm() {
    return layoutAlgorithm;
  }

  public boolean isShowMap() {
    return showMap;
  }

  public File getMinecraftRoot() {
    return minecraftRoot;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  public void setConverterType(ConverterType converterType) {
    this.converterType = converterType;
  }

  public void setMetricGenerationParams(Map<String, Object> metricGenerationParams) {
    this.metricGenerationParams = metricGenerationParams;
  }

  public void setMappingXml(File mappingXml) {
    this.mappingXml = mappingXml;
  }

  public void setScale(float scale) {
    this.scale = scale;
  }

  public void setValidate(boolean validate) {
    this.validate = validate;
  }

  public void setLayoutAlgorithm(LayoutAlgorithm layoutAlgorithm) {
    this.layoutAlgorithm = layoutAlgorithm;
  }

  public void setShowMap(boolean showMap) {
    this.showMap = showMap;
  }

  public void setMinecraftRoot(File minecraftRoot) {
    this.minecraftRoot = minecraftRoot;
  }

}
