package com.myGatlingTest.api

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class API extends Simulation{

  //protocol

  val httpProtocol=
    http.baseUrl("https://reqres.in/api/users")
  //scenario
  val scn= scenario("Get API Request Demo")
    .exec(
      http("Get Single User")
        .get("/2")
        .check(
          status.is(200),
          jsonPath("$.data.first_name").is("Janet"))

    )
    .pause(2)


  //setup

  setUp(
    scn.inject(
      nothingFor(5),
      atOnceUsers(1),
      rampUsers(5) during (10),
      constantUsersPerSec(20) during (20)
    ).protocols(httpProtocol)
  )
}
