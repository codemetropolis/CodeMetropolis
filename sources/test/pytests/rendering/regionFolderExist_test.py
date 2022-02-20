import pytest
import os

def testRegionFolderExist():
    exist = False
    if (os.path.isdir("renderingTest\\output\\world\\region") == True):
        exist = True
    
    assert exist == True, 'region folder is not exist in RenderingTest/output/world folder'
    
    