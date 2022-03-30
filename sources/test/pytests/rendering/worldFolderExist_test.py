import pytest
import os

def testWorldFolderExist():
    exist = False
    if (os.path.isdir("renderingTest\\output\\world") == True):
        exist = True
    
    assert exist == True, 'world folder is not exist in RenderingTest/output folder'
    
    