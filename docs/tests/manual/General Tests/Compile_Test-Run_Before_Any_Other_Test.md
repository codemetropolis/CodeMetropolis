
# Check if project is compilable on issue branch

### Purpose
This is a general test that needs to be run before any other kind of test is run when testing an issue's solution. When the tester selects a new issue to test its solution through different kinds of tests this needs to be the first one to be done, in order to see if the project on the isssue's branch can still be complied and no error will be given back regarding this matter.

### Pre-requisites	
- Java runtime 22

### Steps
1. Create an arbitrary folder on your PC and navigate into it
2. Open git bash by right clicking in the folder and choosing the **Git Bash Here** option
3. Type this into the command line
	```cmd
	git clone https://github.com/codemetropolis/CodeMetropolis.git
	```
4. Navigate to the CodeMetropolis\sources folder and open a git bash command line the same as before
5. Run this in the command line
	```cmd
	mvn clean package
	```

### Expected Result
The maven command creates the binary files and no errors are written on the command line. Files to be created: **target** named folders in
- sources/commons
- sources/gui
- sources/integration
- sources/toolchain 
- Toolchains subfolders: converter, mapping, placing, rendering