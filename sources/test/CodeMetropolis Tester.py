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

#args
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

#arguments and variables
arguments = parser.parse_args()

test_file_path = arguments.test_path
test_file = None
modified_test_file_path = None

input_path = arguments.input_path
expected_output_path = arguments.expected_output_path
output_path = arguments.generated_output_path
mapping_path = arguments.mapping_file_path
types = arguments.types
parameters = arguments.parameters

splitted_test_file_path = test_file_path.split('/')
splitted_test_file_backslash = test_file_path.split('\\')

if '.py' in str(splitted_test_file_path[len(splitted_test_file_path)-1]) and len(splitted_test_file_path)!=1:
    test_file = splitted_test_file_path[len(splitted_test_file_path)-1]
    modified_test_file_path = test_file_path
    test_file_path = ""
    for i in range(len(splitted_test_file_path)-1):
        test_file_path = test_file_path + splitted_test_file_path[i] + '/'

if '.py' in str(splitted_test_file_backslash[len(splitted_test_file_backslash)-1]) and len(splitted_test_file_backslash)!=1:
    test_file = splitted_test_file_backslash[len(splitted_test_file_backslash)-1]
    modified_test_file_path = test_file_path

    test_file_path = ""
    for i in range(len(splitted_test_file_backslash)-1):
        test_file_path = test_file_path + splitted_test_file_backslash[i] + '\\'


#warning functions
def warning(test_file_path, test_file, input_path, expected_output_path, output_path, selected_tool):
    error_messages = {
        "Invalid test file path!": not path.exists(test_file_path),
        "Invalid test file!": str(test_file) != "None" and not path.exists(modified_test_file_path),
        "Invalid input file path!": not path.exists(input_path),
        "Invalid expected output file path!": not path.exists(expected_output_path),
        "Test file directory is empty!": len(os.listdir(test_file_path)) == 0,
        "No test on the selected folder!": ".py" not in str(next(walk(test_file_path), (None, None, []))[2]),
        "Invalid output path!": output_path is not None and not path.exists(output_path),
        "Converter tool requires type argument!": types is None and "converter" in selected_tool,
        "Mapping tool requires mapping_file_path argument!": mapping_path is None and "mapping" in selected_tool
    }

    for error, condition in error_messages.items():
        if condition:
            print(f"Error: {error}")
            return -1
    
    return 0

#._splitter function
def splitter(fileNames):
    fileNamesSplit = fileNames.split('.')
    fileName = fileNamesSplit[0]
    return fileName

#import pytest file function
def random_test_file_select(test_file_path):
    sys.path.append(os.path.join(os.path.dirname(__file__), test_file_path))
    fileNames = next(walk(test_file_path), (None, None, []))[2]
    fileName = splitter(fileNames[0])
    return fileName

#run pytest function
def run_pytest(test_file, test_file_path, modified_test_file_path, expected_output_path, output_path):
    if (test_file == None):
        pytest.main([test_file_path, '--expected', expected_output_path, '--output', output_path, "-s"])
        
    if (test_file != None):
        pytest.main([modified_test_file_path, '--expected', expected_output_path, '--output', output_path, "-s"])
    
#jars functions

#converter tool
def converter_tool(input_path, types, parameters, output_path):
    if parameters == "None" or parameters is None:
        subprocess.call(['java.exe', '-jar', '../distro/converter-1.4.0.jar', '-t', '' + types , '-s' , '' + input_path]),
    else:
        subprocess.call(['java.exe', '-jar', '../distro/converter-1.4.0.jar', '-t', '' + types , '-s' , '' + input_path , '-p' , '' + parameters]),

    shutil.move(os.path.join('./', 'converterToMapping.xml'), os.path.join(output_path, 'converterToMapping.xml'))   

    run_pytest(test_file, test_file_path, modified_test_file_path, expected_output_path, output_path)
           
#mapping tool 
def mapping_tool(input_path, mapping_path, output_path):
    subprocess.call(['java.exe', '-jar', '../distro/mapping-1.4.0.jar', '-i', '' + input_path , "-m", '' + mapping_path])
    shutil.move(os.path.join('./', 'mappingToPlacing.xml'), os.path.join(output_path, 'mappingToPlacing.xml'))

    run_pytest(test_file, test_file_path, modified_test_file_path, expected_output_path, output_path)

#placing tool
def placing_tool(input_path, output_path):
    subprocess.call(['java.exe', '-jar', '../distro/placing-1.4.0.jar', '-i', '' + input_path])
    shutil.move(os.path.join('./', 'placingToRendering.xml'), os.path.join(output_path, 'placingToRendering.xml'))

    run_pytest(test_file, test_file_path, modified_test_file_path, expected_output_path, output_path)

#rendering tool
def rendering_tool(input_path, types, output_path):
    subprocess.call(['java.exe', '-jar', '../distro/rendering-1.4.0.jar', '-i', '' + input_path, '-world', 'world'])
    worldExist = os.path.exists(output_path)
    if (worldExist == True):
        shutil.rmtree(output_path)  

    shutil.move(os.path.join('./', 'world'), os.path.join(output_path, 'world'))

    run_pytest(test_file, test_file_path, modified_test_file_path, expected_output_path, output_path)

#MAIN
while True:

    #import test file
    if (str(test_file) != "None"):
        sys.path.append(os.path.join(os.path.dirname(__file__), test_file_path))
        importPytestFile = __import__(splitter(test_file))
        
    if (str(test_file) == "None"):
        importPytestFile = __import__(random_test_file_select(test_file_path))

    #selected tool from test
    selected_tool = importPytestFile.jar

    #warnings
    if (warning(test_file_path, test_file, input_path, expected_output_path, output_path, selected_tool) == -1):
        break

    match selected_tool:
        case "converter": 
            converter_tool(input_path, types, parameters, output_path)
        case "mapping": 
            mapping_tool(input_path, mapping_path, output_path)
        case "placing": 
            placing_tool(input_path, output_path)
        case "rendering": 
            rendering_tool(input_path, types, output_path)
    
    break
   
    




