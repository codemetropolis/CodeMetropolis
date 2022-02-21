from queue import Empty
import pytest
import subprocess
import os
import shutil
import sys
import argparse

parser = argparse.ArgumentParser(description='CodeMetropolis tester')

parser.add_argument('--pyfile', dest='pyfile', type=str,
                    help='run a specific pytest file to test from pytests folder')
parser.add_argument('--all', dest='all', type=str,
                    help='all')                   
parser.add_argument('folder', metavar='F', type=str,
                    help='specific name of the folder from the pytests folder (converter/mapping/placing/rendering)')
parser.add_argument('input', metavar='I', type=str,
                    help='input file name')                  

#pelda
# python tester.py --pyfile elementNameMatch_test converter inputFile
# python tester.py --all all converter inputFile

#arguments
args = parser.parse_args()
input = args.input

#pytest_directory_split
pytestFolder = args.folder
pytestPath = 'pytests/' + pytestFolder + "/"
sys.path.append(os.path.join(os.path.dirname(__file__), pytestPath))

#import_pytest
pyFileName = args.pyfile
if (pyFileName is not None):
    pyFile = __import__(pyFileName)
if (pyFileName is None):
    from os import walk
    fileNames = next(walk(pytestPath), (None, None, []))[2]
    fileNameSplit = fileNames[0].split('.')
    fileName = fileNameSplit[0]
    pyFile = __import__(fileName)

jarFile = pyFile.jar

#run_pytest
allPytestRun = args.all
def pytestRunner(pyFile):
    pytest.main(['-x', pytestPath + pyFile + '.py'])

#run jars
if('converter' in jarFile):
    subprocess.call(['java.exe', '-jar', '../distro/converter-1.4.0.jar', '-t' ,'sourcemeter', '-s' , '' + pyFile.input + input + ".graph"]),
    shutil.move('converterToMapping.xml', '' + pyFile.output)
    if (allPytestRun is None):
        pytestRunner(args.pyfile)
    if (allPytestRun is not None):
        pytest.main(["-x", pytestPath])  


if('mapping' in jarFile):
    subprocess.call([r'javapath.bat'])
    shutil.move("mappingToPlacing.xml", '' + pyFile.output)
    if (allPytestRun is None):
        pytestRunner(args.pyfile)
    if (allPytestRun is not None):
        pytest.main(["-x", pytestPath])  

if('placing' in jarFile):
    subprocess.call(['java.exe', '-jar', '../distro/placing-1.4.0.jar', '-i', '' + pyFile.input])
    shutil.move("placingToRendering.xml", '' + pyFile.output)
    if (allPytestRun is None):
        pytestRunner(args.pyfile)
    if (allPytestRun is not None):
        pytest.main(["-x", pytestPath])  

if('rendering' in jarFile):
    subprocess.call(['java.exe', '-jar', '../distro/rendering-1.4.0.jar', '-i', '' + pyFile.input, '-world', 'world'])
    worldExist = os.path.exists(pyFile.output)
    if (worldExist == True):
        shutil.rmtree(pyFile.output)  
    shutil.move("world", '' + pyFile.output)
    if (allPytestRun is None):
        pytestRunner(args.pyfile)
    if (allPytestRun is not None):
        pytest.main(["-x", pytestPath])  





"""
#use_jars
if ("converterTest" in args.input):
    subprocess.call(['java.exe', '-jar', '../distro/converter-1.4.0.jar', '-t' ,'sourcemeter', '-s' , ''+ args.input])
    shutil.move("converterToMapping.xml", "converterTest/output/converterToMapping.xml")
    pytestFolder = "converterTest/"
    
    
if ("mappingTest" in args.input):
    if("mappingTest\inputs" not in args.input):
        shutil.copy("" + args.input, "mappingTest/inputs/converterToMapping.xml")
    subprocess.call([r'javapath.bat'])
    shutil.move("mappingToPlacing.xml", "mappingTest/output/mappingToPlacing.xml")
    pytestFolder = "mappingTest/"

if ("mappingTest" in args.input):
    subprocess.call(['java.exe', '-jar', '../distro/mapping-1.4.0.jar', '-i' , ''+ args.input, '-m', 'mappingTest/inputs/sourcemeter_mapping_example.xml'])
    #shutil.move("mappingToPlacing.xml", "mappingTest/output/mappingToPlacing.xml")
    pytestFolder = "mappingTest/"



if ("placingTest" in args.input):
    subprocess.call(['java.exe', '-jar', '../distro/placing-1.4.0.jar', '-i', '' + args.input])
    shutil.move("placingToRendering.xml", "placingTest/output/placingToRendering.xml")
    pytestFolder = "PlacingTest/"  


if ("renderingTest" in args.input):
    subprocess.call(['java.exe', '-jar', '../distro/rendering-1.4.0.jar', '-i', '' + args.input, '-world', 'world'])
    worldExist = os.path.exists("renderingTest/output/world")
    if (worldExist == True):
        shutil.rmtree("renderingTest/output/world")  
    shutil.move("world", "renderingTest/output/world")
    pytestFolder = "RenderingTest/"    


#run_pyfiles
if(type(args.pyfile) == str):
    pytest.main(["-x", pytestFolder + args.pyfile])   
else:
    pytest.main(["-x", pytestFolder])


"""