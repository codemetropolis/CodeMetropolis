import pytest
import os

jar = 'rendering'

def directories(filePath, worldFoldersList):
    for root, dirs, files in os.walk(filePath, topdown=False):
       for name in dirs:
          worldFoldersList.append(os.path.join(root, name))
    return worldFoldersList

def testWorldFolderFolders(expected, output):

    outputFilePath = output + "/world"
    expectedFilePath = expected

    expectedWorldFoldersList = []
    outputWorldFoldersList = []
    passCounter = -1
    
    directories(expectedFilePath, expectedWorldFoldersList)
    directories(outputFilePath, outputWorldFoldersList)  
    try:
        for i in range(len(expectedWorldFoldersList)):
            expectedWorldFoldersList[i] = expectedWorldFoldersList[i].replace(expectedFilePath + "\\","")
            outputWorldFoldersList[i] = outputWorldFoldersList[i].replace(outputFilePath + "\\","")
            if (expectedWorldFoldersList[i] in outputWorldFoldersList):
                passCounter = passCounter + 1
    
    except IndexError as exception:
        pytest.fail("The output world folder does not contain all the expected folders.")
        
    expectedWorldFoldersListLength = len(expectedWorldFoldersList)
    
    if (expectedWorldFoldersListLength == 0 and outputWorldFoldersList != 0):
        expectedWorldFoldersList = " "
        
    assert len(expectedWorldFoldersList)-1 == passCounter, "The output world folder does not contain the expected folders."
    