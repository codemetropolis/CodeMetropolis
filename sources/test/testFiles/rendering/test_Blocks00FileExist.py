import pytest
import os

jar = 'rendering'

def testBlocks00FileExist(output):
    
    exist = False
    if (os.path.isfile(output + "\\world\\TEMP\\blocks.0.0.csv") == True):
        exist = True
    
    assert exist == True, 'test_Blocks00FileExist: The blocks.0.0.csv file is not exist in ./world/TEMP folder'
    
    