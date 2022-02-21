import pytest
from pathlib import Path

jar = 'converter'
input = 'IO/inputs/'
expected = 'IO/expected/converterToMapping.xml'
output = 'IO/output/converterToMapping.xml'

def testOutputFileExist():
    fileExist = False
    outputFile = Path(output)
    if (outputFile.is_file() == True):
        fileExist = True
        
    assert fileExist == True, "Output file is not exist in the converterTest\output folder"
    
