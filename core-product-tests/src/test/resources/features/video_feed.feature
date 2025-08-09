Feature: This feature file test video feeds scenarios

  @videoFeed
  Scenario: Count video feeds and those older than 3 days
    Given I am on the CP home page
    When I hover over the menu item and click on New & Featured
    Then I count total number of video feeds
    And I count video feeds older than or equal to 3 days