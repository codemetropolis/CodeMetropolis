import pytest
from pathlib import Path

jar = 'placing'

def testOutputFileExist(output):
    outputFullPath = output + "/placingToRendering.xml"
    fileExist = False
    outputFile = Path(outputFullPath)
    if (outputFile.is_file() == True):
        fileExist = True
        
    assert fileExist == True, "Output file is not exist in the PlacingTest/output directory"
    
