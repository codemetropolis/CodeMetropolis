import pytest
import os

jar = 'rendering'

def testWorldFolderExist(output):
    exist = False
    if (os.path.isdir(output + "\\world") == True):
        exist = True
    
    assert exist == True, 'Generated output world folder is not exist!'
    
    