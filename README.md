RestAssured Framwork is used for API Automation.
1. ->Project with name "getLatestCountryDataByCodeAPIScripts" contains automated API scripts for 5 scenarios. 
    All 5 scenarios are written in the same class as different methods. Out of 21 Test scenarios written, 5 have been automated.
	 ->Project with name "Covid19DataCucumberAPIFramework" contains Cucumber API Framework. All 5 scenarios are written in Gherkin Format using a simple framework.
	
2. Logs and test reports(cucumber report) is available in "Covid19DataCucumberAPIFramework" Maven project. 
	 logs path in project -> ..Covid19DataCucumberAPIFramework\logging.txt
	 Test Report path in project -> ..Covid19DataCucumberAPIFramework\target\cucumber-html-reports\overview-features.html
								   ..Covid19DataCucumberAPIFramework\target\cucumber-html-reports\report-feature_3599473306.html
								   
3. ->To execute the scenarios from "getLatestCountryDataByCodeAPIScripts" project, navigate to
   '..getLatestCountryDataByCodeAPIScripts\src\get_latest_country_data_by_code_api_tests\GetLatestCountryDataByCodeAPITests.java' path, right click and choose 'Run As - Java Application'
	 ->To execute the scenarios from "Covid19DataCucumberAPIFramework" project and generate reports, open command prompt and navigate to the path containing pom.xml
   and enter command 'mvn verify'. To execute the feature file without cucumber report, navigate to '..Covid19DataCucumberAPIFramework\src\test\java\features\getLatestCountryDataByCodeAPIValidation.feature' path,
   right click and choose 'Run As - Cucumber Feature'.
