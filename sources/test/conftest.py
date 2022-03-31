def pytest_addoption(parser):
    parser.addoption("--expected", action="store")
    parser.addoption("--output", action="store")


def pytest_generate_tests(metafunc):
    option_value = metafunc.config.option.expected
    if 'expected' in metafunc.fixturenames and option_value is not None:
        metafunc.parametrize("expected", [option_value])
    option_value2 = metafunc.config.option.output
    if 'output' in metafunc.fixturenames and option_value is not None:
        metafunc.parametrize("output", [option_value2])