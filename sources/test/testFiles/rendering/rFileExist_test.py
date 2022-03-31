import pytest
import os

jar = 'rendering'

def testRFileExist(output):
    exist = False
    if (os.path.isfile(output + "\\world\\region\\r.0.0.mca") == True):
        exist = True
    
    assert exist == True, 'r.0.0.mca is not exist in ./world/region folder'
    
    