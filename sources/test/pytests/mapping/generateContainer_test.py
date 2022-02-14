from xml.dom import minidom

excpected_file = minidom.parse('mapping\\expected\\mappingToPlacing.xml')
output_file = minidom.parse('mapping\\output\\mappingToPlacing.xml')

class TestClass:
    def test_one(self):
        buildableExpected = excpected_file.getElementsByTagName('buildable')
        buildable = output_file.getElementsByTagName('buildable')
        
        exceptedContainContainer = False
        outputContainContainer = False
        
        if(buildableExpected[0].attributes['type'].value == "container"):
            exceptedContainContainer = True
            
        if(buildable[0].attributes['type'].value == "container"):
            outputContainContainer = True
                                 
        assert exceptedContainContainer == outputContainContainer, 'The numbers of container is not equal in excepted and output file'
        
