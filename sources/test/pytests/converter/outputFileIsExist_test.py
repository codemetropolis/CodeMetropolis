import pytest
from pathlib import Path

jar = 'converter'
input = 'inputs/'
expected = 'expected/converterToMapping.xml'
output = 'output/converterToMapping.xml'

def testOutputFileExist():
    fileExist = False
    outputFile = Path(output)
    if (outputFile.is_file() == True):
        fileExist = True
        
    assert fileExist == True, "Output file is not exist"
    
