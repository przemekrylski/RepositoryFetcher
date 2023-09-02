# RepositoryFetcher

Application writen in Java 17 and SpringBoot 3. It has been divided on different classes which are responsible for separate logic in the whole application.

Short explanation of classes mentioned above:

Configuration - configuring restTemple to put "Authorization" header as default in every call using API
  /RestTemplateConfig 
  
Controller - GET Mapping for fetching github repositories and handling Http Errors
  /GitHubController 
  
Exception - Custom exceptions needed for better explanation of different exceptions during program runtime
  /GitHubCallFailedException
  /GivenWrongFormatException
  /UsernameDoesNotExistException

Interceptor - Initialized with an instance of HttpHeaders that contains the headers, in this example I set "Authorization" header which will be used in calls
  /HeaderRequestInterceptor

Model - Set of different models/DTOs, each of them is responsible for containing different parts of data of api calls
  /CustomErrorResponse
  /GitHubBranch
  /GitHubCommit
  /GitHubOwner
  /GitHubRepository

Service - Implementation of user service to fetch and manipulate date from github api, getting repositories, checking if they are forked, getting branches for each repository and the latest commit of each branch. For better readability and the ability to maintain and extend the code in the future service could be implemented like /GitHubService as Interface with different method structures and /GitHubServiceImpl implementing GitHubService methods and providing exact logic for each of them.
  /GitHubService


In order to make github api call we need to create new GET method and paste: "http://localhost:8080/api/owner-info?username=". After "username=" we should put requested user login/nickname from github in order to get all needed repositories
