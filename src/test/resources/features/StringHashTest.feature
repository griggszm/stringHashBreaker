Feature: Test correctness of hashing algorithm

  Scenario: Hash a string 1
    Given the plaintext "helloworld"
    When the string is hashed
    Then the hash is "1839003400"

  Scenario: Hash a string 2
    Given the plaintext "verylongteststring1234567890"
    When the string is hashed
    Then the hash is "1092661835"

  Scenario: Hash a string 3
    Given the plaintext "debug"
    When the string is hashed
    Then the hash is "-1510899478"