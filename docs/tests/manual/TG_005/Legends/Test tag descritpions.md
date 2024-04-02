# Component tags

#component_converter - Tests under this tag focus on the functionality and reliability of the CodeMetropolis converter component. This includes verifying that the converter accurately translates source code or other inputs into the CodeMetropolis model format, ensuring compatibility and correctness in the conversion process.

#component_converter_SonarQube - This tag is specific to tests that examine the integration and data conversion between SonarQube and CodeMetropolis. Tests should check if metrics, issues, and other data from SonarQube are correctly interpreted and represented within the CodeMetropolis environment after conversion.

#component_converter_SourceMeter - Tests tagged with this focus on the integration and conversion process between SourceMeter and CodeMetropolis. It involves ensuring that the analysis results from SourceMeter, such as code complexity and quality metrics, are accurately converted and mapped within the CodeMetropolis visualization tool.

#component_mapping - These tests evaluate the component mapping capabilities of CodeMetropolis, ensuring that elements from the source data (e.g., code structures, metrics) are correctly mapped to visual components in the CodeMetropolis model. This includes validating the accuracy and meaningfulness of the visual representation.

#component_placing - This tag applies to tests that verify the algorithms and logic used for placing components within the CodeMetropolis visualization. Tests should assess whether components are placed logically and usefully, facilitating an intuitive understanding of the source code's structure and metrics.

#component_rendering - Tests with this tag focus on the rendering performance and quality within CodeMetropolis. This includes checking for visual accuracy, rendering speed, and the ability to handle large datasets without significant performance degradation.

#component_block-modifier - These tests are concerned with the block modifier component of CodeMetropolis, which allows users to modify the visual representation of code elements. Tests should cover the flexibility, accuracy, and usability of these modifications, ensuring they reflect the intended changes without errors.

#component_GUI - Tests under this tag are dedicated to assessing the graphical user interface of CodeMetropolis. This involves evaluating the usability, responsiveness, and intuitiveness of the interface, as well as the effectiveness of the GUI in facilitating user interaction with the CodeMetropolis toolset.

# File format tags

#testFile-format_IXML - Focuses on tests using IXML (intermediate XML format) files, verifying the system's handling and interpretation of IXML specifics. This file format is used among (most of) the CodeMetropolis tools to transfer information. Those are XML files containing the buildings.

#testFile-format_CDF - Focuses on tests using CDF (Common Data Format) files, verifying the system's handling and interpretation of IXML specifics. This file format is utilized to input information from external systems (like SourceMeter and SonarQube) into the CodeMetropolis toolkit. This is the output of the #component_converter  tool.

#testFile-format_mapping - Focuses on tests using mapping configuration files, verifying the system's handling and interpretation of IXML specifics. They used to set up the mapping between data points and visual properties. This is the configuration file of the #component_mapping  tool

#testFile-format_graph - Covers tests where the input is in a graph format, checking the system's ability to accurately read, interpret, and utilize graph data structures.

# Pull request tags

#pullRequest_304 - Tests associated with changes or new features introduced in pull request 304.

#pullRequest_301 - Tests associated with changes or new features introduced in pull request 301.

#pullRequest_225 - Tests associated with changes or new features introduced in pull request 225.

#pullRequest_203 - Tests associated with changes or new features introduced in pull request 203.

#pullRequest_199 - Tests associated with changes or new features introduced in pull request 199.

#pullRequest_195 - Tests associated with changes or new features introduced in pull request 195.

#pullRequest_176 - Tests associated with changes or new features introduced in pull request 176.

#pullRequest_163 - Tests associated with changes or new features introduced in pull request 163.

# Type tags
#triage

#type_bug - Tests designed to identify, replicate, or verify the fix of a bug in the system.

#type_feature - Tests that validate the functionality, usability, and integration of new features.

#type_refactoring - Tests ensuring that refactoring efforts have not altered the existing functionality or introduced new issues.

#type_performance - 

# Category tags

#category_input - Tests focused on how the system handles, validates, and processes input data.

#category_output - Tests that evaluate the accuracy, completeness, and format of the system's outputs.

# Status tags

#status_not_implemented_functionality - This tag is used for tests that are written ahead of the corresponding functionality being implemented. It’s an embodiment of the TDD principle where tests guide the development. Functionality are not implemented on any branch.

#status_not_merged_functionality - Indicates tests for functionality that has been developed and tested but not yet merged into the main branch, possibly due to pending review or integration issues.

>Regularly review these tags during your development planning meetings to prioritize and assign development tasks. Ensure that tests with this tag are addressed promptly to keep the development cycle moving.

#status_depricated - Marks tests that are no longer relevant due to changes in the application's requirements or architecture.

>Periodically review and clean up tests marked as deprecated. This is a good practice to avoid confusion and ensure that your test suite remains manageable and focused on current functionalities.

#status_draft - Used for tests that are in an incomplete state or are placeholders for future test development.

>Keep an eye on draft tests to ensure they are either completed or updated as the related development tasks progress. Draft tests should regularly be reviewed to decide if they should be completed, repurposed, or removed.

