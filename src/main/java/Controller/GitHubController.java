package Controller;

import Model.*;
import Exception.GivenWrongFormatException;
import Exception.UsernameDoesNotExist;
import Service.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GitHubController {
    @Autowired
    private GitHubService gitHubService;

    @GetMapping("/owner-info")
    public ResponseEntity<Object> getOwnerInfo(
            @RequestParam String username,
            @RequestHeader(value = "Accept", defaultValue = "application/json") String acceptHeader) {
        try {
            GitHubOwner owner = gitHubService.getGitHubOwner(username);
            List<GitHubRepository> repositories = gitHubService.getGitHubRepositories(username);

            for (GitHubRepository repository : repositories) {
                List<GitHubBranch> branches = gitHubService.getGitHubBranches(username, repository.getName());

                for (GitHubBranch branch : branches) {
                    GitHubCommit lastCommit = gitHubService.getGitHubLastCommit(username, repository.getName(), branch.getName());
                    branch.setLastCommit(lastCommit);
                }
            }

            return new ResponseEntity<>(owner, HttpStatus.OK);
        } catch (UsernameDoesNotExist e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), "GitHub user not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (GivenWrongFormatException e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "GitHub user not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
