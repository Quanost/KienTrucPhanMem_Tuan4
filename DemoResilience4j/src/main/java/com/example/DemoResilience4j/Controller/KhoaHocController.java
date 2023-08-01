package com.example.DemoResilience4j.Controller;



import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/allKhoaHoc")
public class KhoaHocController {

    private RestTemplate restTemplate=new RestTemplate();

    private static final String SERVICE_A = "serviceA";
    int count=1;
    int countCir=1;

//    @GetMapping("/getAll")
//    public List<KhoaHocReponse> callAPI() {
//        System.out.println("Retry method called " + count++ + " times at " + new Date());
//
//        RestTemplate restTemplate = new RestTemplate();
//        String fooResourceUrl
//                = "http://localhost:8087/khoahoc/khoahocs";
//        ResponseEntity<List<KhoaHocReponse> > response
//                = restTemplate.exchange(fooResourceUrl , HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<KhoaHocReponse>>() {});
//
//        List<KhoaHocReponse> courseResponses = response.getBody();
//        return courseResponses;
//
//    }
//    @GetMapping()
//    @CircuitBreaker(name = SERVICE_A)
//    @Retry(name = SERVICE_A, fallbackMethod = "serviceAFallback")
//    @RateLimiter(name = SERVICE_A)
//    public String serviceA() {
//        String url = "http://localhost:8087/khoahoc/khoahocs";
//        System.out.println("Retry method called " + count++ + " times at " + new Date());
//        return restTemplate.getForObject(
//                url,
//                String.class
//        );
//    }

    @GetMapping("/circuitBreaker")
    @CircuitBreaker(name = SERVICE_A, fallbackMethod = "serviceAFallback")
    public String servicecircuitBreaker() {
        String url = "http://localhost:8087/khoahoc/khoahocs";
        System.out.println("CircuitBreaker method called " + countCir++ + " times at " + new Date());
        return restTemplate.getForObject(
                url,
                String.class
        );
    }
    @GetMapping("/retry")
    @Retry(name = SERVICE_A,fallbackMethod = "serviceAFallback")
    public String getAllKhoaHocRetry() {
        String url = "http://localhost:8087/khoahoc/khoahocs";
        System.out.println("Retry method called " + count++ + " times at " + new Date());
        return restTemplate.getForObject(
                url,
                String.class
        );
    }

    @GetMapping("/rateLimiter")
    @RateLimiter(name = SERVICE_A)
    public String getAllKhoaHocRate() {
        String url = "http://localhost:8087/khoahoc/khoahocs";
        return restTemplate.getForObject(
                url,
                String.class
        );
    }


    public String serviceAFallback(Exception e) {
        return "This service CRUD connect failed";
    }
}
