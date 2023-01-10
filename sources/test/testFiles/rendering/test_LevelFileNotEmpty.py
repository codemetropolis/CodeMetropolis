import pytest
import os

jar = 'rendering'

def testBlocks00FileNotEmpty(output):
    
    exist = False
    if (os.stat(output + "\\world\\level.dat").st_size != 0):
        exist = True
    
    assert exist == True, 'test_LevelFileNotEmpty: The level.dat file is empty'
    
    