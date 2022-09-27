import pytest
from pathlib import Path

jar = 'mapping'

def testOutputFileExist(output):
    outputFullPath = output + "/mappingToPlacing.xml"
    fileExist = False
    outputFile = Path(outputFullPath)
    if (outputFile.is_file() == True):
        fileExist = True
        
    assert fileExist == True, "Output file is not exist in the output directory"
    
