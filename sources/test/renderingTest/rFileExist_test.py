import pytest
import os

def testRFileExist():
    exist = False
    if (os.path.isfile("renderingTest\\output\\world\\region\\r.0.0.mca") == True):
        exist = True
    
    assert exist == True, 'r.0.0.mca is not exist in RenderingTest/output/world/region folder'
    
    