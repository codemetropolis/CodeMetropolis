# Check issue no. #195's solution if it gives back an error on capitalized -L or --LAYOUT switch

## Tags
#pullRequest_195 #component_placing #category_input

_Related issue:_ https://github.com/codemetropolis/CodeMetropolis/issues/195 <br>
_Related branch:_ https://github.com/codemetropolis/CodeMetropolis/tree/195-placing-with-empty-layout-parameter-is-not-handled-correctly

### Purpose
The purpose of this test is to see if the solution committed to the above issue gives back an error when trying to use layout parameter as *-L* or *-LAYOUT*.

### Test File
- [[defaultValidCodeMetroMappingToPlacing.xml]]

### Pre-requisites
- Java runtime 22
- Command line opened in the folder of placing-1.5.0.jar
- A *defaultValidCodeMetroMappingToPlacing.xml* file exists in the placing-1.5.0.jar's folder. (Has to be a valid file, which was produced by running converter-1.5.0.jar with the *converterToMapping.xml* as input)
  
### Steps
1. Run the following command in the folder of placing-1.5.0.jar:
```cmd
	java -jar placing-1.5.0.jar -i defaultValidCodeMetroMappingToPlacing.xml -L pack
```
or
```cmd
	java -jar placing-1.5.0.jar -i defaultValidCodeMetroMappingToPlacing.xml --LAYOUT pack
```

### Expected Result
The tool gives back a warning or error and the program will not finish running.