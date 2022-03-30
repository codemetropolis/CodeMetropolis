def pytest_addoption(parser):
    parser.addoption("--expected", action="store")


def pytest_generate_tests(metafunc):
    option_value = metafunc.config.option.expected
    if 'expected' in metafunc.fixturenames and option_value is not None:
        metafunc.parametrize("expected", [option_value])