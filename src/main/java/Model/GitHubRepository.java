package Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GitHubRepository {
    private String name;
    private boolean fork;
    private GitHubOwner owner;
}
