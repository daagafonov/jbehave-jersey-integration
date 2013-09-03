Meta:

@author daagafonov@gmail.com
@server http://localhost:8080

Scenario:  Create a new UNIQUE_CODE benefit

Given I add "Content-Type" header equal to "application/json"
And I add "Airtime-Country" header equal to "TE"
And I set base path
When I send a POST request on "/admin/benefits/createBenefit" with body:
{
  "image": "http://www.google.de",
  "imageTeaser": "http://www.google.de",
  "author": "author",
  "headline": {
    "de": "headlineDE",
    "en": "headlineEN"
  },
  "subHeadline": {
    "de": "subDE",
    "en": "subEN"
  },
  "descriptionHeadline": {
    "de": "descHeadLineDE",
    "en": "descHeadLineEN"
  } ,
  "descriptionLine1": {
    "de": "descriptionLine1DE",
    "en": "descriptionLine1EN"
  },
  "descriptionLine2": {
    "de": "descriptionLine2DE",
    "en": "descriptionLine2EN"
  },
  "type": "UNIQUE_CODE",
  "parameter1": "p1",
  "parameter2": "p2"
}
Then the response status code should be 200
And the header "Content-Type" should be contains "application/json"
And the response should be in JSON
And the JSON node "success" should be equal to "true"
And the JSON node "results.benefit.id" should exists
And I remember JSON node "results.benefit.id" as "benefitId"
And the JSON node "results.benefit.image" should be equal to "http://www.google.de"
And the JSON node "results.benefit.imageTeaser" should be equal to "http://www.google.de"
And the JSON node "results.benefit.author" should be equal to "author"
And the JSON node "results.benefit.headline.de" should be equal to "headlineDE"
And the JSON node "results.benefit.headline.en" should be equal to "headlineEN"
And the JSON node "results.benefit.subHeadline.de" should be equal to "subDE"
And the JSON node "results.benefit.subHeadline.en" should be equal to "subEN"
And the JSON node "results.benefit.descriptionHeadline.de" should be equal to "descHeadLineDE"
And the JSON node "results.benefit.descriptionHeadline.en" should be equal to "descHeadLineEN"
And the JSON node "results.benefit.descriptionLine1.de" should be equal to "descriptionLine1DE"
And the JSON node "results.benefit.descriptionLine1.en" should be equal to "descriptionLine1EN"
And the JSON node "results.benefit.descriptionLine2.de" should be equal to "descriptionLine2DE"
And the JSON node "results.benefit.descriptionLine2.en" should be equal to "descriptionLine2EN"
And the JSON node "results.benefit.type" should be equal to "UNIQUE_CODE"
And the JSON node "results.benefit.parameter1" should be equal to "p1"
And the JSON node "results.benefit.parameter2" should be equal to "p2"


Scenario: Get the UNIQUE_CODE benefit

Given I add "Content-Type" header equal to "application/json"
And I add "Airtime-Country" header equal to "TE"
When I send a POST request on "/admin/benefits/getBenefit" with the remembered "benefitId" as "benefitId" node in JSON body:
{ "benefitId" : %benefitId% }
Then the response status code should be 200
And the header "Content-Type" should be contains "application/json"
And the response should be in JSON
And the JSON node "success" should be equal to "true"
And the JSON node "results.benefit.id" should be equal to the remembered "benefitId"
And the JSON node "results.benefit.type" should be equal to "UNIQUE_CODE"


Scenario: Get all benefits

Given I add "Content-Type" header equal to "application/json"
And I add "Airtime-Country" header equal to "TE"
When I send a POST request on "/admin/benefits/getBenefits" with body:
{}
Then the response status code should be 200
And the header "Content-Type" should be contains "application/json"
And the response should be in JSON
And the JSON node "success" should be equal to "true"


Scenario: Delete the UNIQUE_CODE benefit

Given I add "Content-Type" header equal to "application/json"
And I add "Airtime-Country" header equal to "TE"
When I send a POST request on "/admin/benefits/deleteBenefit" with the remembered "benefitId" as "benefitId" node in JSON body:
{ "benefitId" : %benefitId% }
Then the response status code should be 200
And the header "Content-Type" should be contains "application/json"
And the response should be in JSON
And the JSON node "success" should be equal to "true"


