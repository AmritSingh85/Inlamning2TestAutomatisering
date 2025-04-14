Feature: Basketball England User Registration
  As a potential Basketball England supporter
  I want to register as a user on the platform
  So that I can access membership features and support basketball

  Background:
    Given I am on the Basketball England registration page

  # Scenario 1: Successful registration
  # allt går som förväntat och ett konto skapas
  Scenario: Create a user account with valid information
    When I enter "01/01/1990" as date of birth
    And I enter "Jan" as firstname
    And I enter "Ola" as lastname
    And I enter "jan.ola@test.com" as email
    And I enter "jan.ola@test.com" as confirm email
    And I enter "Password123!" as password
    And I enter "Password123!" as confirm password
    And I select Fan as my role in basketball
    And I accept the terms and conditions
    And I accept the age confirmation
    And I accept the code of ethics
    And I click the create account button

  # Scenario 2: Registration with missing lastname
  # efternamn saknas
  Scenario: Create a user account with missing lastname
    When I enter "01/01/1990" as date of birth
    And I enter "Jan" as firstname
    And I enter "" as lastname
    And I enter "jan.ola@test.com" as email
    And I enter "jan.ola@test.com" as confirm email
    And I enter "Password123!" as password
    And I enter "Password123!" as password
    And I enter "Password123!" as confirm password
    And I select Fan as my role in basketball
    And I accept the terms and conditions
    And I accept the age confirmation
    And I accept the code of ethics
    And I click the create account button
    Then I should see an error message indicating that lastname is required

  # Scenario 3: Registration with wrong passwords
  # lösenord matchar inte
  Scenario: Create a user account with wrong passwords
    When I enter "01/01/1990" as date of birth
    And I enter "Jan" as firstname
    And I enter "Ola" as lastname
    And I enter "jan.ola@test.com" as email
    And I enter "john.ola@test.com" as confirm email
    And I enter "Password123!" as password
    And I enter "DifferentPassword!" as confirm password
    And I select Fan as my role in basketball
    And I accept the terms and conditions
    And I accept the age confirmation
    And I accept the code of ethics
    And I click the create account button
    Then I should see an error message indicating that passwords do not match

  # Scenario 4: Registration without accepting terms and conditions
  # terms and conditions är inte godkänt
  Scenario Outline: Create a user account with different terms acceptance options
    When I enter "<dateOfBirth>" as date of birth
    And I enter "<firstname>" as firstname
    And I enter "<lastname>" as lastname
    And I enter "<email>" as email
    And I enter "<confirmEmail>" as confirm email
    And I enter "<password>" as password
    And I enter "<confirmPassword>" as confirm password
    And I select "<basketballRole>" as my role in basketball
    And I <termsAcceptance> the terms and conditions
    And I <ageAcceptance> the age confirmation
    And I <ethicsAcceptance> the code of ethics
    And I click the create account button
    Then I should see <errorOutcome>

    Examples:
      | dateOfBirth | firstname | lastname | email            | confirmEmail     | password     | confirmPassword | basketballRole | termsAcceptance | ageAcceptance | ethicsAcceptance | errorOutcome                                                           |
      | 01/01/1990  | Jan       | Ola      | jan.ola@test.com | jan.ola@test.com | Password123! | Password123!    | Fan            | do not accept   | accept        | accept           | an error message indicating that terms and conditions must be accepted |
      | 01/01/1990  | Jan       | Ola      | jan.ola@test.com | jan.ola@test.com | Password123! | Password123!    | Player         | accept          | do not accept | accept           | an error message indicating that age confirmation must be accepted     |
      | 01/01/1990  | Jan       | Ola      | jan.ola@test.com | jan.ola@test.com | Password123! | Password123!    | Coach          | accept          | accept        | do not accept    | an error message indicating that code of ethics must be accepted       |