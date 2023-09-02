package Service;

import Exception.UsernameDoesNotExist;
import Exception.GivenWrongFormatException;
import Model.GitHubBranch;
import Model.GitHubOwner;
import Model.GitHubRepository;
import Model.GitHubCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GitHubService {
    private static final String GITHUB_API_BASE_URL = "https://api.github.com";

    @Autowired
    private RestTemplate restTemplate;

    public GitHubOwner getGitHubOwner(String username) {
        String apiUrl = GITHUB_API_BASE_URL + "/users/" + username;
        try {
            ResponseEntity<GitHubOwner> ownerResponseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, null, GitHubOwner.class);

            if (ownerResponseEntity.getStatusCode() == HttpStatus.OK) {
                return ownerResponseEntity.getBody();
            } else {
                throw new UsernameDoesNotExist("GitHub user not found");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new UsernameDoesNotExist("GitHub user not found");
        }
    }

    public List<GitHubRepository> getGitHubRepositories(String username) {
        String apiUrl = GITHUB_API_BASE_URL + "/users/" + username + "/repos";
        ResponseEntity<GitHubRepository[]> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, null, GitHubRepository[].class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            List<GitHubRepository> repositories = new ArrayList<>();
            for (GitHubRepository gitHubRepository : responseEntity.getBody()) {
                if (!gitHubRepository.isFork()) {
                    repositories.add(gitHubRepository);
                }
            }
            return repositories;
        } else {
            throw new UsernameDoesNotExist("GitHub user not found");
        }
    }

    public List<GitHubBranch> getGitHubBranches(String username, String repositoryName) {
        String apiUrl = GITHUB_API_BASE_URL + "/repos/" + username + "/" + repositoryName + "/branches";
        ResponseEntity<GitHubBranch[]> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, null, GitHubBranch[].class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(responseEntity.getBody());
        } else {
            throw new GivenWrongFormatException("Branches not available for the repository");
        }
    }

    public GitHubCommit getGitHubLastCommit(String username, String repositoryName, String branchName) {
        String apiUrl = GITHUB_API_BASE_URL + "/repos/" + username + "/" + repositoryName + "/branches/" + branchName;
        ResponseEntity<GitHubCommit> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, null, GitHubCommit.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity.getBody();
        } else {
            throw new GivenWrongFormatException("Last commit SHA not available for the branch");
        }
    }
}