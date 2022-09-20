import pytest
from pathlib import Path

jar = 'converter'

def testOutputFileExist(output):
    outputFullPath = output + "/converterToMapping.xml"
    fileExist = False
    outputFile = Path(outputFullPath)
    if (outputFile.is_file() == True):
        fileExist = True
        
    assert fileExist == True, "outputFileExist_test: Output file not exist"
    
