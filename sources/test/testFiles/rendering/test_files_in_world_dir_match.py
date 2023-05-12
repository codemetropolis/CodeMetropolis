import os

jar = 'rendering'

def test_files_in_world_dir_match(expected_rendering_path, rendering_output_path):
    for root, dirs, files in os.walk(expected_rendering_path):
        for file in files:
            expected_file_path = os.path.join(root, file)

            output_file_path = os.path.join(rendering_output_path, os.path.relpath(expected_file_path, expected_rendering_path))

            assert os.path.exists(output_file_path), f"test_files_in_world_dir_match: File not found in output world directory: '{output_file_path}'"