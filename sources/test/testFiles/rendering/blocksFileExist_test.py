import os

jar = 'rendering'

def testRFileExist(output):
    
    exist = False
    if (os.path.isfile(output + "\\world\\TEMP\\blocks.0.0.csv") == True):
        exist = True
    
    assert exist == True, 'blocks.0.0.csv is not exist in ./world/TEMP folder'
    
    