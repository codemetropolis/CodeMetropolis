from queue import Empty
import pytest
import subprocess
import os
import shutil
import sys
import argparse

parser = argparse.ArgumentParser(description='CodeMetropolis tester')

parser.add_argument('folder', metavar='F', type=str,
                    help='specific name of the folder from the pytests folder (converter/mapping/placing/rendering)')
parser.add_argument('--pyfile', dest='pyfile', type=str,
                    help='a specific pytest file to test from pytests folder')
parser.add_argument('--all', dest='pyfile', type=str,
                    help='run all pytest file from the selected folder')
parser.add_argument('input', metavar='I', type=str,
                    help='input file name')                  

#pelda
# python tester.py --pyfile converter/elementNameMatch_test sanyi

#arguments
args = parser.parse_args()
input = args.input

#pytest_directory_split
pytestFolder = args.folder
pytestPath = 'pytests/' + pytestFolder + "/"
sys.path.append(os.path.join(os.path.dirname(__file__), pytestPath))

#import_pytest
pyFileName = args.pyfile
if (pyFileName is not Empty):
    pyFile = __import__(pyFileName)
if (pyFileName is Empty):
jarFile = pyFile.jar

#run jars
if('converter' in jarFile):
    subprocess.call(['java.exe', '-jar', '../distro/converter-1.4.0.jar', '-t' ,'sourcemeter', '-s' , '' + pyFile.input + input + ".graph"]),
    shutil.move('converterToMapping.xml', '' + pyFile.output)
    pytest.main(['-x', './pytests/' + args.pyfile + '.py'])

if('mapping' in jarFile):
    subprocess.call([r'javapath.bat'])
    shutil.move("mappingToPlacing.xml", '' + pyFile.output)
    pytest.main(['-x', './pytests/' + args.pyfile + '.py'])

if('placing' in jarFile):
    subprocess.call(['java.exe', '-jar', '../distro/placing-1.4.0.jar', '-i', '' + pyFile.input])
    shutil.move("placingToRendering.xml", '' + pyFile.output)
    pytest.main(['-x', './pytests/' + args.pyfile + '.py'])

if('rendering' in jarFile):
    subprocess.call(['java.exe', '-jar', '../distro/rendering-1.4.0.jar', '-i', '' + pyFile.input, '-world', 'world'])
    worldExist = os.path.exists(pyFile.output)
    if (worldExist == True):
        shutil.rmtree(pyFile.output)  
    shutil.move("world", '' + pyFile.output)
    pytest.main(['-x', './pytests/' + args.pyfile + '.py'])




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