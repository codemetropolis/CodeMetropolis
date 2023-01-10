import pytest
import os

jar = 'rendering'

def testRegion00FileExist(output):
    exist = False
    if (os.path.isfile(output + "\\world\\region\\r.0.0.mca") == True):
        exist = True
    
    assert exist == True, 'test_Region00FileExist: The r.0.0.mca file is not exist in ./world/region folder'
    
    