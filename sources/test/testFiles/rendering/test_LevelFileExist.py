import pytest
import os

jar = 'rendering'

def testLevelFileExist(output):
    exist = False
    if (os.path.isfile(output + "\\world\\level.dat") == True):
        exist = True
    
    assert exist == True, 'test_LevelFileExist: The level.dat file is not exist in ./world folder'
    
    