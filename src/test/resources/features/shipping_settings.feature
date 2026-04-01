@shipping @settings
Feature: Shipping Settings
  As an admin
  I want to configure global shipping settings
  So that my channels pick up the correct shipping rules

  Background:
    Given I am logged in as an admin
    And I navigate to the Shipping Settings page

  @flat-rate @regression
  Scenario: Flat rate shipping saves and displays correctly for USA
    When I enable shipping for country "United States"
    And I set the shipping method to "Flat Rate"
    And I enter a flat rate of "5.99"
    And I save the shipping settings
    Then a success message is displayed
    And the flat rate "5.99" is displayed for "United States"

  @by-quantity @regression
  Scenario: Shipping by quantity saves and displays correctly
    When I enable shipping for country "United States"
    And I set the shipping method to "By Quantity"
    And I enter a rate of "2.00" for quantity "1"
    And I enter a rate of "1.50" for quantity "2"
    And I save the shipping settings
    Then a success message is displayed
    And the quantity rates are displayed correctly

  @toggle @regression
  Scenario: Shipping can be enabled and disabled
    When I disable shipping globally
    And I save the shipping settings
    Then shipping is shown as disabled
    When I enable shipping globally
    And I save the shipping settings
    Then shipping is shown as enabled

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