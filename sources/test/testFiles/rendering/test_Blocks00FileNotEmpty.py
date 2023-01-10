import pytest
import os

jar = 'rendering'

def testBlocks00FileNotEmpty(output):
    
    exist = False
    if (os.stat(output + "\\world\\TEMP\\blocks.0.0.csv").st_size != 0):
        exist = True
    
    assert exist == True, 'test_Blocks00FileNotEmpty: The blocks.0.0.csv file is empty'
    
    