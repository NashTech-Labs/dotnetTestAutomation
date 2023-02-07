package com.myGatlingTest.api

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._
class PostPutDelete extends Simulation {

  //protocol

  val httpProtocol=
    http.baseUrl("https://reqres.in/api/users")
  //scenario
  val createuser= scenario("Create User API Request Demo")
    .exec(
      http("Create User")
        .post("?page=2")
        .header("content-type", "application/json")
        .asJson
        .body(RawFileBody("data/user.json"))
//        .body(StringBody(
//          """
//            |{
//            |    "name": "morpheus",
//            |    "job": "leader"
//            |}
//            |""".stripMargin))
        .check(
          status.is(201),
          jsonPath("$.name").is("morpheus"))

    )
    .pause(2)

   //scenario put request
   val updateuser = scenario("Update User API Request Demo")
     .exec(
       http("Update User")
         .put("/2")
         .header("content-type", "application/json")
         .asJson
         .body(RawFileBody("data/user.json"))
         .check(
           status.is(200),
           jsonPath("$.name").is("morpheus"))

     )
     .pause(2)
  //setup

  setUp(
    createuser.inject(rampUsers(5) during (10)).protocols(httpProtocol),
    updateuser.inject(rampUsers(3) during (3),
    ).protocols(httpProtocol)
  )

}
