Meta:

@author dmytro agafonov
@server http://localhost:8080

Scenario:  Set a new offer with type KICKBACK

Given I add "Content-Type" header equal to "application/json"
And I add "Airtime-Country" header equal to "TE"
And I set base path
When I send a POST request on "/admin/offers/set" with body:
{
  "type":"KICKBACK",
  "url":{
    "ru":"sdfssdfsdfs",
    "de":"erwew",
    "en":"fsdfsdf"
  },
  "headline":{
    "ru":"sdfs",
    "de":"erwew",
    "en":"fsdfsdf"
  },
  "description":{
  	"ru":"sdfs",
    "de":"erwew",
    "en":"fsdfsdf"
  },
  "imageId":{
  	"ru":"sdfs",
    "de":"erwew",
    "en":"fsdfsdf"
  }
}
Then the response status code should be 200
And the header "Content-Type" should be contains "application/json"
And the response should be in JSON
And the JSON node "success" should be equal to "true"

And the JSON node "results.offer.type" should be equal to "KICKBACK"
And the JSON node "results.offer.url.de" should be equal to "erwew"
And the JSON node "results.offer.url.ru" should be equal to "sdfssdfsdfs"
And the JSON node "results.offer.url.en" should be equal to "fsdfsdf"

And the JSON node "results.offer.headline.de" should be equal to "erwew"
And the JSON node "results.offer.headline.ru" should be equal to "sdfs"
And the JSON node "results.offer.headline.en" should be equal to "fsdfsdf"

And the JSON node "results.offer.description.de" should be equal to "erwew"
And the JSON node "results.offer.description.ru" should be equal to "sdfs"
And the JSON node "results.offer.description.en" should be equal to "fsdfsdf"

And the JSON node "results.offer.imageId.de" should be equal to "erwew"
And the JSON node "results.offer.imageId.ru" should be equal to "sdfs"
And the JSON node "results.offer.imageId.en" should be equal to "fsdfsdf"

