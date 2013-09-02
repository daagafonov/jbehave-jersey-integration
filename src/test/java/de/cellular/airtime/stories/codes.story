Meta:

@author dmytro agafonov
@server http://localhost:8080

Scenario:  Create a new CAN charge

Given I add "Content-Type" header equal to "application/json"
And I add "Airtime-Country" header equal to "TE"
And I set base path
When I send a POST request on "/admin/codes/createCharge" with body:
{
  "numberOfCodes":100,
  "productType":"355ml Energy Drink",
  "author":"daa",
  "type":"CAN" 
}
Then the response status code should be 200
And the header "Content-Type" should be contains "application/json"
And the response should be in JSON
And the JSON node "success" should be equal to "true"

And the JSON node "results.charge.id" should exists
And I remember JSON node "results.charge.id" as "chargeId"

And the JSON node "results.charge.author" should be equal to "daa"
And the JSON node "results.charge.numberOfRedeemedCodes" should be equal to "0"
And the JSON node "results.charge.numberOfUnredeemedCodes" should be equal to "100"
And the JSON node "results.charge.productType" should be equal to "355ml Energy Drink"
And the JSON node "results.charge.totalNumberOfCodes" should be equal to "100"
And the JSON node "results.charge.type" should be equal to "CAN"
