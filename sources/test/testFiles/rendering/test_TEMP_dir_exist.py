import os

jar = 'rendering'

def test_TEMP_dir_exist(rendering_output_path):
    
    assert os.path.isdir(rendering_output_path + "/TEMP"), 'test_TEMP_dir_exist: The TEMP directory is not exist in output world folder'