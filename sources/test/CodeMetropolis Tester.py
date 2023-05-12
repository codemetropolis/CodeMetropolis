from ast import And
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

parser = argparse.ArgumentParser(description='This is a Python script that uses command line arguments to perform regression tests by comparing expected output files with CodeMetropolis output files.')

parser.add_argument('test_path', metavar='F', type=str,
                    help='Specifies the path of one or multiple test files. Required argument!')  

parser.add_argument('input_path', metavar='I', type=str,
                    help='Specifies the path of the input files used for testing. Required argument!')  

parser.add_argument('expected_output_path', metavar='E', type=str,
                    help='Specifies the path of the expected output files. Required argument!') 

parser.add_argument('generated_output_path', metavar='O', type=str,
                    help='Specifies the path of the generated output files by tools. Required argument!')  

parser.add_argument('--mapping_file_path', dest='mapping_file_path', type=str,
                    help='Specifies the path of the mapping file. Required argument, if you test mapping tool output!') 

parser.add_argument('--type', dest='types', type=str,
                    help='Specifies the type for tool. Required argument, if you test converter or rendering tool output!')  
                    
parser.add_argument('--parameters', dest='parameters', type=str,
                    help='Optional parameters. You can use it if supported by the tool')                 

#arguments_and_variables
arguments = parser.parse_args()
argPytestFolder = arguments.test_path
argPytestFile = None
pathToPytestFile = None
argInput = arguments.input_path
mapping_path = arguments.mapping_file_path
argExpectedoutput = arguments.expected_output_path
generatedOutputPath = arguments.generated_output_path
parameters = arguments.parameters
types = arguments.types
splittedPathSlash = argPytestFolder.split('/')
splittedPathBackslash = argPytestFolder.split('\\')

if '.py' in str(splittedPathSlash[len(splittedPathSlash)-1]) and len(splittedPathSlash)!=1:
    argPytestFile = splittedPathSlash[len(splittedPathSlash)-1]
    pathToPytestFile = argPytestFolder
    argPytestFolder = ""
    for i in range(len(splittedPathSlash)-1):
        argPytestFolder = argPytestFolder + splittedPathSlash[i] + '/'

if '.py' in str(splittedPathBackslash[len(splittedPathBackslash)-1]) and len(splittedPathBackslash)!=1:
    argPytestFile = splittedPathBackslash[len(splittedPathBackslash)-1]
    pathToPytestFile = argPytestFolder

    argPytestFolder = ""
    for i in range(len(splittedPathBackslash)-1):
        argPytestFolder = argPytestFolder + splittedPathBackslash[i] + '\\'



#FUNCTIONS
#WARNINGS_function
def warning(argPytestFolder, argPytestFile, argInput, argExpectedoutput, generatedOutputPath, selectedJar):
    #converter_type_required
    if (types is None and "converter" in selectedJar):
        print("Error: Converter tool require type argument!")
        return -1
    #mapping_file_required
    if (mapping_path is None and "mapping" in selectedJar):
        print("Error: Mapping tool require mapping_file_path argument!")
        return -1
    #paths_and files_not_exist
    if (path.exists(argPytestFolder) == False):
        print("Warning: There is no folder with this name or the path is invalid!")
        return -1
    if (str(argPytestFile) != "None" and path.exists(pathToPytestFile) == False):
        print("Warning: There is no pytest file with this name or the path is invalid!")
        return -1
    if (path.exists(argInput) == False):
        print("Warning: There is no file with this name or the path is invalid!")
        return -1
    if (path.exists(argExpectedoutput) == False):
        print("Warning: There is no expected output file with this name or the path is invalid!")
        return -1 
    #directory_empty
    if (len(os.listdir(argPytestFolder)) == 0):
        print("Warning: Directory is empty!")
        return -1
    #no_py_file_in_directory
    directoryFiles = next(walk(argPytestFolder), (None, None, []))[2]
    if (".py" not in str(directoryFiles)):
        print("Warning: No pytests on the selected folder!")
        return -1
    #generatedOutputPath_not_exist
    if (generatedOutputPath != None and path.exists(generatedOutputPath) == False):
        print("Warning: There is no folder with this name or the path is invalid!")
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

