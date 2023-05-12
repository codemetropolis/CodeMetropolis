import os

jar = 'rendering'

def test_level_file_not_empty(rendering_output_path):
    
    notEmpty = False
    if (os.stat(rendering_output_path + "/level.dat").st_size != 0):
        notEmpty = True
    
    assert notEmpty == True, 'test_level_file_not_empty: The level.dat file is empty.'
    
    