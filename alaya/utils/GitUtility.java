package bright.api.alaya.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.testng.Assert;
import org.testng.util.Strings;

public class GitUtility extends MainClassAlaya {

	
	public static  String branchName = null;
	public static  String gitHubToken = null;
	
	/* 
	 * This method will clone the artifacts repository based on env and fetch git token from parameter store
	 *  */
	public static void cloneRepo(String Url,String repoName) throws InvalidRemoteException, TransportException, GitAPIException, IOException {

		
		
		File localRepoDir = new File(repoName); 
		String githubUrl=Url;
		FileUtility.deleteDirectory(localRepoDir);
		
		if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("d1")) {
			 gitHubToken=CommonUtilityMethods.fetchXApiKey("gitPT");
			 if(Strings.isNullOrEmpty(gitHubToken)) {
				 logger.info("Git hub token not found from paramStore..Finding in config files");
				 gitHubToken=FileUtility.getValueFromConfig("githubTokenDev");
				 logger.info("Git hub token in config files");
			 }
			 else {
				 logger.info("Git hub token found from paramStore");
			 }
				 
			 branchName=FileUtility.getValueFromConfig("GitDevBranch");
		}
		
		else if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("t1") ) {
			 gitHubToken=CommonUtilityMethods.fetchXApiKey("gitPT");
			 if(Strings.isNullOrEmpty(gitHubToken)) {
				 logger.info("Git hub token not found from paramStore..Finding in config files");
				 gitHubToken=FileUtility.getValueFromConfig("githubTokenTest");
			 }
			 else {
				 logger.info("Git hub token found from paramStore");
			 }
			 branchName=FileUtility.getValueFromConfig("GitTestBranch");
			 
		}
		
		else if(CommonUtilityMethods.getEnvironment().equalsIgnoreCase("p1")) {
			 gitHubToken=CommonUtilityMethods.fetchXApiKey("gitPT");
			 if(Strings.isNullOrEmpty(gitHubToken)) {
				 logger.info("Git hub token not found from paramStore..Finding in config files");
				 gitHubToken=FileUtility.getValueFromConfig("githubTokenProd");
			 }
			 else {
				 logger.info("Git hub token found from paramStore");
			 }
			 branchName=FileUtility.getValueFromConfig("GitProdBranch");
		}
		else {
			Assert.fail("Please Provide a Valid Environment");
		}

	
		CredentialsProvider credentialsProvider = new
				UsernamePasswordCredentialsProvider(gitHubToken, ""); TextProgressMonitor
				consoleProgressMonitor = new TextProgressMonitor(new
						PrintWriter(System.out));

				Repository repo =
						Git.cloneRepository().setProgressMonitor(consoleProgressMonitor).setDirectory
						(localRepoDir)
						.setURI(githubUrl).setCredentialsProvider(credentialsProvider).call().
						getRepository();

				try (Git git = new Git(repo)) {

					git.branchList().setListMode(ListMode.ALL).call().stream().forEach(r ->
					System.out.println(r.getName()));


					Optional<String> branch =
							git.branchList().setListMode(ListMode.REMOTE).call().stream() .map(r ->
							r.getName()).filter(n ->
							n.contains(branchName)).findAny();

					if (branch.isPresent()) { 
						
					git.checkout().setProgressMonitor(consoleProgressMonitor).setCreateBranch(
							true).setName("testdata") .setStartPoint(branch.get()).call(); 
					}

				}

	
	logger.info("Repo is cloned successfully at given location:"+ repoName + "Path is: +"+ localRepoDir.getAbsolutePath());
	}

}