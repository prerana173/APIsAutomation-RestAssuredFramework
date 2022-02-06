Feature: Validating getLatestCountryDataByCode API

  @HappyPath @RegressionScenario @ValidHeadersAndValidQueryParam
  Scenario Outline: Verify that API returns all relevant covid data details for the valid country code sent
    Given getCountryDataByCode API is sent with "<valid>" parameters and "validHeader" headers
    When User calls "<api>" with "<method>" https request
    Then API should return status code <statusCode> and status line "<statusLine>"
    And "code" in response body should be "<valid>"

    Examples: 
      | valid | api           | method | statusCode | statusLine |
      | in    | country/code/ | GET    |        200 | OK         |

  @ValidHeadersAndNonExistingCountryCodeInQueryParam
  Scenario Outline: Verify that API returns empty array when user sends invalid/ non existing country code
    Given getCountryDataByCode API is sent with "<invalid>" parameters and "validHeader" headers
    When User calls "<api>" with "<method>" https request
    Then API should return status code <statusCode> and status line "<statusLine>"
    And response body should be empty array

    Examples: 
      | invalid | api           | method | statusCode | statusLine |
      | xyz     | country/code/ | GET    |        200 | OK         |

  @BlankHeadersAndValidCountryCodeInQueryParam
  Scenario Outline: Verify that API returns 401 status code with error message when user sends blank headers
    Given getCountryDataByCode API is sent with "<valid>" parameters and "invalidHeader" headers
    When User calls "<api>" with "<method>" https request
    Then API should return status code <statusCode> and status line "<statusLine>"
    And response body should return "<error>" message

    Examples: 
      | valid | api           | method | statusCode | statusLine   | error                                                                     |
      | in    | country/code/ | GET    |        401 | Unauthorized | Invalid API key. Go to https://docs.rapidapi.com/docs/keys for more info. |

  @ChangingHttpMethodFromGetToPost
  Scenario Outline: Verify that API returns 404 status code with error message when user sends Http Method as POST instead of GET
    Given getCountryDataByCode API is sent with "<valid>" parameters and "validHeader" headers
    When User calls "<api>" with "<method>" https request
    Then API should return status code <statusCode> and status line "<statusLine>"
    And response body should return "<error>" message

    Examples: 
      | valid | api           | method | statusCode | statusLine | error                                 |
      | in    | country/code/ | POST   |        404 | Not Found  | Endpoint/country/code/ does not exist |

  @verifyCasesCountIsNeverLessThanZero
  Scenario Outline: Verify that covid case counts returned in response is never less than zero
    Given getCountryDataByCode API is sent with "<valid>" parameters and "validHeader" headers
    When User calls "<api>" with "<method>" https request
    Then API should return status code <statusCode> and status line "<statusLine>"
    And value returned for "confirmed" key in response payload should not be less than zero
    And value returned for "recovered" key in response payload should not be less than zero
    And value returned for "critical" key in response payload should not be less than zero
    And value returned for "deaths" key in response payload should not be less than zero

    Examples: 
      | valid | api           | method | statusCode | statusLine |
      | in    | country/code/ | GET    |        200 | OK         |
