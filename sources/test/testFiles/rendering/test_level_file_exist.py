import os

jar = 'rendering'

def test_level_file_exist(rendering_output_path):
    
    assert os.path.isfile(rendering_output_path + "/level.dat"), 'test_level_file_exist: The level.dat file is not exist in output world'

      