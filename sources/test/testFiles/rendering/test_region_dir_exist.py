import os

jar = 'rendering'

def test_region_dir_exist(rendering_output_path):
    
    assert os.path.isdir(rendering_output_path + "/region"), 'test_region_dir_exist: The region directory is not exist in output world folder'
