import os

def testRFileExist():
    exist = False
    if (os.path.isfile("renderingTest\\output\\world\\TEMP\\blocks.0.0.csv") == True):
        exist = True
    
    assert exist == True, 'blocks.0.0.csv is not exist in RenderingTest/output/world/TEMP folder'
    
    