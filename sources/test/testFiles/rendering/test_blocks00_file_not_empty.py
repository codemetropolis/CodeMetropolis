import os

jar = 'rendering'

def test_blocks00_file_not_empty(rendering_output_path):
    
    notEmpty = False
    if (os.stat(rendering_output_path + "/TEMP/blocks.0.0.csv").st_size != 0):
        notEmpty = True
    
    assert notEmpty == True, 'test_blocks00_file_not_empty: The blocks.0.0.csv file is empty.'
    
    