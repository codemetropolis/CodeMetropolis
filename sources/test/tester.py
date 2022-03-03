from queue import Empty
import pytest
import subprocess
import os
import shutil
import sys
import argparse

parser = argparse.ArgumentParser(description='CodeMetropolis tester wich generate an output from selected input file and compare it with an excepted file')

parser.add_argument('--pyfile', dest='pyfile', type=str,
                    help='pytest file name without extension')
parser.add_argument('--all', dest='all', type=str,
                    help='all keyword to run all pytest file from the selected folder')                   
#parser.add_argument('folder', metavar='F', type=str,
#                    help='If use --pyfile: the specific folder name in pytests folder where the selected pytest(s) exist\n ')
parser.add_argument('input', metavar='I', type=str,
                    help='Selected input file name from IO/input directory')                  

#pelda
# python tester.py --pyfile elementNameMatch_test converter inputFile
# python tester.py --all all converter inputFile
#python tester.py --pyfile converter/elementNameMatch_test inputFile

#arguments
args = parser.parse_args()
input = args.input
#pytest_directory_split
#pytestFolder = args.folder
def PathSet(PyFilePathArray):
    pytestPath = 'pytests/' + PyFilePathArray + "/"
    sys.path.append(os.path.join(os.path.dirname(__file__), pytestPath))

#import_pytest
#if_--pytest
pyFilePath = args.pyfile
if (pyFilePath is not None):
    if ("/" in pyFilePath):
        PyFilePathArray = pyFilePath.split('/')
        pyFileName = PyFilePathArray[1]        
        PathSet(PyFilePathArray[0])
        pyFile = __import__(pyFileName)
    elif ('"\"' in pyFilePath):
        PyFilePathArray = pyFilePath.split('"\"')
        pyFileName = PyFilePathArray[1]
        PathSet(PyFilePathArray[0])
        pyFile = __import__(pyFileName)
    else :
        print("Not valid path to the pytest file")

#if_--all
"""
if (pyFilePath is None):
    from os import walk
    fileNames = next(walk(pytestPath), (None, None, []))[2]
    fileNameSplit = fileNames[0].split('.')
    fileName = fileNameSplit[0]
    pyFile = __import__(fileName)
"""
jarFile = pyFile.jar

#run_pytest_function
allPytestRun = args.all
def pytestRunner(pyFile):
    pytest.main(['-x', 'pytests/' + pyFile + '.py'])
    print(pyFile)

def allPytestRunCheck(allPytestRunFunc):
    if (allPytestRunFunc is None):
        pytestRunner(args.pyfile)
#    if (allPytestRunFunc is not None):
#        pytest.main(["-x", pytestPath])

#run jars
if('converter' in jarFile):
    subprocess.call(['java.exe', '-jar', '../distro/converter-1.4.0.jar', '-t' ,'sourcemeter', '-s' , '' + pyFile.input + input + ".graph"]),
    shutil.move('converterToMapping.xml', '' + pyFile.output)
    allPytestRunCheck(allPytestRun)


if('mapping' in jarFile):
    javaPathFile = open("javapath.bat","w")
    javaPathLine = """setlocal 
    SET PATH=C:/Program Files/Java/jre1.8.0_301/bin;%PATH%
    java.exe -jar ../distro/mapping-1.4.0.jar -i """ + pyFile.input + input + ".xml -m mapping_IO/inputs/sourcemeter_mapping_example.xml"
    javaPathFile.write(javaPathLine)
    javaPathFile.close();
    subprocess.call([r'javapath.bat'])
    shutil.move("mappingToPlacing.xml", '' + pyFile.output)
    os.remove('javapath.bat')
    allPytestRunCheck(allPytestRun)


if('placing' in jarFile):
    subprocess.call(['java.exe', '-jar', '../distro/placing-1.4.0.jar', '-i', '' + pyFile.input])
    shutil.move("placingToRendering.xml", '' + pyFile.output)
    allPytestRunCheck(allPytestRun)

if('rendering' in jarFile):
    subprocess.call(['java.exe', '-jar', '../distro/rendering-1.4.0.jar', '-i', '' + pyFile.input, '-world', 'world'])
    worldExist = os.path.exists(pyFile.output)
    if (worldExist == True):
        shutil.rmtree(pyFile.output)  
    shutil.move("world", '' + pyFile.output)
    allPytestRunCheck(allPytestRun)




