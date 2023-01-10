import pytest
import os

jar = 'rendering'

def testBlocks00FileNotEmpty(output):
    
    exist = False
    if (os.stat(output + "\\world\\region\\r.0.0.mca").st_size != 0):
        exist = True
    
    assert exist == True, 'test_Region00FileNotEmpty: The r.0.0.mca file is empty'
    
    