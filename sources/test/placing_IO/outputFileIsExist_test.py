import pytest
from pathlib import Path

def testOutputFileExist():
    fileExist = False
    outputFile = Path('placingTest\\output\\placingToRendering.xml')
    if (outputFile.is_file() == True):
        fileExist = True
        
    assert fileExist == True, "Output file is not exist in the PlacingTest/output directory"
    
