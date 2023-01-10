import pytest
import os

jar = 'rendering'

def testWorldFolderExist(output):
    exist = False
    if (os.path.isdir(output + "\\world") == True):
        exist = True
    
    assert exist == True, 'test_WorldFolderExist: The world folder is not exist'
    
    