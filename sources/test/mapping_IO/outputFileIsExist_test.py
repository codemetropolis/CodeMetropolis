import pytest
from pathlib import Path

jar = 'converter'
input = 'IO/inputs/'
expected = 'IO/expected/mappingToPlacing.xml'
output = 'IO/output/mappingToPlacing.xml'

def testOutputFileExist():
    fileExist = False
    outputFile = Path(output)
    if (outputFile.is_file() == True):
        fileExist = True
        
    assert fileExist == True, "Output file is not exist in the output directory"
    
