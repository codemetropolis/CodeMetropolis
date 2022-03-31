import pytest
import os

jar = 'rendering'

def testLevelFileExist(output):
    exist = False
    if (os.path.isfile(output + "\\world\\level.dat") == True):
        exist = True
    
    assert exist == True, 'level.dat is not exist in ./world folder'
    
    