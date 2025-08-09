Feature: CP Jackets extraction
  @cp_jackets
  Scenario: Extract Men’s Jackets data and attach to report
    Given I am on the CP home page
    When I navigate to the Men's Jackets section
    And I collect jacket details from all pages
    Then I save the details to a text file
#    And I attach the file to the test report