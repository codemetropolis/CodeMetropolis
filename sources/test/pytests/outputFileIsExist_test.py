import pytest
from pathlib import Path

jar = "converter"
input = "converter_IO\inputs\input.graph"
expected = "converter_IO\expected\converterToMapping.xml"
output = "converter_IO\output\converterToMapping.xml"

def testOutputFileExist():
    fileExist = False
    outputFile = Path(output)
    if (outputFile.is_file() == True):
        fileExist = True
        
    assert fileExist == True, "Output file is not exist in the converterTest\output folder"
    
