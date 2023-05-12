import os

jar = 'rendering'

def test_r00_file_exist(rendering_output_path):
    
    assert os.path.isfile(rendering_output_path + "/region/r.0.0.mca"), 'test_r00_file_exist: The r.0.0 file is not exist in output world'

    
    