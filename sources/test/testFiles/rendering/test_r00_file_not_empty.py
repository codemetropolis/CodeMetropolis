import os

jar = 'rendering'

def test_r00_file_not_empty(rendering_output_path):
    
    notEmpty = False
    if (os.stat(rendering_output_path + "/region/r.0.0.mca").st_size != 0):
        notEmpty = True
    
    assert notEmpty == True, 'test_r00_file_not_empty: The r0.0.mca file is empty.'
    
    