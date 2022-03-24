import pytest
import os

def testTEMPFolderExist():
    exist = False
    if (os.path.isdir("renderingTest\\output\\world\\TEMP") == True):
        exist = True
    
    assert exist == True, 'TEMP folder is not exist in RenderingTest/output/world folder'
    
    