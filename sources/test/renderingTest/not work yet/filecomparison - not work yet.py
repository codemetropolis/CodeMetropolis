import pytest
import os
import filecmp

def testLevelFileExist():
    result = filecmp.cmp("","","level.dat")
    
    
    dir1 = "C:\Users\Zoli\CodeMetropolisNew\CodeMetropolis\sources\test\renderingTest\expected\world"
  

    dir2 = "C:\Users\Zoli\CodeMetropolisNew\CodeMetropolis\sources\test\renderingTest\output\world"
       

    common = ["level.dat"]
      

    match, mismatch, errors = filecmp.cmpfiles(dir1, dir2, common)
      
    
    assert type(match) == str, 'level.dat is not exist in RenderingTest/output/world folder'
    
    