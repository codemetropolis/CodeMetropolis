import os

jar = 'rendering'

def test_blocks00_file_exist(rendering_output_path):
    
    assert os.path.isfile(rendering_output_path + "/TEMP/blocks0.0.csv"), 'test_r00_file_exist: The blocks0.0.csv file is not exist in output world'

    
    