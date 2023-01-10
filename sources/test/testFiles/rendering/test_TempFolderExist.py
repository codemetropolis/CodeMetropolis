import pytest
import os

jar = 'rendering'

def testTEMPFolderExist(output):
    exist = False
    if (os.path.isdir(output + "\\world\\TEMP") == True):
        exist = True
    
    assert exist == True, 'test_TempFolderExist: The TEMP folder is not exist in ./world folder'
    
    