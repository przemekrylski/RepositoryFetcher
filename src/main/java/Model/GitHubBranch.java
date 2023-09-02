package Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GitHubBranch {
    private GitHubCommit lastCommit;
    private String name;
}
