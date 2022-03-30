from queue import Empty
import pytest
import subprocess
import os
import shutil
import sys
import argparse
import os.path
from os import path
from os import walk

parser = argparse.ArgumentParser(description=' A program to define the compare between the expected output and fresly generated output of Codemetropolis')

parser.add_argument('pytest_folder', metavar='F', type=str,
                    help='Run all pytest files from the selected folder')      
parser.add_argument('--pytest_file', dest='pytest_file', type=str,
                    help='Run one selected pytest file from the selected folder')                
parser.add_argument('input', metavar='I', type=str,
                    help='Selected input file')       
parser.add_argument('expected_output', metavar='E', type=str,
                    help='Selected expected output')             

#arguments
arguments = parser.parse_args()
argPytestFolder = arguments.pytest_folder
argPytestFile = arguments.pytest_file
argInput = arguments.input
argExpectedoutput = arguments.expected_output
pathToPytestFile = argPytestFolder + "/" + str(argPytestFile)

#WARNINGS_function
def warning(argPytestFolder, argPytestFile, argInput, argExpectedoutput):
    #paths_and files_exist
    if (path.exists(argPytestFolder) == False):
        print("Warning: Nincs ilyen nevű pytest mappa vagy hibás az elérési útvonal!")
        return -1
    if (str(argPytestFile) != "None" and path.exists(pathToPytestFile) == False):
        print("Warning: Nincs ilyen nevű pytest fájl vagy hibás az elérési útvonal!")
        return -1
    if (path.exists(argInput) == False):
        print("Warning: Nincs ilyen nevű input fájl vagy hibás az elérési útvonal!")
        return -1
    if (path.exists(argExpectedoutput) == False):
        print("Warning: Nincs ilyen nevű expected output fájl vagy hibás az elérési útvonal!")
        return -1 
    #directory_empty
    if (len(os.listdir(argPytestFolder)) == 0):
        print("Warning: Directory is empty")
        return -1
    #no_py_file_in_directory
    directoryFiles = next(walk(argPytestFolder), (None, None, []))[2]
    if (".py" not in str(directoryFiles)):
        print("Warning: No pytests on the selected folder")
        return -1



#._splitter_function
def dotSplitter(fileNames):
    fileNamesSplit = fileNames.split('.')
    fileName = fileNamesSplit[0]
    return fileName



#import_pytest_file_function
def randomPytestFileSelect(argPytestFolder):
    sys.path.append(os.path.join(os.path.dirname(__file__), argPytestFolder))
    fileNames = next(walk(argPytestFolder), (None, None, []))[2]
    fileName = dotSplitter(fileNames[0])
    return fileName


    

#MAIN
while True:
    #warnings
    if (warning(argPytestFolder, argPytestFile, argInput, argExpectedoutput) == -1):
        break

    #import_pytest_file
    if (str(argPytestFile) != "None"):
        sys.path.append(os.path.join(os.path.dirname(__file__), argPytestFolder))
        importPytestFile = __import__(dotSplitter(argPytestFile))
    if (str(argPytestFile) == "None"):
        importPytestFile = __import__(randomPytestFileSelect(argPytestFolder))

    #Converter_jars_runner
    selectedJar = importPytestFile.jar

    #converter
    if('converter' in selectedJar):
        subprocess.call(['java.exe', '-jar', '../distro/converter-1.4.0.jar', '-t' ,'sourcemeter', '-s' , '' + argInput]),
        shutil.move('converterToMapping.xml', '' + importPytestFile.output)
        pytest.main(["-x", pathToPytestFile, '--expected', argExpectedoutput])
        #allPytestRunCheck(allPytestRun)

        #TODO
        #megcsinálni, hogy akkor is menjen a pytest dolog, amikor csak mappát adunk meg. 
        #átírni a pytest fájlokban az expected részt
        #kiszervezni a jar futtatásokat is functionbe.


    print("Végigfutott") 
    break




