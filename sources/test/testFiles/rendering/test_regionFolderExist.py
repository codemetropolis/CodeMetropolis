import pytest
import os

jar = 'rendering'

def testRegionFolderExist(output):
    exist = False
    if (os.path.isdir(output + "\\world\\region") == True):
        exist = True
    
    assert exist == True, 'test_regionFolderExist: The region folder is not exist in ./world folder'
    
    