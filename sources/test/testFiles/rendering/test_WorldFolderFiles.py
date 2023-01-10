import pytest
import os

jar = 'rendering'

def directories(filePath, worldFilesList):
    for root, dirs, files in os.walk(filePath, topdown=False):
       for name in files:
          worldFilesList.append(os.path.join(root, name))
    return worldFilesList

def testWorldFolderFiles(expected, output):

    outputFilePath = output + "/world"
    expectedFilePath = expected

    expectedworldFilesList = []
    outputworldFilesList = []
    passCounter = -1
    
    directories(expectedFilePath, expectedworldFilesList)
    directories(outputFilePath, outputworldFilesList)  
    try:
        for i in range(len(expectedworldFilesList)):
            expectedworldFilesList[i] = expectedworldFilesList[i].replace(expectedFilePath + "\\","")
            outputworldFilesList[i] = outputworldFilesList[i].replace(outputFilePath + "\\","")
            if (expectedworldFilesList[i] in outputworldFilesList):
                passCounter = passCounter + 1
    
    except IndexError as exception:
        pytest.fail("The output world folder does not contain all the expected files.")
        
    expectedworldFilesListLength = len(expectedworldFilesList)
    
    if (expectedworldFilesListLength == 0 and outputworldFilesList != 0):
        expectedworldFilesList = " "
        
    assert len(expectedworldFilesList)-1 == passCounter, "The output world folder does not contain the expected files."
    