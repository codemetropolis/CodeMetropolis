import os

jar = 'rendering'

def test_world_dir_exist(rendering_output_path):
    
    assert os.path.isdir(rendering_output_path), 'test_world_dir_exist: The output world directory is not exist'
