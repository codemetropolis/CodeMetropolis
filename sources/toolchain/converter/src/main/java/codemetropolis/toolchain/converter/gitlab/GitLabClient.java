package codemetropolis.toolchain.converter.gitlab;

import org.gitlab4j.api.*;

public class GitLabClient {

    private final String EXCEPTION_MESSAGE = "Invalid project, group, username or password! (-i hostUrl (eg.: http://gitlab-okt.sed.hu) parameters are: -p username=... password=... group=... project=...)";

    private String hostUrl;
    private String username;
    private String password;

    public GitLabClient(String hostUrl, String username, String password) {
        this.hostUrl = hostUrl;
        this.username = username;
        this.password = password;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    @SuppressWarnings("deprecation")
	public void authentication() throws GitLabConnectException
    {
        GitLabApi gitLabApi = null;

        try {
            gitLabApi = GitLabApi.login(hostUrl, username, password);
        } catch (GitLabApiException e) {
            throw new GitLabConnectException(EXCEPTION_MESSAGE);
        }

        if(gitLabApi != null) {
            GitLabElement.setGitLabApi(gitLabApi);
        }
    }
}