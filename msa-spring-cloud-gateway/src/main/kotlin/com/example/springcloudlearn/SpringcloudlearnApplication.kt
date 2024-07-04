package com.example.springcloudlearn

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringcloudlearnApplication {
  @Bean
  fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator {
    return builder.routes {
      route("path1") {
        path("/path1/**")
        predicate { true }
        filters { prefixPath("/main") }
        uri("http://localhost:8081")
      }
      route("path2") {
        path("/path2/**")
        predicate { true }
        filters { prefixPath("/main") }
        uri("http://localhost:8081/")
      }

      route("default") {
        path("/**")
        filters {
          stripPrefix(1)
          prefixPath("/main/test")
        }
        uri("http://localhost:8081")
      }
    }
  }
}

fun main(args: Array<String>) {
  runApplication<SpringcloudlearnApplication>(*args)
}
