import os

jar = 'rendering'

def test_number_of_files_in_world_folder(expected_rendering_path, rendering_output_path):
    expected_files = []
    for root, dirs, files in os.walk(expected_rendering_path):
        for file in files:
            expected_files.append(os.path.join(root, file))

    output_files = []
    for root, dirs, files in os.walk(rendering_output_path):
        for file in files:
            output_files.append(os.path.join(root, file))

    assert len(expected_files) == len(output_files), f"test_number_of_files_in_world_folder: Expected {len(expected_files)} files in world folder, but found {len(output_files)}"