#run_pytests_function
def runPytests(argPytestFile, argPytestFolder, pathToPytestFile, argExpectedoutput, generatedOutputPath):
    if (argPytestFile == None):
        pytest.main([argPytestFolder, '--expected', argExpectedoutput, '--output', generatedOutputPath, "-s"])
        
    if (argPytestFile != None):
        pytest.main([pathToPytestFile, '--expected', argExpectedoutput, '--output', generatedOutputPath, "-s"])
    
#jars_functions
#converter_jar_function
def converterJar(argInput, types, parameters, generatedOutputPath):
    if parameters == "None" or parameters is None:
        subprocess.call(['java.exe', '-jar', '../distro/converter-1.4.0.jar', '-t', '' + types , '-s' , '' + argInput]),
    else:
        subprocess.call(['java.exe', '-jar', '../distro/converter-1.4.0.jar', '-t', '' + types , '-s' , '' + argInput , '-p' , '' + parameters]),
    #generatedOutputFolderExist(generatedOutputPath)
    shutil.move(os.path.join('./', 'converterToMapping.xml'), os.path.join(generatedOutputPath, 'converterToMapping.xml'))   
           
#mapping_jar_function
def mappingJar(argInput, mapping_path, generatedOutputPath):
    javaPathFile = open("javapath.bat","w")
    javaPathContent = """setlocal 
    SET PATH=C:/Program Files/Java/jre1.8.0_301/bin;%PATH%
    java.exe -jar ../distro/mapping-1.4.0.jar -i """ + argInput + " -m " + mapping_path
    javaPathFile.write(javaPathContent)
    javaPathFile.close()
    subprocess.call([r'javapath.bat'])
    #shutil.move("mappingToPlacing.xml", '' + generatedOutputPath)
    #generatedOutputFolderExist(generatedOutputPath)
    shutil.move(os.path.join('./', 'mappingToPlacing.xml'), os.path.join(generatedOutputPath, 'mappingToPlacing.xml'))
    os.remove('javapath.bat')

#placing_jar_function
def placingJar(argInput, generatedOutputPath):
    subprocess.call(['java.exe', '-jar', '../distro/placing-1.4.0.jar', '-i', '' + argInput])
    #shutil.move("placingToRendering.xml", '' + generatedOutputPath)
    #generatedOutputFolderExist(generatedOutputPath)
    shutil.move(os.path.join('./', 'placingToRendering.xml'), os.path.join(generatedOutputPath, 'placingToRendering.xml'))

#rendering_jar_function
def renderingJar(argInput, types, generatedOutputPath):
    subprocess.call(['java.exe', '-jar', '../distro/rendering-1.4.0.jar', '-i', '' + argInput, '-world', 'world'])
    worldExist = os.path.exists(generatedOutputPath)
    if (worldExist == True):
        shutil.rmtree(generatedOutputPath)  

    shutil.move(os.path.join('./', 'world'), os.path.join(generatedOutputPath, 'world'))
    runPytests(argPytestFile, argPytestFolder, pathToPytestFile, argExpectedoutput, generatedOutputPath)

#MAIN
while True:

    #import_pytest_file
    if (str(argPytestFile) != "None"):
        sys.path.append(os.path.join(os.path.dirname(__file__), argPytestFolder))
        importPytestFile = __import__(dotSplitter(argPytestFile))
        
    if (str(argPytestFile) == "None"):
        importPytestFile = __import__(randomPytestFileSelect(argPytestFolder))

    #Converter_jars_runner
    selectedJar = importPytestFile.jar

    #warnings
    if (warning(argPytestFolder, argPytestFile, argInput, argExpectedoutput, generatedOutputPath, selectedJar) == -1):
        break

    #converter
    if('converter' in selectedJar):
        converterJar(argInput, types, parameters, generatedOutputPath)
        runPytests(argPytestFile, argPytestFolder, pathToPytestFile, argExpectedoutput, generatedOutputPath)

    #mapping
    if('mapping' in selectedJar):
        mappingJar(argInput, mapping_path, generatedOutputPath)
        runPytests(argPytestFile, argPytestFolder, pathToPytestFile, argExpectedoutput, generatedOutputPath)
    
    #placing
    if('placing' in selectedJar):
        placingJar(argInput, generatedOutputPath)
        runPytests(argPytestFile, argPytestFolder, pathToPytestFile, argExpectedoutput, generatedOutputPath)

    #rendering
    if('rendering' in selectedJar):
        renderingJar(argInput, types, generatedOutputPath)
        
    break




