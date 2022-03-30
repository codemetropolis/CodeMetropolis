import pytest
import os

def testLevelFileExist():
    exist = False
    if (os.path.isfile("renderingTest\\output\\world\\level.dat") == True):
        exist = True
    
    assert exist == True, 'level.dat is not exist in RenderingTest/output/world folder'
    
    