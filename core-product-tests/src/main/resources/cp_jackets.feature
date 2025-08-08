Feature: CP Jackets extraction
  @cp_jackets
  Scenario: Extract jackets across paginated pages
    Given I open CP home page
    When I navigate to Shop > Men's
    Then I collect title, price and top seller flag for each jacket and save to a text file
