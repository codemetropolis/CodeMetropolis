import os
import datetime

jar = 'rendering'

def test_check_files_modification_time(rendering_output_path):
    output_files = []
    for root, dirs, files in os.walk(rendering_output_path):
        for file in files:
            output_files.append(os.path.join(root, file))

    for file in output_files:
        mtime = os.path.getmtime(file)
        mod_time = datetime.datetime.fromtimestamp(mtime)
        curr_time = datetime.datetime.now()
        diff = curr_time - mod_time
        assert diff.seconds <= 30, f"test_check_files_modification_time: '{file}' file was last modified more than 30 minutes ago"
