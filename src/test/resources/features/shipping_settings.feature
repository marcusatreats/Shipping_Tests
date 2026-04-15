@shipping @settings
Feature: Shipping Settings
  As an admin
  I want to configure global shipping settings
  So that my channels pick up the correct shipping rules

  Background:
    Given I am logged in as an admin
    And I navigate to the Shipping Settings page

  @regression
  Scenario: I can select Target Countries and I my Shipping Settings Save
    When I select the Target country "United States"
    And I click Continue
    And I click Save on my Delivery Times
    And I save the shipping settings
    Then a success message is displayed
    And United States is displayed as a Shipping Country

  @regression
  Scenario: I can add a Country to Rest of World
    When I select the Target country "France"
    And I click continue
    And I click Save on my Delivery Times
    Then a success message is displayed
    And France is shown in my Rest of World profile

  @regression
  Scenario: I can disable a shipping Country
    When I select multiple Target Countries
    And I click continue
    And I click Save on my Delivery Times
    Then I can diable all shipping countries except the default
    And I save the shipping settings
    Then only the deafult country is enabled

  @countries @regression
  Scenario Outline: Shipping settings can be configured for multiple target countries
    When I enable shipping for country "<country>"
    And I set the shipping method to "Flat Rate"
    And I enter a flat rate of "<rate>"
    And I save the shipping settings
    Then a success message is displayed
    And the flat rate "<rate>" is displayed for "<country>"

    Examples:
      | country        | rate |
      | United States  | 5.99 |
      | Canada         | 8.99 |
      | United Kingdom | 7.49 |

  @wip
  Scenario: Settings validation prevents saving without a rate
    When I enable shipping for country "United States"
    And I set the shipping method to "Flat Rate"
    And I leave the flat rate empty
    And I save the shipping settings
    Then a validation error is displayed